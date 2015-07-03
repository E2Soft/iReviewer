package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.GroupsCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewGroupsCehckListFragment extends AbstractCheckListFragment<Group>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewGroupsCehckListFragment()
	{}
	
	public ReviewGroupsCehckListFragment(String itemId)
	{
		super(R.id.REVIEW_GROUP_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<Group> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Group>(getActivity(), Group.class, adapter)
		{
			protected List<Group> getData()
			{
				return CurrentUser.getModel(getActivity()).getGroupsCreated();
			}
		};
	}
	
	@Override
	protected void onItemCheck(Group item, boolean checked)
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
	protected AbstractArrayAdapter<Group> createAdapter()
	{
		final String reviewId = getArguments().getString(RELATED_ID);
		return new GroupsCheckAdapter(getActivity())
		{
			@Override
			protected boolean isChecked(Group item)
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
