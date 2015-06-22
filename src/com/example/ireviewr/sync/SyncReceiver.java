package com.example.ireviewr.sync;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.activities.ReviewerPreferenceActivity;
import com.example.ireviewr.sync.auto.SyncService;
import com.example.ireviewr.tools.ReviewerTools;

public class SyncReceiver extends BroadcastReceiver {
	
	private static int notificationID = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		/*Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
		context.startService(new Intent(context,SyncService.class));*/
		
		if(intent.getAction().equals(MainActivity.SYNC_DATA)){
			
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			boolean allowSyncNotif = sharedPreferences.getBoolean(context.getString(R.string.notif_on_sync_key), false);
			
			if(allowSyncNotif){
				int resultCode = intent.getExtras().getInt(SyncService.RESULT_CODE);
				
				NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
				Bitmap bm = null;
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
				
				Intent wiFiintent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				PendingIntent pIntent = PendingIntent.getActivity(context, 0, wiFiintent, 0);
				
				Intent settingsIntent = new Intent(context, ReviewerPreferenceActivity.class);
				PendingIntent pIntentSettings = PendingIntent.getActivity(context, 0, settingsIntent, 0);
				
				if(resultCode == ReviewerTools.TYPE_NOT_CONNECTED){
					bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_network_wifi);
					mBuilder.setSmallIcon(R.drawable.ic_action_error);
					mBuilder.setContentTitle("Automatic Sync problem");
					mBuilder.setContentText("Bad news, no internet connection");
					mBuilder.addAction(R.drawable.ic_action_network_wifi, context.getString(R.string.turn_wifi_on), pIntent);
					mBuilder.addAction(R.drawable.ic_action_settings, context.getString(R.string.turn_notif_on), pIntentSettings);
				}else if(resultCode == ReviewerTools.TYPE_MOBILE){
					bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_network_cell);
					mBuilder.setSmallIcon(R.drawable.ic_action_warning);
					mBuilder.setContentTitle("Automatic Sync warning");
					mBuilder.setContentText("Please connect to wifi to sync data");
					mBuilder.addAction(R.drawable.ic_action_network_wifi, context.getString(R.string.turn_wifi_on), pIntent);
					mBuilder.addAction(R.drawable.ic_action_settings, context.getString(R.string.turn_notif_on), pIntentSettings);
				}else{
					bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
					mBuilder.setSmallIcon(R.drawable.ic_action_refresh_w);
					mBuilder.setContentTitle("Automatic Sync");
					mBuilder.setContentText("Good news, everything is sync now.");
					mBuilder.addAction(R.drawable.ic_action_settings, context.getString(R.string.turn_notif_on), pIntentSettings);
				}
	
				
				mBuilder.setLargeIcon(bm);
				// notificationID allows you to update the notification later on.
				mNotificationManager.notify(notificationID, mBuilder.build());
				
				//Toast.makeText(context, "I'm "+bla, Toast.LENGTH_SHORT).show();
			}
		}
		
	}

}
