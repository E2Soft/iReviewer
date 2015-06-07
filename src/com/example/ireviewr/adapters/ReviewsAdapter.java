package com.example.ireviewr.adapters;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.ReviewerTools;

public class ReviewsAdapter extends AbstractArrayAdapter<Review>
{
	public ReviewsAdapter(Context context)
	{
		super(context, R.layout.review_item);
	}
	
	private void setImageFromPath(ImageView imageView, String path)
	{
		File imgFile = new  File(path);

		if(imgFile.exists())
		{
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    imageView.setImageBitmap(myBitmap);
		}
		else
		{
			imageView.setImageResource(R.drawable.ic_action_picture);
		}
	}

	@Override
	protected void populateView(View view, Review item)
	{
		TextView name = (TextView)view.findViewById(R.id.review_name);
		name.setText(item.getName());
		
		TextView date = (TextView)view.findViewById(R.id.review_date_created);
		date.setText(ReviewerTools.preapreDate(item.getDateCreated()));
		
		RatingBar rating = (RatingBar)view.findViewById(R.id.review_rating_list);
		rating.setRating((float)item.getRating());
		
		// TODO mozda dodati main image u model ??
		/*ImageView image = (ImageView)view.findViewById(R.id.review_item_icon);
		setImageFromPath(image, item.geti().getPath());*/
	}
	
	@Override
	protected String getTextToFilter(Review item)
	{
		return item.getName();
	}
}
