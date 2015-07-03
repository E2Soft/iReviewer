package com.example.ireviewr.adapters;

import android.content.Context;

import com.example.ireviewr.model.Tag;

public abstract class TagsCheckAdapter extends AbstractCheckArrayAdapter<Tag>
{
	public TagsCheckAdapter(Context context)
	{
		super(context);
	}
	
	@Override
	protected String getMainDataToDisplay(Tag item)
	{
		return item.getName();
	}
}