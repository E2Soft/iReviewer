from protorpc import message_types
from protorpc import messages
from datetime import datetime

class UserMessage(messages.Message):
	user_name = messages.StringField(1)
	last_modified = message_types.DateTimeField(2)
	email = messages.StringField(3)
	uuid = messages.StringField(4)
	deleted = messages.BooleanField(5)

class UserMessageCollection(messages.Message):
	items = messages.MessageField(UserMessage, 1, repeated=True)
	
class CommentMessage(messages.Message):
	content = messages.StringField(1)
	creator = messages.StringField(2)
	review_uuid = messages.StringField(3)
	uuid = messages.StringField(4)
	last_modified = message_types.DateTimeField(5)

class CommentMessageCollection(messages.Message):
	items = messages.MessageField(CommentMessage, 1, repeated=True)	
	
class ImageMessage(messages.Message):
	name = messages.StringField(1, required=True)
	image = messages.BytesField(2, required=True)
	last_modified = message_types.DateTimeField(3)
	is_main = messages.BooleanField(4)
	uuid = messages.StringField(5, required=True)
	review_uuid = messages.StringField(6,required=True)
	revobj_uuid = messages.StringField(7,required=True)
	deleted = messages.BooleanField(8)

class ImageMessageCollection(messages.Message):
	items = messages.MessageField(ImageMessage, 1, repeated=True)	
	
class ReviewMessage(messages.Message):
	name = messages.StringField(4)
	description = messages.StringField(5)
	rating = messages.FloatField(6)
	tags = messages.StringField(7, repeated=True)
	last_modified = message_types.DateTimeField(8)
	date_created = message_types.DateTimeField(9)
	creator = messages.StringField(10)
	revobj_uuid = messages.StringField(11)
	uuid = messages.StringField(12)
	deleted = messages.BooleanField(13)

class ReviewMessageCollection(messages.Message):
	items = messages.MessageField(ReviewMessage, 1, repeated=True)

class GroupMessage(messages.Message):
	name = messages.StringField(1)
	owner = messages.StringField(2)
	last_modified = message_types.DateTimeField(3)
	uuid = messages.StringField(4)
	deleted = messages.BooleanField(5)

class GroupMessageCollection(messages.Message):
	items = messages.MessageField(GroupMessage, 1, repeated=True)	
	
class ReviewObjectMessage(messages.Message):
	name = messages.StringField(1)
	description = messages.StringField(2)
	creator = messages.StringField(3)
	lat = messages.FloatField(4)
	lon = messages.FloatField(5)
	tags = messages.StringField(6, repeated=True)
	last_modified = message_types.DateTimeField(7)
	uuid = messages.StringField(8)
	deleted = messages.BooleanField(9)

class ReviewObjectMessageCollection(messages.Message):
	items = messages.MessageField(ReviewObjectMessage, 1, repeated=True)	
	
class DeleteMessage(messages.Message):
	uuid = messages.StringField(1)
	last_modified = messages.StringField(2)
	
class DeleteMessageCollection(messages.Message):
	items = messages.MessageField(DeleteMessage, 1, repeated=True)

class GroupToReviewMessage(messages.Message):
	uuid = messages.StringField(1)
	last_modified = message_types.DateTimeField(2)
	deleted = messages.BooleanField(3)
	group = messages.StringField(4)
	review = messages.StringField(5)
	
class GroupToReviewMessageCollection(messages.Message):
	items = messages.MessageField(GroupToReviewMessage, 1, repeated=True)

class GroupToUserMessage(messages.Message):
	uuid = messages.StringField(1)
	last_modified = message_types.DateTimeField(2)
	deleted = messages.BooleanField(3)
	group = messages.StringField(4)
	user = messages.StringField(5)
	
class GroupToUserMessageCollection(messages.Message):
	items = messages.MessageField(GroupToUserMessage, 1, repeated=True)

class StringMessage(messages.Message):
	content = messages.StringField(1)
	
class DateMessage(messages.Message):
	date = messages.StringField(1)
	