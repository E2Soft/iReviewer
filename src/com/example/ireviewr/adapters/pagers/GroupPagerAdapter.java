package com.example.ireviewr.adapters.pagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.fragments.groups.GroupDetailFragment;
import com.example.ireviewr.fragments.groups.UserFragmentList;
import com.example.ireviewr.fragments.reviews.ReviewsGroupList;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.tools.ReviewerTools;

public class GroupPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail","Reviews", "Users"};
	private String itemId;
	
	public static String NAME = "NAME";
	public static String LAST_MODIFIED = "LAST MODIFIED";
	
	public GroupPagerAdapter(String itemId, FragmentManager fm)
	{
		super(fm);
		this.itemId = itemId;
	}
	
	@Override
	public Fragment getItem(int position)
	{
		switch(position)
		{
			case 0:
			{
				Group group = Group.getByModelId(Group.class, itemId);
				
				Bundle bundle = new Bundle();
				bundle.putString(NAME, group.getName());
				bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(group.getDateModified()));
				
				Fragment fragment = new GroupDetailFragment();
				fragment.setArguments(bundle);
				return fragment;
			}
			case 1:
			{
				return ReviewsGroupList.newInstance(itemId);
			}
			case 2:
			{
				return UserFragmentList.newInstance(itemId);
			}
			default:
			{
				Log.e("GroupPagerAdapter", "Internal error unknown slide position.");
				return null;
			}
		}
	}
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return names[position];
	}
	
	@Override
	public int getCount()
	{
		return names.length;
	}

}
