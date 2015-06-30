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
	
def string_to_datetime(value):
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
	
def date_to_utc(date):
	return date.replace(tzinfo=None) - date.utcoffset()
