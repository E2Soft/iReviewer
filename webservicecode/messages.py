from protorpc import message_types
from protorpc import messages

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
	uuid = messages.StringField(4, required=True)
	review_uuid = messages.StringField(5,required=True)
	revobj_uuid = messages.StringField(6,required=True)
	deleted = messages.BooleanField(7)

class ImageMessageCollection(messages.Message):
	items = messages.MessageField(ImageMessage, 1, repeated=True)	
	
class ReviewMessage(messages.Message):
	name = messages.StringField(4)
	description = messages.StringField(5)
	rating = messages.FloatField(6)
	tags = messages.StringField(7, repeated=True)
	last_modified = message_types.DateTimeField(8)
	creator = messages.StringField(9)
	revobj_uuid = messages.StringField(10)
	uuid = messages.StringField(11)
	deleted = messages.BooleanField(12)

class ReviewMessageCollection(messages.Message):
	items = messages.MessageField(ReviewMessage, 1, repeated=True)

class GroupMessage(messages.Message):
	name = messages.StringField(1)
	owner = messages.StringField(2)
	users = messages.StringField(3, repeated=True)
	reviews = messages.StringField(4, repeated=True)
	last_modified = message_types.DateTimeField(5)
	reviews_changed = message_types.DateTimeField(6)
	users_changed = message_types.DateTimeField(7)
	uuid = messages.StringField(8)
	deleted = messages.BooleanField(9)

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