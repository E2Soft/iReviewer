package com.example.ireviewr.fragments.groups;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.dialogs.DefaultCancelListener;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;

public class GroupsListFragment extends AbstractDetailListFragment<Group>
{
	public GroupsListFragment()
	{
		super(R.id.GROUP_LOADER, R.menu.standard_list_menu);
	}
	
	@Override
	protected ModelLoaderCallbacks<Group> createLoaderCallbacks()
	{
		// sve grupe
		return new ModelLoaderCallbacks<Group>(getActivity(), Group.class, adapter);
	}

	@Override
	protected AbstractArrayAdapter<Group> createAdapter()
	{
		return new GroupAdapter(getActivity());
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
	
	// pitaj korisnika kako da se zove nova grupa i kreiraj novu grupu
	private void onMenuAction()
	{
		final EditText promptView = new EditText(getActivity());
		promptView.setHint(R.string.new_group_hint);
		new AlertDialog.Builder(getActivity())
			.setView(promptView)
			.setTitle(R.string.new_group)
			.setPositiveButton(R.string.create, new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//get data
					String text = promptView.getText().toString();
					//create object
					User testUser = CurrentUser.getModel(GroupsListFragment.this.getActivity());

					new Group(text, testUser).save();
					Toast.makeText(getActivity(), R.string.created, Toast.LENGTH_SHORT).show();
				}
			})
			.setNegativeButton(R.string.cancel, new DefaultCancelListener())
			.show();
	}

	@Override
	protected void onItemClick(Group item)
	{
		FragmentTransition.to(GroupTabsFragment.newInstance(item.getModelId()), getActivity());
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.groups);
	}
}