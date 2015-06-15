package com.example.ireviewr.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.ireviewr.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ReviewerTools {

	public static String PATTERN = "dd.MM.yyyy";
	
	@SuppressLint("SimpleDateFormat")
	public static String preapreDate(Date date) {

		SimpleDateFormat formater = new SimpleDateFormat(PATTERN);

		return formater.format(date);
	}
	
	public static void setImageFromPath(ImageView imageView, String path)
	{
		setImageFromPath(imageView, path, null);
	}
	
	public static void setImageFromPath(ImageView imageView, String path, Integer defaultImage)
	{
		if(path != null)
		{
			File imgFile = new  File(path);
			if(imgFile.exists())
			{
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    imageView.setImageBitmap(myBitmap);
			    return ;
			}
		}
		
		if(defaultImage != null)
		{
			imageView.setImageResource(defaultImage);
		}
		else
		{
			imageView.setImageResource(R.drawable.ic_action_picture);
		}
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
}
