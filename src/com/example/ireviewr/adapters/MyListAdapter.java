package com.example.ireviewr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.NavItem;

public class MyListAdapter extends ArrayAdapter<NavItem> implements Filterable {

 	private ArrayList<NavItem> items;
 	private ArrayList<NavItem> itemsOriginal;
 	private Context mContext;
	
	public MyListAdapter(Context context, int resource, ArrayList<NavItem> items) {
		super(context, resource,items);
		this.items = items;
		this.mContext = context;
		itemsOriginal = new ArrayList<NavItem>(this.items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_list_item, parent, false);
		}
		
		//get item
		NavItem navItem = items.get(position);
		
		//populate view
		ImageView imageView = (ImageView)itemView.findViewById(R.id.icon);
		imageView.setImageResource(navItem.getmIcon());
		
		TextView textViewTitle = (TextView)itemView.findViewById(R.id.title);
		textViewTitle.setText(navItem.getmTitle());
		
		TextView textViewDesc = (TextView)itemView.findViewById(R.id.subTitle);
		textViewDesc.setText(navItem.getmSubtitle());
		
		return itemView;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	@Override
	public NavItem getItem(int position) {
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
					ArrayList<NavItem> tempList = new ArrayList<NavItem>();

		            // search content in friend list
		            for (NavItem item : items) {
		                if (item.getmTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
				items = (ArrayList<NavItem>) results.values; // has the filtered values
                /*notifyDataSetChanged();  // notifies the data with new filtered values*/
				/*items = (ArrayList<NavItem>)results.values;
		        notifyDataSetChanged();
		        clear();
		        int count = items.size();
		        for(int i = 0; i<count; i++){
		            add(items.get(i));
		            notifyDataSetInvalidated();
		        }*/
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
