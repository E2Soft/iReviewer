package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.example.ireviewr.R;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.tools.ReviewerTools;

public class GroupAdapter extends AbstractArrayAdapter<Group>
{
	public GroupAdapter(Context context)
	{
		super(context, R.layout.group_item);
	}

	@Override
	protected void populateView(View view, Group item)
	{
		TextView title = (TextView)view.findViewById(R.id.group_name);
		title.setText(item.getName());
		
		TextView dateCreated = (TextView)view.findViewById(R.id.group_date_created);
		dateCreated.setText(ReviewerTools.preapreDate(item.getDateModified()));
		
		TextView numberOfUsers = (TextView)view.findViewById(R.id.group_users);
		numberOfUsers.setText("Users:"+item.getUsers().size());
	}
	
	@Override
	protected String getTextToFilter(Group item)
	{
		// filtriraj po imenu grupe
		return item.getName();
	}
}
