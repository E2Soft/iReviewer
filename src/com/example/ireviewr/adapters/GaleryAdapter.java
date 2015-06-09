package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.tools.ReviewerTools;

public class GaleryAdapter extends AbstractArrayAdapter<Image>
{
	public GaleryAdapter(Context context)
	{
		super(context, R.layout.galery_item);
	}

	@Override
	protected void populateView(View view, Image item)
	{
		//populate view
		ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
		ReviewerTools.setImageFromPath(imageView, item.getPath());
		
		TextView textViewTitle = (TextView)view.findViewById(R.id.text1);
		// TODO smisliti nesto bolje, npr izvuci filename nekako
		textViewTitle.setText(item.getPath());
	}
	
	@Override
	protected String getTextToFilter(Image item)
	{
		// TODO smisliti nesto bolje, npr izvuci filename nekako
		return item.getPath();
	}
}
