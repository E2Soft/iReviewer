package com.example.ireviewr.fragments.reviewobjects;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.activities.TagFilterActivity;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewObjectAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewObjectsListFragment extends AbstractDetailListFragment<ReviewObject>
{
	public static int REQUEST_TAG_FILTER = 0;
	public static String TAG_FILTER = "TAG_FILTER";
	private ArrayList<String> tagFilter;
	
	public ReviewObjectsListFragment()
	{
		super(R.id.REVIEW_OBJECT_LOADER, R.menu.review_object_list_menu);
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
	protected ModelLoaderCallbacks<ReviewObject> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<ReviewObject>(getActivity(), ReviewObject.class, adapter)
		{
			protected List<ReviewObject> getData()
			{
				List<Tag> tags = new ArrayList<Tag>();
				for(String tagId : tagFilter)
				{
					tags.add(Tag.getByModelId(Tag.class, tagId));
				}
				return ReviewObject.getFilteredByTags(tags);
			}
		};
	}

	@Override
	protected AbstractArrayAdapter<ReviewObject> createAdapter()
	{
		return new ReviewObjectAdapter(getActivity());
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
		FragmentTransition.to(ReviewObjectFormFragment.newInstance(), getActivity());
	}
	
	private void showTagFilterDialog()
	{
		Intent intent = new Intent(getActivity(), TagFilterActivity.class);
		intent.putExtra(TagFilterActivity.CHECKED_ITEMS, tagFilter);
		startActivityForResult(intent, REQUEST_TAG_FILTER);
	}

	@Override
	protected void onItemClick(ReviewObject item)
	{
		FragmentTransition.to(ReviewObjectTabsFragment.newInstance(item.getModelId()), getActivity());
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.places);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAG_FILTER)
		{
			tagFilter = data.getStringArrayListExtra(TagFilterActivity.CHECKED_ITEMS);
			reloadData();
		}
	}
}