package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.ireviewr.model.Review;

public class ReviewImagePagerAdapter extends ImagePagerAdapter
{
	public ReviewImagePagerAdapter(String relatedId, FragmentManager fm, Context context)
	{
		super(relatedId, fm, context);
	}

	@Override
	protected void refreshState()
	{
		images = getReview().getImages();
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, relatedId);
	}
}