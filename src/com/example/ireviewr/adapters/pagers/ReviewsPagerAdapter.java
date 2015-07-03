package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;
import com.example.ireviewr.fragments.reviews.ReviewGalleryFragment;
import com.example.ireviewr.fragments.reviews.ReviewGroupsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewTagsListFragment;
import com.example.ireviewr.model.Review;

public class ReviewsPagerAdapter extends FragmentPagerAdapter
{
	private String[] names ={"Detail", "Comments", "Galery", "Groups", "Tags"};
	private String itemId;
	
	public ReviewsPagerAdapter(String itemId, FragmentManager fm, Context context)
	{
		super(fm);
		this.itemId = itemId;
		names[0] = context.getString(R.string.detail);
		names[1] = context.getString(R.string.comments);
		names[2] = context.getString(R.string.gallery);
		names[3] = context.getString(R.string.groups);
		names[4] = context.getString(R.string.tags);
	}
	
	@Override
	public int getCount()
	{
		return names.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return names[position];
	}
	
	@Override
	public Fragment getItem(int position)
	{
		switch(position)
		{
			case 0:
			{
				Review review = Review.getByModelId(Review.class, itemId);
				return ReviewDetailFragment.newInstance(review);			
			}
			case 1:
			{
				return new CommentsListFragment(itemId);
			}
			case 2:
			{
				return new ReviewGalleryFragment(itemId);
			}
			case 3:
			{
				return new ReviewGroupsListFragment(itemId);
			}
			case 4:
			{
				return new ReviewTagsListFragment(itemId);
			}
			default:
			{
				Log.e("ReviewsPagerAdapter", "Internal error unknown slide position.");
				return null;
			}
		}
	}
}
