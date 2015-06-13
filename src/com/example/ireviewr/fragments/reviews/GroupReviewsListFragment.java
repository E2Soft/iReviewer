package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;

/**
 * Svi reviewovi u datoj grupi.
 */
public class GroupReviewsListFragment extends AbstractReviewsListFragment
{
	public GroupReviewsListFragment()
	{}
	
	public GroupReviewsListFragment(String itemId)
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
				return Group.getByModelId(Group.class, getArguments().getString(RELATED_ID)).getReviews();
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
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.mainContent, new GroupReviewsCheckListFragment(getArguments().getString(RELATED_ID)))
		.addToBackStack(null)
		.commit();
	}
}
