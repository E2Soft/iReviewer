package com.example.ireviewr.fragments.reviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ireviewr.R;

public class ReviewDetailFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.fragment_detail_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.edit_item:
				Toast.makeText(getActivity(), "Edit Review item pressed", Toast.LENGTH_LONG).show();
				return true;
			case R.id.delete_item:
				Toast.makeText(getActivity(), "Delete Review item pressed", Toast.LENGTH_LONG).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.review_detail, container, false);
		
		Bundle bundle = getArguments();
		
		TextView name = (TextView)view.findViewById(R.id.review_name_content);
		name.setText(bundle.getString("NAME"));
		
		TextView created = (TextView)view.findViewById(R.id.review_desc_contnt);
		created.setText(bundle.getString("CREATED"));
		
		RatingBar rating = (RatingBar)view.findViewById(R.id.review_rating_content);
		rating.setRating((float)bundle.getDouble("RATING"));
		
		TextView modified = (TextView)view.findViewById(R.id.review_modified_contnt);
		modified.setText(bundle.getString("LAST MODIFIED"));
		
		TextView description = (TextView)view.findViewById(R.id.review_description_contnt);
		description.setText(bundle.getString("DESCRIPTION"));
		
		ImageView image = (ImageView)view.findViewById(R.id.review_image_content);
		image.setImageResource(bundle.getInt("IMAGE"));
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.detail);
		setHasOptionsMenu(true);
	}
}
