package com.example.ireviewr.fragments.groups;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.dialogs.DefaultCancelListener;
import com.example.ireviewr.fragments.AbstractListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.CurrentUser;

public class GroupsListFragment extends AbstractListFragment<Group>
{
	@Override
	protected ModelLoaderCallbacks<Group> getModelLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Group>(getActivity(), Group.class, myAdapter);
	}

	@Override
	protected AbstractArrayAdapter<Group> getAdapter()
	{
		return new GroupAdapter(getActivity());
	}

	@Override
	protected int getLoaderId()
	{
		return MainActivity.LOADER_ID.GROUP;
	}

	@Override
	protected void addItem()
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
					try
					{
						new Group(text, testUser).saveOrThrow();
					}
					catch(SQLiteConstraintException ex)
					{
						Toast.makeText(getActivity(), "A group with name: "+text+" already exists.", Toast.LENGTH_LONG).show();
					}
				}
			})
			.setNegativeButton(R.string.cancel, new DefaultCancelListener())
			.show();
	}

	@Override
	protected void onItemClick(String modelId)
	{
		Fragment fragment = new GroupTabsFragment(modelId);
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment)
												.addToBackStack(null).commit();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.groups);
	}
}