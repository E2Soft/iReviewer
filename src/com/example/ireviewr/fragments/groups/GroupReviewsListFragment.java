package com.example.ireviewr.fragments.groups;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviews.AbstractReviewsListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.GroupToReview;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;

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
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter, GroupToReview.class)
		{
			@Override
			protected List<Review> getData()
			{
				return getGroup().getReviews();
			}
		};
	}
	
	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		String currentUserId = CurrentUser.getId(getActivity());
		if(getGroup().isCreatedBy(currentUserId))
		{
			menu.findItem(R.id.menu_action)
			.setIcon(R.drawable.ic_action_edit)
			.setTitle(R.string.edit_item);
		}
		else
		{
			menu.removeItem(R.id.menu_action);
		}
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
		FragmentTransition.to(new GroupReviewsCheckListFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private Group getGroup()
	{
		return Group.getByModelId(Group.class, getArguments().getString(RELATED_ID));
	}
}
