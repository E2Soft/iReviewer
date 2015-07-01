package com.example.ireviewr.fragments.reviewobjects;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviews.AbstractReviewsListFragment;
import com.example.ireviewr.fragments.reviews.CreateReviewFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewObjectReviewsListFragment extends AbstractReviewsListFragment
{
	public ReviewObjectReviewsListFragment()
	{}
	
	public ReviewObjectReviewsListFragment(String itemId)
	{
		super(itemId, R.menu.standard_list_menu);
	}
	
	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter)
		{
			@Override
			protected List<Review> getData()
			{
				return getRevob().getReviews();
			}
		};
	}
	
	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		menu.findItem(R.id.menu_action)
		.setIcon(R.drawable.ic_action_new)
		.setTitle(R.string.add_item);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.menu_action:
				onMenuAction();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}

	private void onMenuAction()
	{
		FragmentTransition.to(CreateReviewFragment.newCreateInstance(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private ReviewObject getRevob()
	{
		return ReviewObject.getByModelId(ReviewObject.class, getArguments().getString(RELATED_ID));
	}
}