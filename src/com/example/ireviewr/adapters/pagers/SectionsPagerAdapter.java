package com.example.ireviewr.adapters.pagers;

import com.example.ireviewr.fragments.AboutFragment;
import com.example.ireviewr.fragments.PreferencesFragment;
import com.example.ireviewr.fragments.reviewobjects.CreateReviewObjectFragment;
import com.example.ireviewr.fragments.reviews.CreateReviewFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter{

	private Context context;
	
	public SectionsPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		if(position == 0){
			fragment = new CreateReviewFragment();
		}else if(position == 1){
			fragment = new AboutFragment();
		}else if(position == 2){
			fragment = new PreferencesFragment();
		}else if(position == 3){
			fragment = new CreateReviewObjectFragment();
		}
		
		return fragment;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return "Page #"+position;
	}
	
	@Override
	public int getCount() {
		return 4;
	}

}
