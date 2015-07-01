package com.example.ireviewr.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.fragments.TagsFragmentList;
import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;
import com.example.ireviewr.fragments.reviews.ReviewGalleryFragment;
import com.example.ireviewr.model.Review;

public class ReviewsPagerAdapter extends FragmentPagerAdapter
{
	private String[] names ={"Detail", "Comments", "Galery", "Tags"};
	private String itemId;
	
	public ReviewsPagerAdapter(String itemId, FragmentManager fm)
	{
		super(fm);
		this.itemId = itemId;
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
				return CommentsListFragment.newInstance(itemId);
			}
			case 2:
			{
				return new ReviewGalleryFragment(itemId);
			}
			case 3:
			{
				return TagsFragmentList.newInstance(itemId);
			}
			default:
			{
				Log.e("ReviewsPagerAdapter", "Internal error unknown slide position.");
				return null;
			}
		}
	}
}
