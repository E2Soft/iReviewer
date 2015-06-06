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
import com.example.ireviewr.model.Comment;
import com.example.ireviewr.tools.ReviewerTools;

public class CommentsAdapter extends ArrayAdapter<Comment>{

	private ArrayList<Comment> items;
	private Context mContext;
	private ArrayList<Comment> itemsOriginal;
	
	public CommentsAdapter(Context context, int resource, ArrayList<Comment> items) {
		super(context, resource);
		this.items = items;
		this.mContext = context;
		this.itemsOriginal = new ArrayList<Comment>(this.items);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public Comment getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.comment_item, parent, false);
		}
		
		Comment comment = items.get(position);
		
		TextView content = (TextView)itemView.findViewById(R.id.content);
		content.setText(comment.getContent());
		
		TextView userCreated = (TextView)itemView.findViewById(R.id.user_created);
		userCreated.setText(comment.getUserCreated());
		
		TextView dateCreated = (TextView)itemView.findViewById(R.id.date_created);
		dateCreated.setText(ReviewerTools.preapreDate(comment.getDateCreated()));
		
		return itemView;
	}
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				
				if (constraint != null && constraint.toString().length() > 0) {
					ArrayList<Comment> tempList = new ArrayList<Comment>();
					
					// search content in friend list
		            for (Comment item : items) {
		                if (item.getContent().toLowerCase().contains(constraint.toString().toLowerCase()) ||
		                		item.getUserCreated().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
				items = (ArrayList<Comment>) results.values; // has the filtered values
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
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	public ArrayList<Comment> getItemsOriginal() {
		return itemsOriginal;
	}
}
