package com.example.ireviewr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.ReviewerTools;

public class TagsAdapter extends ArrayAdapter<Tag> {

	private ArrayList<Tag> items;
	private Context mContext;
	
	public TagsAdapter(Context context, int resource, ArrayList<Tag> items) {
		super(context, resource);
		this.items = items;
		this.mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.tags_item, parent, false);
		}
		
		Tag tag = items.get(position);
		
		CheckedTextView textViewTitle = (CheckedTextView)itemView.findViewById(R.id.tag_name);
		textViewTitle.setText(tag.getName());
		
		TextView textViewDesc = (TextView)itemView.findViewById(R.id.tag_date_created);
		textViewDesc.setText(ReviewerTools.preapreDate(tag.getDateCreated()));
		
		return itemView;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public Tag getItem(int position) {
		return items.get(position);
	}

}
