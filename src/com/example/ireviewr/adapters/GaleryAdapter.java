package com.example.ireviewr.adapters;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Image;

public class GaleryAdapter extends AbstractArrayAdapter<Image>
{
	public GaleryAdapter(Context context)
	{
		super(context, R.layout.galery_item);
	}
	
	private void setImageFromPath(ImageView imageView, String path)
	{
		File imgFile = new  File(path);

		if(imgFile.exists())
		{
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    imageView.setImageBitmap(myBitmap);
		}
		else{
			imageView.setImageResource(R.drawable.ic_action_picture);
		}
	}

	@Override
	protected void populateView(View view, Image item)
	{
		//populate view
		ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
		setImageFromPath(imageView, item.getPath());
		
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
