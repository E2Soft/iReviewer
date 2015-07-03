package com.example.ireviewr.fragments.reviewobjects;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.TagCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.TagToReviewObject;
import com.example.ireviewr.tools.FragmentTransition;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ReviewObjectTagsCheckListFragment extends AbstractCheckListFragment<Tag>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewObjectTagsCheckListFragment()
	{}
	
	public ReviewObjectTagsCheckListFragment(String itemId)
	{
		super(R.id.REVIEW_OBJECT_TAG_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<Tag> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Tag>(getActivity(), Tag.class, adapter, TagToReviewObject.class);
	}
	
	@Override
	protected void onItemCheck(Tag item, boolean checked)
	{
		ReviewObject reviewObj = ReviewObject.getByModelId(ReviewObject.class, getArguments().getString(RELATED_ID));
		if(checked)
		{
			item.addReviewObject(reviewObj);
		}
		else
		{
			item.removeReviewObject(reviewObj);
		}
	}

	@Override
	protected AbstractArrayAdapter<Tag> createAdapter()
	{
		final String reviewObjId = getArguments().getString(RELATED_ID);
		
		return new TagCheckAdapter(getActivity())
		{
			@Override
			protected boolean isChecked(Tag item)
			{
				return item.hasReviewObject(reviewObjId);
			}
		};
	}

	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		menu.findItem(R.id.menu_action)
		.setIcon(R.drawable.ic_action_accept)
		.setTitle(R.string.accept);
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
		FragmentTransition.remove(this, getActivity());
	}
}