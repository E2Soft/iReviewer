package com.example.ireviewr.fragments.users;

import java.util.List;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.UsersAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.GroupToUser;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.CurrentUser;

public class GroupUsersListFragment extends AbstractDetailListFragment<User>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public GroupUsersListFragment()
	{}
	
	public GroupUsersListFragment(String itemId)
	{
		super(R.id.GROUP_USER_LOADER, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected AbstractArrayAdapter<User> createAdapter()
	{
		return new UsersAdapter(getActivity());
	}

	@Override
	protected void onItemClick(User item)
	{
		Fragment fragment = new UserDetailFragment(item);
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment).
												addToBackStack(null).commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelLoaderCallbacks<User> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<User>(getActivity(), User.class, adapter, GroupToUser.class)
		{
			@Override
			protected List<User> getData()
			{
				return getGroup().getUsers();
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
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.mainContent, new GroupUsersCheckListFragment(getArguments().getString(RELATED_ID)))
		.addToBackStack(null)
		.commit();
	}
	
	private Group getGroup()
	{
		return Group.getByModelId(Group.class, getArguments().getString(RELATED_ID));
	}
}
