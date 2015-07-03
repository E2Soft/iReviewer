package com.example.ireviewr.adapters;

import android.content.Context;
import com.example.ireviewr.model.User;

public class GroupUsersCheckAdapter extends AbstractCheckArrayAdapter<User>
{
	private String groupId;
	
	public GroupUsersCheckAdapter(Context context, String groupId)
	{
		super(context);
		this.groupId = groupId;
	}
	
	@Override
	protected String getMainDataToDisplay(User item)
	{
		return item.getName();
	}

	@Override
	protected boolean isChecked(User item)
	{
		return item.isInGroup(groupId);
	}
}
