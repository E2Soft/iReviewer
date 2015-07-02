package com.example.ireviewr.sync.auto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ireviewr.sync.tasks.SyncTask;
import com.example.ireviewr.tools.ReviewerTools;

public class SyncService extends Service {

	public static String RESULT_CODE = "RESULT_CODE";
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//Intent ints = new Intent(MainActivity.SYNC_DATA);
		int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
		
		//ints.putExtra(RESULT_CODE, status);
		
		//ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
		if(status == ReviewerTools.TYPE_WIFI){
			new SyncTask(getApplicationContext()).execute();
		}
		
		//sendBroadcast(ints);
		
		stopSelf();
		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
