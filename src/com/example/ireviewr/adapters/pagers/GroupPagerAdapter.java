package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ireviewr.fragments.groups.GroupDetailFragment;
import com.example.ireviewr.fragments.groups.GroupTabsFragment;
import com.example.ireviewr.fragments.groups.UserFragmentList;
import com.example.ireviewr.fragments.reviews.ReviewsGroupList;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.tools.ReviewerTools;

public class GroupPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	private String[] names ={"Detail","Reviews", "Users"};
	private Bundle bundle;
	
	public static String NAME = "NAME";
	public static String LAST_MODIFIED = "LAST MODIFIED";
	
	public GroupPagerAdapter(Bundle bundle, FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		this.bundle = bundle;
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		Group group = bundle.getParcelable(GroupTabsFragment.DATA);
		
		if(position == 0){
			Bundle bundle = new Bundle();
			bundle.putString(NAME, group.getName());
			bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(group.getLastModified()));
			
			fragment = new GroupDetailFragment();
			fragment.setArguments(bundle);
		}else if(position == 1){
			fragment = ReviewsGroupList.newInstance(group.getReviews());
		}else if(position == 2){
			fragment = UserFragmentList.newInstance(group.getUsers());
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
