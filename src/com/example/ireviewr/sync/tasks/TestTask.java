package com.example.ireviewr.sync.tasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appspot.webservice.crud.Crud;
import com.appspot.webservice.crud.model.MessagesReviewMessage;
import com.appspot.webservice.crud.model.MessagesReviewMessageCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

public class TestTask extends AsyncTask<Void, Void, MessagesReviewMessageCollection>{
	Context ctx;
	
	public TestTask(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	protected MessagesReviewMessageCollection doInBackground(Void... params) {
		
		Crud.Builder builder = new Crud.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
		builder.setRootUrl("http://192.168.1.6:8080/_ah/api/");//ovo nije potrebno ako se servis izvrsava u produkciji.Dodati adresu koju ima masina
		
		Crud api = builder.build();

		try {
			Crud.Review.List review = api.review().list();
			MessagesReviewMessageCollection collection =  review.execute();
			
			return collection;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(MessagesReviewMessageCollection result) {
		
		if(result != null){
			String rez = "";
			for (MessagesReviewMessage img : result.getItems()) {
				rez+=img.getName()+"\n";
			}
			
			Toast.makeText(ctx, rez, Toast.LENGTH_LONG).show();
			
		}else{
			Toast.makeText(ctx, "Nista nije vratio :(", Toast.LENGTH_LONG).show();
		}
	}
	
}
