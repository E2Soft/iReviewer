package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.ireviewr.fragments.reviewobjects.ReviewObjectImageDetailFragment;
import com.example.ireviewr.model.ReviewObject;

public class ReviewObjectImagePagerAdapter extends ImagePagerAdapter
{
	public ReviewObjectImagePagerAdapter(String relatedId, FragmentManager fm, Context context)
	{
		super(relatedId, fm, context);
	}

	@Override
	protected void refreshState()
	{
		images = getRevob().getImages();
	}
	
	private ReviewObject getRevob()
	{
		return ReviewObject.getByModelId(ReviewObject.class, relatedId);
	}
	
	@Override
	public Fragment getItem(int position)
	{
		return new ReviewObjectImageDetailFragment(images.get(position));
	}
}
