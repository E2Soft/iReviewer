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
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.ImageUtils;
import com.example.ireviewr.tools.ReviewerTools;

public class ReviewDetailFragment extends Fragment {

	public static final String NAME ="NAME";
	public static final String DESCRIPTION ="DESCRIPTION";
	public static final String CREATED = "CREATED";
	public static final String LAST_MODIFIED = "LAST MODIFIED";
	public static final String IMAGE = "IMAGE";
	public static final String RATING = "RATING";
	public static final String ID = "ID";
	
	public static ReviewDetailFragment newInstance(Review review)
	{
		ReviewDetailFragment newFrag = new ReviewDetailFragment();
		newFrag.setArguments(new Bundle());
		newFrag.dataToArguments(review);
		return newFrag;
	}
	
	private void dataToArguments(Review review)
	{
		Bundle bundle = getArguments();
		bundle.putString(NAME, review.getName());
		bundle.putString(DESCRIPTION, review.getDescription());
		bundle.putString(CREATED,ReviewerTools.preapreDate(review.getDateCreated()));
		bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(review.getDateModified()));
		Image mainImage = review.getMainImage();
		if(mainImage != null)
		{
			bundle.putString(IMAGE, mainImage.getPath());
		}
		bundle.putDouble(RATING, review.getRating());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		
		populateView(view);
		
		return view;
	}
	
	private void populateView(View view)
	{
		Bundle bundle = getArguments();
		
		TextView name = (TextView)view.findViewById(R.id.review_name_content);
		name.setText(bundle.getString(NAME));
		
		TextView created = (TextView)view.findViewById(R.id.review_desc_contnt);
		created.setText(bundle.getString(CREATED));
		
		RatingBar rating = (RatingBar)view.findViewById(R.id.review_rating_content);
		rating.setRating((float)bundle.getDouble(RATING));
		
		TextView modified = (TextView)view.findViewById(R.id.review_modified_contnt);
		modified.setText(bundle.getString(LAST_MODIFIED));
		
		TextView description = (TextView)view.findViewById(R.id.review_description_contnt);
		description.setText(bundle.getString(DESCRIPTION));
		
		ImageView image = (ImageView)view.findViewById(R.id.review_image_content);
		ImageUtils.setImageFromPath(image, bundle.getString(IMAGE), 128, 128);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.detail);
		setHasOptionsMenu(true);
	}
	
}
