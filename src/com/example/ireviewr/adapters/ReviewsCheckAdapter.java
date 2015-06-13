package com.example.ireviewr.adapters;

import com.example.ireviewr.model.Review;

import android.content.Context;

public class ReviewsCheckAdapter extends AbstractCheckArrayAdapter<Review>
{
	public ReviewsCheckAdapter(Context context)
	{
		super(context);
	}
	
	@Override
	protected String getMainDataToDisplay(Review item)
	{
		return item.getName();
	}
}
