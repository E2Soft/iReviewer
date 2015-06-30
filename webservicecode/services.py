import endpoints
from protorpc import remote
from protorpc import message_types
from protorpc import messages

from google.appengine.ext import ndb
import httplib

from model import UserModel, CommentModel, ImageModel, ReviewModel, GroupModel, ReviewObjectModel, GroupToReviewModel, GroupToUserModel
from messages import UserMessage, CommentMessage, ImageMessage, ReviewMessage, GroupMessage, ReviewObjectMessage, GroupToReviewMessage, GroupToUserMessage, StringMessage, DateMessage
from messages import UserMessageCollection, CommentMessageCollection, ImageMessageCollection, ReviewMessageCollection, GroupMessageCollection, ReviewObjectMessageCollection, GroupToReviewMessageCollection, GroupToUserMessageCollection
from messages import DeleteMessage, DeleteMessageCollection

from utils import string_to_datetime, date_to_utc

@endpoints.api(name='sync', version='v1', description='API to sync data from clients to server')
class ReviewerSyncApi(remote.Service):

	@endpoints.method(DateMessage, CommentMessageCollection, 
		path='synccomment', http_method='GET', name='comment.syncdown')
	def comment_sync_down(self, request):
		"""
			metoda koja ce vratiti sve komentare koji su dodati/menjani nakon proteglov
			vremena od poslednje sinhronizacije:
			
			Args:
				StringMessage (type): messages.Message 
				vreme poslednje sinhronizacije u %Y-%m-%dT%H:%M:%S formatu
				
			Returns:
				CommentMessageCollection (type): messages.Message 
				Izlazna poruka koja se salje klijentima
		"""
		
		query = CommentModel.query(CommentModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for comment in query:
			my_items.append(CommentMessage(content=comment.content,creator = comment.creator,review_uuid=comment.review_uuid,
			uuid=comment.uuid,last_modified=comment.last_modified))
		
		return CommentMessageCollection(items = my_items)
		
	@endpoints.method(DateMessage, ImageMessageCollection, 
		path='syncimage', http_method='GET', name='image.syncdown')
	def image_sync_down(self, request):
		query = ImageModel.query(ImageModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for img in query:
			my_items.append(ImageMessage(name=img.name,image=img.image,uuid=request.uuid,review_uuid=img.review_uuid,
			revobj_uuid=img.revobj_uuid,is_main=img.is_main,last_modified=img.last_modified, deleted=img.deleted))
		
		return ImageMessageCollection(items = my_items)
		
	@endpoints.method(DateMessage, ReviewMessageCollection, 
		path='syncreview', http_method='GET', name='review.syncdown')
	def review_sync_down(self, request):
		query = ReviewModel.query(ReviewModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for review in query:
			my_items.append(ReviewMessage(name=review.name,description=review.description,rating=review.rating,
			tags=review.tags,last_modified=review.last_modified,date_created = review.date_created,creator=review.creator,revobj_uuid=review.reviewobj_uuid,
			uuid=review.uuid, deleted = review.deleted))
		
		return ReviewMessageCollection(items = my_items)
	
	@endpoints.method(DateMessage, GroupMessageCollection, 
		path='syncgroup', http_method='GET', name='group.syncdown')
	def group_sync_down(self, request):
		query = GroupModel.query(GroupModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for group in query:
			my_items.append(GroupMessage(name=group.name,owner=group.owner,
			last_modified=group.last_modified,
			uuid=group.uuid, deleted=group.deleted))
		
		return GroupMessageCollection(items = my_items)		
	
	@endpoints.method(DateMessage, ReviewObjectMessageCollection, 
		path='syncrevobject', http_method='GET', name='revobject.syncdown')
	def revobject_sync_down(self, request):
		query = ReviewObjectModel.query(ReviewObjectModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for rev in query:
			my_items.append(ReviewObjectMessage(name=rev.name,description=rev.description,creator=rev.creator,
			lat=rev.lat,lon=rev.lon,tags=rev.tags,last_modified=rev.last_modified,uuid=rev.uuid,deleted=rev.deleted))
		
		return ReviewObjectMessageCollection(items = my_items)
	
	@endpoints.method(DateMessage, UserMessageCollection, 
		path='syncuser', http_method='GET', name='user.syncdown')
	def user_sync_down(self, request):
		query = UserModel.query(UserModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for user in query:
			my_items.append(UserMessage(user_name = user.user_name, last_modified = user.last_modified, email=user.email, 
			uuid = user.uuid, deleted=user.deleted))
		
		return UserMessageCollection(items = my_items)
		
	@endpoints.method(UserMessage, StringMessage, 
		path='registeruser', http_method='POST', name='user.register')
	def register_user(self, request):
		
		query = UserModel.query(UserModel.user_name==request.user_name)
		if query.count() != 0:
			return StringMessage(content = "name_exists")
		
		query = UserModel.query(UserModel.email==request.email)
		if query.count() != 0:
			return StringMessage(content = "email_exists")
		
		UserModel(user_name=request.user_name,last_modified=date_to_utc(request.last_modified),
		email=request.email,uuid=request.uuid,deleted=request.deleted).put()
		
		return StringMessage(content = "ok")
	
	@endpoints.method(DateMessage, GroupToReviewMessageCollection, 
		path='syncgrouptoreview', http_method='GET', name='grouptoreview.syncdown')
	def group_to_review_sync_down(self, request):
		query = GroupToReviewModel.query(GroupToReviewModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for group_to_review in query:
			my_items.append(GroupToReviewMessage(last_modified = group_to_review.last_modified, 
			uuid = group_to_review.uuid, deleted = group_to_review.deleted, 
			group = group_to_review.group, review = group_to_review.review))
		
		return GroupToReviewMessageCollection(items = my_items)
		
	@endpoints.method(DateMessage, GroupToUserMessageCollection, 
		path='syncgrouptouser', http_method='GET', name='grouptouser.syncdown')
	def group_to_user_sync_down(self, request):
		query = GroupToUserModel.query(GroupToUserModel.last_modified > string_to_datetime(request.date))
		
		my_items = []
		
		for group_to_user in query:
			my_items.append(GroupToUserMessage(last_modified = group_to_user.last_modified, 
			uuid = group_to_user.uuid, deleted = group_to_user.deleted, 
			group = group_to_user.group, user = group_to_user.user))
		
		return GroupToUserMessageCollection(items = my_items)


@endpoints.api(name='crud', version='v1', description='API to manipulate data from clients to server')
class ReviewerCRUDApi(remote.Service):

	#Tested
	@endpoints.method(message_types.VoidMessage,UserMessageCollection,
		path='user', http_method='GET', name='users.list')
	def user_list(self, unused_request):
		qry = UserModel.query()
		my_items = []
			
		for user in qry:
			my_items.append(UserMessage(user_name = user.user_name, last_modified = user.last_modified, email=user.email, 
			uuid = user.uuid, deleted=user.deleted))
		
		return UserMessageCollection(items = my_items)
	
	@endpoints.method(UserMessage, message_types.VoidMessage, 
		path='user', http_method='POST', name='user.insert')
	def user_insert(self, request):
		
		query = UserModel.query(UserModel.uuid==request.uuid)
		
		if query.count() == 0:
			UserModel(user_name=request.user_name,last_modified=date_to_utc(request.last_modified),
			email=request.email,uuid=request.uuid,deleted=request.deleted).put()
		else:
			user = query.get()
		
			user.user_name = request.user_name
			user.last_modified = date_to_utc(request.last_modified)
			user.email = request.email
			user.deleted = request.deleted
			
			user.put()
		
		return message_types.VoidMessage()
	
	#Tested
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='user', http_method='DELETE', name='user.delete')
	def user_delete(self, request):
		
		for item in request.items:
			query = UserModel.query(UserModel.uuid == item.uuid)
			
			if query.count() != 0:
				user = query.get()
				user.delete = True
				user.last_modified = string_to_datetime(item.last_modified)
				
				user.put()
			
		return message_types.VoidMessage()
	
	#Tested	
	@endpoints.method(message_types.VoidMessage,CommentMessageCollection,
		path='comment', http_method='GET', name='comment.list')
	def comment_list(self, unused_request):
		qry = CommentModel.query()
		my_items = []
			
		for comment in qry:
			my_items.append(CommentMessage(content = comment.content, last_modified = comment.last_modified, 
			creator=comment.creator, uuid = comment.uuid, review_uuid=comment.review_uuid))
		
		return CommentMessageCollection(items = my_items)
	
	#Tested	
	@endpoints.method(CommentMessageCollection, message_types.VoidMessage, 
		path='comment', http_method='POST', name='comment.insert')
	def comment_insert(self, request):
		my_items = CommentMessageCollection(items = request.items)

		for comment in my_items.items:
			CommentModel(content = comment.content, creator=comment.creator, uuid = comment.uuid,
			last_modified=date_to_utc(comment.last_modified), review_uuid=comment.review_uuid).put()
			
		return message_types.VoidMessage()
	
	#Tested
	@endpoints.method(message_types.VoidMessage,ImageMessageCollection,
		path='image', http_method='GET', name='image.list')
	def image_list(self, unused_request):
		qry = ImageModel.query()
		my_items = []
			
		for img in qry:
			my_items.append(ImageMessage(name=img.name, image=img.image, uuid = img.uuid, 
			review_uuid = img.review_uuid, revobj_uuid = img.revobj_uuid, is_main = img.is_main,deleted=img.deleted))
		
		return ImageMessageCollection(items = my_items)
	
	#Tested
	@endpoints.method(ImageMessageCollection, message_types.VoidMessage, 
		path='image', http_method='POST', name='image.insert')
	def image_insert(self, request):
		#image is Base64 string
		my_items = ImageMessageCollection(items = request.items)
		
		for item in my_items.items:
			ImageModel(name=item.name,image=item.image, uuid = item.uuid, review_uuid=item.review_uuid, 
			revobj_uuid=item.revobj_uuid,is_main=item.is_main,last_modified=date_to_utc(item.last_modified),deleted=item.deleted).put()
			
		return message_types.VoidMessage()
	
	#Tested	
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='image', http_method='DELETE', name='image.delete')
	def image_delete(self, request):
		
		for item in request.items:
			query = ImageModel.query(ImageModel.uuid == item.uuid)
			
			if query.count() != 0:
				imgage = query.get()
				imgage.delete = True
				imgage.last_modified = string_to_datetime(item.last_modified)
				
				imgage.put()
		
		return message_types.VoidMessage()
	
	#Tested	
	@endpoints.method(message_types.VoidMessage,ReviewMessageCollection,
		path='review', http_method='GET', name='review.list')
	def review_list(self, unused_request):
		qry = ReviewModel.query()
		my_items = []
			
		for rev in qry:
			my_items.append(ReviewMessage(name=rev.name,description=rev.description,rating=rev.rating,tags=rev.tags,
			revobj_uuid=rev.reviewobj_uuid,last_modified=rev.last_modified, date_created=rev.date_created,creator=rev.creator,uuid=rev.uuid,
			deleted=rev.deleted))
		
		return ReviewMessageCollection(items = my_items)
	
	#Tested
	@endpoints.method(ReviewMessageCollection, message_types.VoidMessage, 
		path='review', http_method='POST', name='review.insert')
	def review_insert(self, request):
		my_items = ReviewMessageCollection(items = request.items)
		
		for rev in my_items.items:
			query = ReviewModel.query(ReviewModel.uuid == rev.uuid)
			
			if query.count() == 0:
				ReviewModel(name=rev.name,description=rev.description,rating=rev.rating,tags=rev.tags,
				reviewobj_uuid=rev.revobj_uuid,last_modified=date_to_utc(rev.last_modified),date_created=date_to_utc(rev.date_created),creator=rev.creator,
				uuid=rev.uuid,deleted=rev.deleted).put()
			else:
				review = query.get()
			
				review.name = rev.name
				review.description = rev.description
				review.rating = rev.rating
				review.tags = rev.tags
				review.reviewobj_uuid=rev.revobj_uuid
				review.last_modified = date_to_utc(rev.last_modified)
				review.date_created = date_to_utc(rev.date_created)
				review.creator=rev.creator
				review.deleted=rev.deleted
				
				review.put()
				
		return message_types.VoidMessage()	
	
	#Tested	
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='review', http_method='DELETE', name='review.delete')
	def review_delete(self, request):
		
		for item in request.items:
			query = ReviewModel.query(ReviewModel.uuid == item.uuid)
			
			if query.count() != 0:
				rev = query.get()
				rev.deleted = True
				rev.last_modified = string_to_datetime(item.last_modified)
				
				rev.put()
		
		return message_types.VoidMessage()	

	#Tested
	@endpoints.method(message_types.VoidMessage,GroupMessageCollection,
		path='group', http_method='GET', name='group.list')
	def group_list(self, unused_request):
		qry = GroupModel.query()
		my_items = []
			
		for group in qry:
			my_items.append(GroupMessage(name=group.name,owner=group.owner,
			last_modified=group.last_modified,uuid=group.uuid,deleted=group.deleted))
		
		return GroupMessageCollection(items = my_items)
	
	#Tested
	@endpoints.method(GroupMessageCollection, message_types.VoidMessage, 
		path='group', http_method='POST', name='group.insert')
	def group_insert(self, request):
		my_items = GroupMessageCollection(items = request.items)
		
		for item in my_items.items:
			query = GroupModel.query(GroupModel.uuid == item.uuid)
			
			if query.count() == 0:
				GroupModel(name=item.name,owner=item.owner,
				last_modified=date_to_utc(item.last_modified), 
				uuid=item.uuid,deleted=item.deleted).put()
			else:
				group = query.get()
			
				group.name = item.name
				group.owner = item.owner
				group.last_modified = date_to_utc(item.last_modified)
				group.deleted = item.deleted
				
				group.put()
				
		return message_types.VoidMessage()
	
	#Tested	
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='group', http_method='DELETE', name='group.delete')
	def group_delete(self, request):
		
		for item in request.items:
			query = GroupModel.query(GroupModel.uuid == item.uuid)
			
			if query.count() != 0:
				group = query.get()
				group.last_modified = string_to_datetime(item.last_modified)
				group.deleted = True
				
				group.put()
			
		return message_types.VoidMessage()	
	
	#Tested	
	@endpoints.method(message_types.VoidMessage,ReviewObjectMessageCollection,
		path='revobject', http_method='GET', name='revobject.list')
	def revobject_list(self, unused_request):
		qry = ReviewObjectModel.query()
		my_items = []
			
		for rev in qry:
			my_items.append(ReviewObjectMessage(name=rev.name,description=rev.description,creator=rev.creator,
			lat=rev.lat,lon=rev.lon,tags=rev.tags,last_modified=rev.last_modified,uuid=rev.uuid,deleted=rev.deleted))
		
		return ReviewObjectMessageCollection(items = my_items)	
	
	#Tested
	@endpoints.method(ReviewObjectMessageCollection, message_types.VoidMessage, 
		path='revobject', http_method='POST', name='revobject.insert')
	def revobject_insert(self, request):
		my_items = ReviewObjectMessageCollection(items = request.items)
		
		for item in my_items.items:
			query = ReviewObjectModel.query(ReviewObjectModel.uuid == item.uuid)
			
			if query.count() == 0:
				ReviewObjectModel(name=item.name,description=item.description,creator=item.creator,
				lat=item.lat,lon=item.lon,tags=item.tags,last_modified=date_to_utc(item.last_modified),
				uuid=item.uuid,deleted=item.deleted).put()
			else:
				revobj = query.get()
				
				revobj.name = item.name
				revobj.description = item.description
				revobj.creator = item.creator
				revobj.lat = item.lat
				revobj.lon = item.lon
				revobj.tags = item.tags
				revobj.last_modified = date_to_utc(item.last_modified)
				revobj.deleted = item.deleted
				
				revobj.put()
				
				
		return message_types.VoidMessage()
		
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='revobject', http_method='DELETE', name='revobject.delete')
	def revobject_delete(self, request):
		
		for item in request.items:
			query = ReviewObjectModel.query(ReviewObjectModel.uuid == item.uuid)
			
			if query.count() != 0:
				revobj = query.get()
				revobj.deleted = True
				revobj.last_modified = string_to_datetime(item.last_modified)
				
				revobj.put()
			
		return message_types.VoidMessage()
		
	@endpoints.method(GroupToReviewMessageCollection, message_types.VoidMessage, 
		path='grouptoreview', http_method='POST', name='grouptoreview.insert')
	def group_to_review_insert(self, request):
		my_items = GroupToReviewMessageCollection(items = request.items)
		
		for item in my_items.items:
			query = GroupToReviewModel.query(GroupToReviewModel.uuid == item.uuid)
			
			if query.count() == 0:
				GroupToReviewModel(uuid = item.uuid, deleted = item.deleted, last_modified = date_to_utc(item.last_modified), 
				group = item.group, review = item.review).put()
			else:
				groupToRev = query.get()
				
				groupToRev.last_modified = date_to_utc(item.last_modified)
				groupToRev.deleted = item.deleted
				groupToRev.group = item.group
				groupToRev.review = item.review
				
				groupToRev.put()
				
		return message_types.VoidMessage()
	
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='grouptoreview', http_method='DELETE', name='grouptoreview.delete')
	def group_to_review_delete(self, request):
		
		for item in request.items:
			query = GroupToReviewModel.query(GroupToReviewModel.uuid == item.uuid)
			
			if query.count() != 0:
				groupToRev = query.get()
				groupToRev.deleted = True
				groupToRev.last_modified = string_to_datetime(item.last_modified)
				
				groupToRev.put()
			
		return message_types.VoidMessage()
		
	@endpoints.method(GroupToUserMessageCollection, message_types.VoidMessage, 
		path='grouptouser', http_method='POST', name='grouptouser.insert')
	def group_to_user_insert(self, request):
		my_items = GroupToUserMessageCollection(items = request.items)
		
		for item in my_items.items:
			query = GroupToUserModel.query(GroupToUserModel.uuid == item.uuid)
			
			if query.count() == 0:
				GroupToUserModel(uuid = item.uuid, deleted = item.deleted, last_modified = date_to_utc(item.last_modified), 
				group = item.group, user = item.user).put()
			else:
				groupToUser = query.get()
				
				groupToUser.last_modified = date_to_utc(item.last_modified)
				groupToUser.deleted = item.deleted
				groupToUser.group = item.group
				groupToUser.user = item.user
				
				groupToUser.put()
				
		return message_types.VoidMessage()
	
	@endpoints.method(DeleteMessageCollection, message_types.VoidMessage, 
		path='grouptouser', http_method='DELETE', name='grouptouser.delete')
	def group_to_user_delete(self, request):
		
		for item in request.items:
			query = GroupToUserModel.query(GroupToUserModel.uuid == item.uuid)
			
			if query.count() != 0:
				groupToUser = query.get()
				groupToUser.deleted = True
				groupToUser.last_modified = string_to_datetime(item.last_modified)
				
				groupToUser.put()
			
		return message_types.VoidMessage()
