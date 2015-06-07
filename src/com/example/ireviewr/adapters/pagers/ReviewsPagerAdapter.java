package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.ireviewr.fragments.GaleryGridFragment;
import com.example.ireviewr.fragments.TagsFragmentList;
import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.ReviewerTools;

public class ReviewsPagerAdapter extends FragmentPagerAdapter
{
	private String[] names ={"Detail", "Comments", "Galery", "Tags"};
	private String itemId;
	
	public static String NAME ="NAME";
	public static String DESCRIPTION ="DESCRIPTION";
	public static String CREATED = "CREATED";
	public static String LAST_MODIFIED = "LAST MODIFIED";
	public static String IMAGE = "IMAGE";
	public static String RATING = "RATING";
	
	public ReviewsPagerAdapter(String itemId, FragmentManager fm, Context context)
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
				
				Bundle bundle = new Bundle();
				bundle.putString(NAME, review.getName());
				bundle.putString(DESCRIPTION, review.getDescription());
				bundle.putString(CREATED,ReviewerTools.preapreDate(review.getDateCreated()));
				bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(review.getDateModified()));
				//bundle.putString(IMAGE, review.getPicture().getPath()); // TODO
				bundle.putDouble(RATING, review.getRating());
				
				Fragment fragment = new ReviewDetailFragment();
				fragment.setArguments(bundle);
				return fragment;
			}
			case 1:
			{
				return CommentsListFragment.newInstance(itemId);
			}
			case 2:
			{
				return GaleryGridFragment.newInstance(itemId);
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
