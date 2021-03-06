package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Tag;

public class TagsAdapter extends AbstractArrayAdapter<Tag>
{
	public TagsAdapter(Context context)
	{
		super(context, R.layout.tags_item);
	}

	@Override
	protected void populateView(View view, Tag item)
	{
		TextView textViewTitle = (TextView)view.findViewById(R.id.tag_name);
		textViewTitle.setText(item.getName());
	}
	
	@Override
	protected String getTextToFilter(Tag item)
	{
		return item.getName();
	}
}
