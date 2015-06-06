package com.example.ireviewr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ireviewr.R;

public class SplashScreenActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_detail);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle bundle = getIntent().getExtras();
		String title = (String)bundle.get("TITLE");
		String test = (String)bundle.get("TEXT");
		Integer icon = (Integer)bundle.get("ICON");
		
		TextView gTitle = (TextView)findViewById(R.id.group_title);
		gTitle.setText(title);
		
		TextView gText = (TextView)findViewById(R.id.group_subTitle);
		gText.setText(test);
		
	}
	
}
