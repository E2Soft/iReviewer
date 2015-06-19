package com.example.ireviewr.adapters.pagers;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ireviewr.loaders.ModelObserver;
import com.example.ireviewr.model.Image;

public abstract class ImagePagerAdapter extends FragmentStatePagerAdapter
{
	protected String relatedId;
	protected List<Image> images;
	private ModelObserver modelObserver;
	
	@SuppressWarnings("unchecked")
	public ImagePagerAdapter(String relatedId, FragmentManager fm, Context context)
	{
		super(fm);
		this.relatedId = relatedId;
		
		modelObserver = new ModelObserver(context, Image.class)
		{
			@Override
			public void onChange(boolean selfChange, Uri uri)
			{
				refreshState();
			}
		};
		
		refreshState();
	}
	
	public void unregisterObservers()
	{
		modelObserver.unRegister();
	}
	
	protected abstract void refreshState();
	
	@Override
	public int getCount()
	{
		return images.size();
	}
}