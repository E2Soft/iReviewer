package com.example.ireviewr.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;

public class ReviewerTools {

	public static String PATTERN = "dd.MM.yyyy";
	
	@SuppressLint("SimpleDateFormat")
	public static String preapreDate(Date date) {

		SimpleDateFormat formater = new SimpleDateFormat(PATTERN);

		return formater.format(date);
	}

	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;

	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public static String getConnectivityStatusString(Context context) {
		int conn = getConnectivityStatus(context);
		String status = null;
		if (conn == TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}
	
	public static String getShortString(String longString, int maxLength)
	{
		if(longString.length() <= maxLength)
		{
			return longString;
		}
		else
		{
			return longString.substring(0, maxLength-3)+"...";
		}
	}
	
	public static String getTagsString(List<String> tagList)
	{
		StringBuilder tagsString = new StringBuilder();
		for(String tag : tagList)
		{
			tagsString.append(" #").append(tag);
		}
		return tagsString.toString();
	}
	
	public static double haversineDistance(double lat1, double lon1, double lat2, double lon2)
	{
		double R = 6371;
		double dLat = (lat2 - lat1) * PI / 180;
		double dLon = (lon2 - lon1) * PI / 180;
		lat1 = lat1 * PI / 180;
		lat2 = lat2 * PI / 180;
		
		double a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2);
		double c = 2 * atan2(sqrt(a), sqrt(1-a));
		double d = R * c;
		
		return d;
	}
}
