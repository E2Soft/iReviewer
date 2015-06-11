package com.example.ireviewr.fragments.groups;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.dialogs.DefaultCancelListener;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.CurrentUser;

public class GroupsListFragment extends ListFragment
{
	private GroupAdapter myAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.groups_list, container, false);
		
		myAdapter = new GroupAdapter(getActivity());
		
		getActivity().getSupportLoaderManager().initLoader(MainActivity.LOADER_ID.GROUP, null, 
				new ModelLoaderCallbacks<Group>(getActivity(), Group.class, myAdapter));
		
		setListAdapter(myAdapter);
		
		return view;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId())
		{
			case R.id.add_item:
				createGroupDialog();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.activity_itemdetail, menu);
		SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
		
		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                myAdapter.getFilter().filter(newText.toString());
                
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		String modelId = myAdapter.getItem(position).getModelId();
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
	
	@SuppressLint("InflateParams")
	private void createGroupDialog()
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
					//EditText editText = (EditText) promptView.findViewById(R.id.edittext);
					String text = promptView.getText().toString();//editText.getText().toString();
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
}