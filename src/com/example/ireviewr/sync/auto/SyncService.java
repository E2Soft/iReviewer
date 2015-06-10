package com.example.ireviewr.sync.auto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SyncService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Toast.makeText(getApplicationContext(), "onStartCommand()", Toast.LENGTH_SHORT).show();
		
		Intent ints = new Intent("SYNC_DATA");
		ints.putExtra("bla", "bla");
		
		sendBroadcast(ints);
		
		stopSelf();
		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
