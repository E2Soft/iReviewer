package com.example.ireviewr.sync.auto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.sync.tasks.TestTask;
import com.example.ireviewr.tools.ReviewerTools;

public class SyncService extends Service {

	public static String RESULT_CODE = "RESULT_CODE";
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(getApplicationContext(), "onStartCommand()", Toast.LENGTH_SHORT).show();
		
		Intent ints = new Intent(MainActivity.SYNC_DATA);
		int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
		
		ints.putExtra(RESULT_CODE, status);
		
		//ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
		//TODO:Metodu dodati koja ce to raditi
		if(status == ReviewerTools.TYPE_WIFI){
			new TestTask(getApplicationContext()).execute();
		}
		
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
