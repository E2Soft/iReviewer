package com.example.ireviewr.adapters;

import android.content.Context;

import com.example.ireviewr.model.Group;

public abstract class GroupsCheckAdapter extends AbstractCheckArrayAdapter<Group>
{
	public GroupsCheckAdapter(Context context)
	{
		super(context);
	}
	
	@Override
	protected String getMainDataToDisplay(Group item)
	{
		return item.getName();
	}
}
