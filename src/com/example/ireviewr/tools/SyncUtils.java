package com.example.ireviewr.tools;

import com.appspot.elevated_surge_702.crud.Crud;
import com.appspot.elevated_surge_702.sync.Sync;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

public class SyncUtils
{
	public static Sync buildSyncApi()
	{
		return new Sync.Builder(
				AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
				.setRootUrl("http://10.0.3.2:8080/_ah/api/") // 10.0.3.2 je adresa pca sa genymotion emulatora
				.build();
	}
	
	public static Crud buildCrudApi()
	{
		return new Crud.Builder(
				AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
				.setRootUrl("http://10.0.3.2:8080/_ah/api/") // 10.0.3.2 je adresa pca sa genymotion emulatora
				.build();
	}
}
