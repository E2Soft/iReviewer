package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.ireviewr.R;
import com.example.ireviewr.loaders.BitmapWorkerTask;
import com.example.ireviewr.model.Image;

public class GaleryAdapter extends AbstractArrayAdapter<Image>
{
	public GaleryAdapter(Context context)
	{
		super(context, R.layout.galery_item);
	}
	
	@Override
	protected void populateView(View view, Image item)
	{
		// populate view
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		BitmapWorkerTask.loadBitmap(item.getPath(), imageView, 100, 100, R.drawable.ic_action_picture, getContext());
	}
}
