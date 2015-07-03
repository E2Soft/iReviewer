package com.example.ireviewr.adapters;

import android.content.Context;

import com.example.ireviewr.model.Tag;

public abstract class TagCheckAdapter extends AbstractCheckArrayAdapter<Tag>
{
	public TagCheckAdapter(Context context)
	{
		super(context);
	}
	
	@Override
	protected String getMainDataToDisplay(Tag item)
	{
		return item.getName();
	}
}