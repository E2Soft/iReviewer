package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.fragments.groups.GroupTabsFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.GroupToReview;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewGroupsListFragment extends AbstractDetailListFragment<Group>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewGroupsListFragment()
	{}
	
	public ReviewGroupsListFragment(String itemId)
	{
		super(R.id.GROUP_REVIEW_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected AbstractArrayAdapter<Group> createAdapter()
	{
		return new GroupAdapter(getActivity());
	}

	@Override
	protected void onItemClick(Group item)
	{
		FragmentTransition.to(GroupTabsFragment.newInstance(item.getModelId()), getActivity());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelLoaderCallbacks<Group> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Group>(getActivity(), Group.class, adapter, GroupToReview.class)
		{
			@Override
			protected List<Group> getData()
			{
				return getReview().getGroups();
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
		FragmentTransition.to(new ReviewGroupsCehckListFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
	}
}
