package com.example.ireviewr.fragments.users;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.GroupUsersCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;

public class GroupUsersCheckListFragment extends AbstractCheckListFragment<User>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public GroupUsersCheckListFragment()
	{}
	
	public GroupUsersCheckListFragment(String itemId)
	{
		super(R.id.GROUP_REVIEW_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<User> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<User>(getActivity(), User.class, adapter);
	}
	
	@Override
	protected void onItemCheck(User item, boolean checked)
	{
		Group group = Group.getByModelId(Group.class, getArguments().getString(RELATED_ID));
		if(checked)
		{
			group.addUser(item);
		}
		else
		{
			group.removeUser(item);
		}
	}

	@Override
	protected AbstractArrayAdapter<User> createAdapter()
	{
		final String groupId = getArguments().getString(RELATED_ID);
		return new GroupUsersCheckAdapter(getActivity(), groupId);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		reloadData();
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
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(this)
		.commit();
		getActivity().getSupportFragmentManager().popBackStack();
	}
}
