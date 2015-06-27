import datetime
import endpoints
from protorpc import messages
import httplib
from protorpc import message_types

date_time_format = '%Y-%m-%dT%H:%M:%S'
time_constatn = 'T'

def gereate_now_to_string():
	current_time = datetime.datetime.now()
	
	day = current_time.day
	month = current_time.month
	year = current_time.year
	
	minute = current_time.minute
	hour = current_time.hour
	second = current_time.second
	
	#2015-5-3T2:4:6
	return '{}-{}-{}T{}:{}:{}'.format(year,month,day,hour,minute,second)

def string_to_datetime():
	return datetime.datetime.strptime(gereate_now_to_string(), date_time_format)
	
def resource_to_datetime(value):
	return datetime.datetime.strptime(value, date_time_format)
	
def calculate_distance(lat1, lon1, lat2, lon2 ):
	R = 6371;
	dLat = (lat2 - lat1) * pi / 180
	dLon = (lon2 - lon1) * pi / 180
	lat1 = lat1 * pi / 180
	lat2 = lat2 * pi / 180
	
	a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2)
	c = 2 * atan2(sqrt(a), sqrt(1-a))
	d = R * c
		
	return d
	
class ConflictException(endpoints.ServiceException):
  """Conflict exception that is mapped to a 409 response."""
  http_status = httplib.CONFLICT
	
UUID_RESOURCE = endpoints.ResourceContainer(
		message_types.VoidMessage,
		uuid=messages.StringField(1, variant=messages.Variant.STRING))
		
DATE_RESOURCE = endpoints.ResourceContainer(
		message_types.VoidMessage,
		last_modified=messages.StringField(1, variant=messages.Variant.STRING))
		
UUID_RESOURCE_LIST = endpoints.ResourceContainer(
		message_types.VoidMessage,
		items=messages.StringField(1, variant=messages.Variant.STRING, repeated=True))