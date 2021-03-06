package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.groups.GroupDetailFragment;
import com.example.ireviewr.fragments.groups.GroupReviewsListFragment;
import com.example.ireviewr.fragments.users.GroupUsersListFragment;
import com.example.ireviewr.model.Group;

public class GroupPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail","Reviews", "Users"};
	private String itemId;
	
	public GroupPagerAdapter(String itemId, FragmentManager fm, Context context)
	{
		super(fm);
		this.itemId = itemId;
		names[0] = context.getString(R.string.detail);
		names[1] = context.getString(R.string.reviews);
		names[2] = context.getString(R.string.users);
	}
	
	@Override
	public Fragment getItem(int position)
	{
		switch(position)
		{
			case 0:
			{
				Group group = Group.getByModelId(Group.class, itemId);
				return GroupDetailFragment.newInstance(group);
			}
			case 1:
			{
				return new GroupReviewsListFragment(itemId);
			}
			case 2:
			{
				return new GroupUsersListFragment(itemId);
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
