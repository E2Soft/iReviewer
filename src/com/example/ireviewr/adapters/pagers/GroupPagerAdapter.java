package com.example.ireviewr.adapters.pagers;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ireviewr.fragments.groups.GroupDetailFragment;
import com.example.ireviewr.fragments.groups.UserFragmentList;
import com.example.ireviewr.fragments.reviews.ReviewsGroupList;
import com.example.ireviewr.model.NavItem;

public class GroupPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	private String[] names ={"Detail","Reviews", "Users"};
	private ArrayList<NavItem> reviews;
	private ArrayList<NavItem> users;
	private Bundle bundle;
	
	public GroupPagerAdapter(Bundle bundle, FragmentManager fm, Context context, 
			ArrayList<NavItem> reviews, ArrayList<NavItem> users) {
		super(fm);
		this.context = context;
		this.reviews = reviews;
		this.users = users;
		this.bundle = bundle;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		if(position == 0){
			fragment = new GroupDetailFragment();
			fragment.setArguments(bundle);
		}else if(position == 1){
			fragment = ReviewsGroupList.newInstance(reviews);
		}else if(position == 2){
			fragment = UserFragmentList.newInstance(users);
		}
		
		return fragment;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return names[position];
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

}
