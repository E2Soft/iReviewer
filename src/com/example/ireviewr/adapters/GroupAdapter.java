package com.example.ireviewr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.tools.ReviewerTools;

public class GroupAdapter extends ArrayAdapter<Group> {

	private ArrayList<Group> items;
 	private ArrayList<Group> itemsOriginal;
 	private Context mContext;
	
	public GroupAdapter(Context context, int resource, ArrayList<Group> items) {
		super(context, resource,items);
		this.items = items;
		this.mContext = context;
		itemsOriginal = new ArrayList<Group>(this.items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.group_item, parent, false);
		}
		
		//get item
		Group navItem = items.get(position);
		
		TextView title = (TextView)itemView.findViewById(R.id.group_name);
		title.setText(navItem.getName());
		
		TextView dateCreated = (TextView)itemView.findViewById(R.id.group_date_created);
		dateCreated.setText(ReviewerTools.preapreDate(navItem.getLastModified()));
		
		TextView numberOfUsers = (TextView)itemView.findViewById(R.id.group_users);
		numberOfUsers.setText("Users:"+navItem.getUsers().size());
		
		return itemView;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	@Override
	public Group getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				
				if (constraint != null && constraint.toString().length() > 0) {
					ArrayList<Group> tempList = new ArrayList<Group>();

		            // search content in friend list
		            for (Group item : items) {
		                if (item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
		                    tempList.add(item);
		                }
		            }

		            filterResults.count = tempList.size();
		            filterResults.values = tempList;
		        }else{
		            filterResults.values = itemsOriginal;
		            filterResults.count = itemsOriginal.size();
		        }

		        return filterResults;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				items = (ArrayList<Group>) results.values; // has the filtered values

				// Now we have to inform the adapter about the new list filtered
		        if (results.count > 0)
					notifyDataSetChanged();
		        else {
		            notifyDataSetInvalidated();
		        }
			}
			
		};
		
		return filter;
	}

}
