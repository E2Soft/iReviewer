package com.example.ireviewr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.ReviewItem;

public class ReviewsAdapter extends ArrayAdapter<ReviewItem>{

	private ArrayList<ReviewItem> items;
	private Context mContext;
	
	public ReviewsAdapter(Context context, int resource, ArrayList<ReviewItem> items) {
		super(context, resource);
		this.items = items;
		this.mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.review_item, parent, false);
		}
		
		ReviewItem tag = items.get(position);
		
		TextView name = (TextView)itemView.findViewById(R.id.review_name);
		name.setText(tag.getName());
		
		TextView date = (TextView)itemView.findViewById(R.id.review_date_created);
		date.setText(tag.getCreated().toString());
		
		
		return itemView;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public ReviewItem getItem(int position) {
		return items.get(position);
	}

}
