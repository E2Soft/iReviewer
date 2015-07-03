package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviewobjects.ReviewObjectDetailFragment;
import com.example.ireviewr.fragments.reviewobjects.ReviewObjectGalleryFragment;
import com.example.ireviewr.fragments.reviewobjects.ReviewObjectReviewsListFragment;
import com.example.ireviewr.model.ReviewObject;

public class ReviewObjectPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail","Reviews", "Gallery"};
	private String itemId;
	
	public ReviewObjectPagerAdapter(String itemId, FragmentManager fm, Context context)
	{
		super(fm);
		this.itemId = itemId;
		names[0] = context.getString(R.string.detail);
		names[1] = context.getString(R.string.reviews);
		names[2] = context.getString(R.string.gallery);
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