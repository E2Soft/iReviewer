package com.example.ireviewr.fragments.reviewobjects;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewObjectAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewObjectsListFragment extends AbstractDetailListFragment<ReviewObject>
{
	public ReviewObjectsListFragment()
	{
		super(R.id.REVIEW_OBJECT_LOADER, R.menu.standard_list_menu);
	}
	
	@Override
	protected ModelLoaderCallbacks<ReviewObject> createLoaderCallbacks()
	{
		// svi review objekti
		return new ModelLoaderCallbacks<ReviewObject>(getActivity(), ReviewObject.class, adapter);
	}

	@Override
	protected AbstractArrayAdapter<ReviewObject> createAdapter()
	{
		return new ReviewObjectAdapter(getActivity());
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
		FragmentTransition.to(CreateReviewObjectFragment.newInstance(), getActivity());
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
}