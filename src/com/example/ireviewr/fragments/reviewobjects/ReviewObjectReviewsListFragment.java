package com.example.ireviewr.fragments.reviewobjects;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.ireviewr.R;
import com.example.ireviewr.activities.TagFilterActivity;
import com.example.ireviewr.fragments.reviews.AbstractReviewsListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.CurrentUser;

public class ReviewObjectReviewsListFragment extends AbstractReviewsListFragment
{
	public static int REQUEST_TAG_FILTER = 0;
	public static String TAG_FILTER = "TAG_FILTER";
	private ArrayList<String> tagFilter;
	
	public ReviewObjectReviewsListFragment()
	{}
	
	public ReviewObjectReviewsListFragment(String itemId)
	{
		super(itemId, R.menu.review_list_menu);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null)
		{
			tagFilter = savedInstanceState.getStringArrayList(TAG_FILTER);
		}
		else
		{
			tagFilter = new ArrayList<String>();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putStringArrayList(TAG_FILTER, tagFilter);
	}
	
	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter)
		{
			@Override
			protected List<Review> getData()
			{
				List<Tag> tags = new ArrayList<Tag>();
				for(String tagId : tagFilter)
				{
					tags.add(Tag.getByModelId(Tag.class, tagId));
				}
				return getRevob().getFilteredReviews(tags, CurrentUser.getModel(context));
			}
		};
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_action:
				onMenuAction();
				return true;
			case R.id.filter_action:
				showTagFilterDialog();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}

	private void onMenuAction()
	{
		// FragmentTransition.to(new CreateReviewFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private void showTagFilterDialog()
	{
		Intent intent = new Intent(getActivity(), TagFilterActivity.class);
		intent.putExtra(TagFilterActivity.CHECKED_ITEMS, tagFilter);
		getParentFragment().startActivityForResult(intent, REQUEST_TAG_FILTER);
	}
	
	private ReviewObject getRevob()
	{
		return ReviewObject.getByModelId(ReviewObject.class, getArguments().getString(RELATED_ID));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAG_FILTER)
		{
			tagFilter = data.getStringArrayListExtra(TagFilterActivity.CHECKED_ITEMS);
			reloadData();
		}
	}
}