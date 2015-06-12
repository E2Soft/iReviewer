package com.example.ireviewr.fragments.reviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewsAdapter;
import com.example.ireviewr.fragments.AbstractListFragment;
import com.example.ireviewr.model.Review;

public abstract class AbstractReviewsListFragment extends AbstractListFragment<Review>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public AbstractReviewsListFragment()
	{}
	
	public AbstractReviewsListFragment(String itemId)
	{
		Bundle bundle = new Bundle();
		bundle.putString(RELATED_ID, itemId);
		setArguments(bundle);
	}

	@Override
	protected AbstractArrayAdapter<Review> getAdapter()
	{
		return new ReviewsAdapter(getActivity());
	}

	@Override
	protected void onItemClick(String modelId)
	{
		Fragment fragment = ReviewTabFragment.newInstance(modelId);
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment).
												addToBackStack(null).commit();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		reloadData();
	}
}
