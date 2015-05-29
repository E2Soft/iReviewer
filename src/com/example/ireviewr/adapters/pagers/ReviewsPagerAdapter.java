package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;

public class ReviewsPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail","Comments", "Galery"};
	private Context context;
	private Bundle bundle;
	
	public ReviewsPagerAdapter(Bundle bundle, FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		this.bundle = bundle;
	}
	
	@Override
	public int getCount() {
		return names.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return names[position];
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		if(position == 0){
			fragment = new ReviewDetailFragment();
		}else if(position == 1){
			fragment = new CommentsListFragment(null);
		}else if(position == 2){
			
		}
		
		return fragment;
	}

}
