package com.example.ireviewr.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SyncReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		/*Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
		context.startService(new Intent(context,SyncService.class));*/
		
		if(intent.getAction().equals("SYNC_DATA")){
			String bla = intent.getExtras().getString("bla");
			
			Toast.makeText(context, "I'm "+bla, Toast.LENGTH_SHORT).show();
		}
		
	}

}
