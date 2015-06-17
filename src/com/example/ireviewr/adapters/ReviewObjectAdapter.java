package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.tools.ReviewerTools;

public class ReviewObjectAdapter extends AbstractArrayAdapter<ReviewObject>
{
	public ReviewObjectAdapter(Context context)
	{
		super(context, R.layout.review_object_item);
	}

	@Override
	protected void populateView(View view, ReviewObject item)
	{
		TextView name = (TextView)view.findViewById(R.id.revob_name);
		name.setText(item.getName());
		
		TextView date = (TextView)view.findViewById(R.id.revob_short_description);
		date.setText(ReviewerTools.getShortString(item.getDescription(), 20));
		
		RatingBar rating = (RatingBar)view.findViewById(R.id.revob_rating_list);
		rating.setRating(item.getAverageRating());
		
		ImageView image = (ImageView)view.findViewById(R.id.revob_item_icon);
		Image mainImage = item.getMainImage();
		String imagePath = (mainImage != null) ? mainImage.getPath() : null;
		ReviewerTools.setImageFromPath(image, imagePath, R.drawable.ic_action_place);
	}
	
	@Override
	protected String getTextToFilter(ReviewObject item)
	{
		return item.getName();
	}
}
