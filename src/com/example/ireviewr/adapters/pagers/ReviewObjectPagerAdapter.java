package com.example.ireviewr.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.fragments.reviewobjects.ReviewObjectDetailFragment;
import com.example.ireviewr.fragments.reviewobjects.ReviewObjectGalleryFragment;
import com.example.ireviewr.fragments.reviewobjects.ReviewObjectReviewsListFragment;
import com.example.ireviewr.model.ReviewObject;

public class ReviewObjectPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Details","Reviews", "Gallery"};
	private String itemId;
	
	public ReviewObjectPagerAdapter(String itemId, FragmentManager fm)
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
				ReviewObject revob = ReviewObject.getByModelId(ReviewObject.class, itemId);
				return ReviewObjectDetailFragment.newInstance(revob);
			}
			case 1:
			{
				return new ReviewObjectReviewsListFragment(itemId);
			}
			case 2:
			{
				return new ReviewObjectGalleryFragment(itemId);
			}
			//case 4:
			//{
			//	return new ReviewObjectTagListFragment(itemId);
			//}
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