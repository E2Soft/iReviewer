package com.example.ireviewr.fragments.groups;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewsCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;

/**
 * Check lista reviewova koje je kreirao korisnik, cekirani se nalaze u grupi.
 */
public class GroupReviewsCheckListFragment extends AbstractCheckListFragment<Review>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public GroupReviewsCheckListFragment()
	{}
	
	public GroupReviewsCheckListFragment(String itemId)
	{
		super(R.id.GROUP_REVIEW_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter)
		{
			@Override
			protected List<Review> getData()
			{
				return CurrentUser.getModel(context).getReviews();
			}
		};
	}
	
	@Override
	protected void onItemCheck(Review item, boolean checked)
	{
		Group group = Group.getByModelId(Group.class, getArguments().getString(RELATED_ID));
		if(checked)
		{
			group.addReview(item);
		}
		else
		{
			group.removeReview(item);
		}
	}

	@Override
	protected AbstractArrayAdapter<Review> createAdapter()
	{
		final String groupId = getArguments().getString(RELATED_ID);
		return new ReviewsCheckAdapter(getActivity())
		{
			@Override
			protected boolean isChecked(Review item)
			{
				return item.isInGroup(groupId);
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
