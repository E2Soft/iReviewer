package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.TagsAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.TagToReview;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewTagsListFragment extends AbstractDetailListFragment<Tag>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewTagsListFragment()
	{}
	
	public ReviewTagsListFragment(String itemId)
	{
		super(R.id.TAG_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected AbstractArrayAdapter<Tag> createAdapter()
	{
		return new TagsAdapter(getActivity());
	}

	@Override
	protected void onItemClick(Tag item)
	{
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelLoaderCallbacks<Tag> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Tag>(getActivity(), Tag.class, adapter, TagToReview.class)
		{
			@Override
			protected List<Tag> getData()
			{
				return getReview().getTags();
			}
		};
	}
	
	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		menu.findItem(R.id.menu_action)
		.setIcon(R.drawable.ic_action_edit)
		.setTitle(R.string.edit_item);
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
		FragmentTransition.to(new ReviewTagsCheckListFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
	}
}
