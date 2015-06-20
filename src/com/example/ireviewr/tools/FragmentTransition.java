package com.example.ireviewr.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.ireviewr.R;

public class FragmentTransition
{
	public static void to(Fragment newFragment, FragmentActivity activity)
	{
		to(newFragment, activity, true);
	}
	
	public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackstack)
	{
		FragmentTransaction transaction = activity.getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.mainContent, newFragment);
		if(addToBackstack) transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public static void remove(Fragment fragment, FragmentActivity activity)
	{
		remove(fragment, activity, true);
	}
	
	public static void remove(Fragment fragment, FragmentActivity activity, boolean popBackstack)
	{
		activity.getSupportFragmentManager().beginTransaction()
			.remove(fragment)
			.commit();
		if(popBackstack) activity.getSupportFragmentManager().popBackStack();
	}
}
