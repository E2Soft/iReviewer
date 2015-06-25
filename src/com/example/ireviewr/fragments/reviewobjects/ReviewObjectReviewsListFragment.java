package com.example.ireviewr.fragments.reviewobjects;

import java.util.ArrayList;
import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviews.AbstractReviewsListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.CurrentUser;

public class ReviewObjectReviewsListFragment extends AbstractReviewsListFragment
{
	private List<Tag> tagFilter; // TODO popuniti tagFilter, issue #22
	
	public ReviewObjectReviewsListFragment()
	{
		tagFilter = new ArrayList<Tag>();
	}
	
	public ReviewObjectReviewsListFragment(String itemId)
	{
		super(itemId, R.menu.standard_list_menu);
		tagFilter = new ArrayList<Tag>();
	}
	
	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter)
		{
			@Override
			protected List<Review> getData()
			{
				return getRevob().getFilteredReviews(tagFilter, CurrentUser.getModel(context));
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
		// FragmentTransition.to(new CreateReviewFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private ReviewObject getRevob()
	{
		return ReviewObject.getByModelId(ReviewObject.class, getArguments().getString(RELATED_ID));
	}
}