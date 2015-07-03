package com.example.ireviewr.fragments.reviews;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.TagCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.TagToReview;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewTagsCheckListFragment extends AbstractCheckListFragment<Tag>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewTagsCheckListFragment()
	{}
	
	public ReviewTagsCheckListFragment(String itemId)
	{
		super(R.id.REVIEW_TAG_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<Tag> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Tag>(getActivity(), Tag.class, adapter, TagToReview.class);
	}
	
	@Override
	protected void onItemCheck(Tag item, boolean checked)
	{
		Review review = Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
		if(checked)
		{
			item.addReview(review);
		}
		else
		{
			item.removeReview(review);
		}
	}

	@Override
	protected AbstractArrayAdapter<Tag> createAdapter()
	{
		final String reviewId = getArguments().getString(RELATED_ID);
		
		return new TagCheckAdapter(getActivity())
		{
			@Override
			protected boolean isChecked(Tag item)
			{
				return item.hasReview(reviewId);
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