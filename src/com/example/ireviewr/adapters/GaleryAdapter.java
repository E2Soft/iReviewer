package com.example.ireviewr.adapters;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.model.GaleryItem;

public class GaleryAdapter extends ArrayAdapter<GaleryItem>{

	private ArrayList<GaleryItem> items;
 	private ArrayList<GaleryItem> itemsOriginal;
 	private Context mContext;
	
	public GaleryAdapter(Context context, int resource, ArrayList<GaleryItem> items) {
		super(context, resource,items);
		this.items = items;
		this.mContext = context;
		itemsOriginal = new ArrayList<GaleryItem>(this.items);
	}
	
	private void setImageFromPath(ImageView imageView, String path){
		File imgFile = new  File(path);

		if(imgFile.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    imageView.setImageBitmap(myBitmap);
		}else{
			imageView.setImageResource(R.drawable.ic_action_picture);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.galery_item, parent, false);
		}
		
		//get item
		GaleryItem navItem = items.get(position);
		
		//populate view
		ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView1);
		setImageFromPath(imageView, navItem.getPath());
		
		TextView textViewTitle = (TextView)itemView.findViewById(R.id.text1);
		textViewTitle.setText(navItem.getName());
		
		return itemView;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	@Override
	public GaleryItem getItem(int position) {
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
					ArrayList<GaleryItem> tempList = new ArrayList<GaleryItem>();

		            // search content in friend list
		            for (GaleryItem item : items) {
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
				items = (ArrayList<GaleryItem>) results.values; // has the filtered values
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
