package com.example.ireviewr.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.fragments.groups.GroupDetailFragment;
import com.example.ireviewr.fragments.groups.UserFragmentList;
import com.example.ireviewr.fragments.reviews.GroupReviewsListFragment;
import com.example.ireviewr.model.Group;

public class GroupPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Details","Reviews", "Users"};
	private String itemId;
	
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
				return new GroupDetailFragment(group);
			}
			case 1:
			{
				return new GroupReviewsListFragment(itemId);
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
