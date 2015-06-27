from google.appengine.ext import ndb
from google.appengine.ext.ndb import msgprop

class UserModel(ndb.Model):
	user_name = ndb.StringProperty(required=True)
	last_modified = ndb.DateTimeProperty()
	email = ndb.StringProperty(required=True)
	uuid = ndb.StringProperty(required=True)
	deleted = ndb.BooleanProperty(required=True)
	
class CommentModel(ndb.Model):
	content = ndb.StringProperty(required=True)
	creator = ndb.StringProperty(required=True)
	review_uuid = ndb.StringProperty(required=True)
	last_modified = ndb.DateTimeProperty()
	uuid = ndb.StringProperty(required=True)

class ImageModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	image = ndb.BlobProperty(required=True)
	uuid = ndb.StringProperty(required=True)
	review_uuid = ndb.StringProperty(required=True)
	revobj_uuid = ndb.StringProperty(required=True)
	last_modified = ndb.DateTimeProperty()
	deleted = ndb.BooleanProperty(required=True)

class ReviewModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	description = ndb.StringProperty(required=True)
	rating = ndb.FloatProperty(required=True)
	tags = ndb.StringProperty(repeated=True)
	reviewobj_uuid = ndb.StringProperty(required=True)
	last_modified = ndb.DateTimeProperty()
	creator = ndb.StringProperty(required=True)
	uuid = ndb.StringProperty(required=True)
	deleted = ndb.BooleanProperty(required=True)

class GroupModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	owner = ndb.StringProperty(required=True)
	users = ndb.StringProperty(repeated=True)
	reviews = ndb.StringProperty(repeated=True)
	last_modified = ndb.DateTimeProperty()
	reviews_changed = ndb.DateTimeProperty()
	users_changed = ndb.DateTimeProperty()
	uuid = ndb.StringProperty(required=True)
	deleted = ndb.BooleanProperty(required=True)

class ReviewObjectModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	description = ndb.StringProperty(required=True)
	creator = ndb.StringProperty(required=True)
	lat = ndb.FloatProperty(required=True)
	lon = ndb.FloatProperty(required=True)
	tags = ndb.StringProperty(repeated=True)
	last_modified = ndb.DateTimeProperty()
	uuid = ndb.StringProperty(required=True)
	deleted = ndb.BooleanProperty(required=True)
	
