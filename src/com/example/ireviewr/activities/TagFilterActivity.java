package com.example.ireviewr.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.tags.TagFilterFragment;

public class TagFilterActivity extends FragmentActivity implements TagFilterChooser
{
	public static final String CHECKED_ITEMS = "CHECKED_ITEMS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_filter);
		
		ArrayList<String> checked;
		
		checked = getIntent().getStringArrayListExtra(CHECKED_ITEMS);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content, new TagFilterFragment(checked))
			.commit();
	}
	
	@Override
	public void returnFilterAsResult(ArrayList<String> filter)
	{
		Intent result = new Intent();
		result.putExtra(CHECKED_ITEMS, filter);
		setResult(Activity.RESULT_OK, result);
		finish();
	}
}