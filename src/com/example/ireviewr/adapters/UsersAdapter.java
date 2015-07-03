package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.ReviewerTools;

public class UsersAdapter extends AbstractArrayAdapter<User>
{
	public UsersAdapter(Context context)
	{
		super(context, R.layout.user_item);
	}

	@Override
	protected void populateView(View view, User item)
	{
		TextView name = (TextView)view.findViewById(R.id.user_name);
		name.setText(item.getName());
		
		TextView date = (TextView)view.findViewById(R.id.user_date_created);
		date.setText(ReviewerTools.preapreDate(item.getDateModified()));
	}
	
	@Override
	protected String getTextToFilter(User item)
	{
		return item.getName();
	}
}
