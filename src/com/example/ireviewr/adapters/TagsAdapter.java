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
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.ReviewerTools;

public class TagsAdapter extends ArrayAdapter<Tag> {

	private ArrayList<Tag> items;
	private Context mContext;
	private ArrayList<Tag> itemsOriginal;
	
	public TagsAdapter(Context context, int resource, ArrayList<Tag> items) {
		super(context, resource);
		this.items = items;
		this.mContext = context;
		this.itemsOriginal = new ArrayList<Tag>(this.items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.tags_item, parent, false);
		}
		
		Tag tag = items.get(position);
		
		TextView textViewTitle = (TextView)itemView.findViewById(R.id.tag_name);
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
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				
				if (constraint != null && constraint.toString().length() > 0) {
					ArrayList<Tag> tempList = new ArrayList<Tag>();
					
					// search content in friend list
		            for (Tag item : items) {
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
			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				items = (ArrayList<Tag>) results.values; // has the filtered values
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

	public ArrayList<Tag> getItemsOriginal() {
		return itemsOriginal;
	}

}
