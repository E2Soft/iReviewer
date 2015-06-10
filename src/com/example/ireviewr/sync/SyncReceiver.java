package com.example.ireviewr.sync;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.ireviewr.R;

public class SyncReceiver extends BroadcastReceiver {
	
	private static int notificationID = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		/*Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
		context.startService(new Intent(context,SyncService.class));*/
		
		if(intent.getAction().equals("SYNC_DATA")){
			String bla = intent.getExtras().getString("bla");
			
			NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
			
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
			mBuilder.setSmallIcon(R.drawable.ic_action_accept);
			mBuilder.setContentTitle("Notification Alert, Click Me!");
			mBuilder.setContentText("Hi, This is Android Notification Detail!");
			mBuilder.setLargeIcon(bm);
			
			// notificationID allows you to update the notification later on.
			mNotificationManager.notify(notificationID, mBuilder.build());
			
			//Toast.makeText(context, "I'm "+bla, Toast.LENGTH_SHORT).show();
		}
		
	}

}
