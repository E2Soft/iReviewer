package com.example.ireviewr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.UserItem;
import com.example.ireviewr.tools.ReviewerTools;

public class UserAdapter extends ArrayAdapter<UserItem>{

	private ArrayList<UserItem> items;
	private Context mContext;
	
	public UserAdapter(Context context, int resource, ArrayList<UserItem> items) {
		super(context, resource);
		this.items = items;
		this.mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.user_item, parent, false);
		}
		
		UserItem tag = items.get(position);
		
		TextView name = (TextView)itemView.findViewById(R.id.user_name);
		name.setText(tag.getUsername());
		
		TextView date = (TextView)itemView.findViewById(R.id.user_date_created);
		date.setText(ReviewerTools.preapreDate(tag.getLastModified()));
		
		
		return itemView;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public UserItem getItem(int position) {
		return items.get(position);
	}

}
