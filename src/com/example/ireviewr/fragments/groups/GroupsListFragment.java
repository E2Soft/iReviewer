package com.example.ireviewr.fragments.groups;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;

public class GroupsListFragment extends ListFragment
{
	private GroupAdapter myAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
				new ModelLoaderCallbacks<Group>(getActivity(), 
				Group.class, 
				myAdapter));
		
		setListAdapter(myAdapter);
		
		return view;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				Toast.makeText(getActivity(), "Add Group item pressed", Toast.LENGTH_LONG).show();
				
				///////test////////
				String newid = ""+UUID.randomUUID().getLeastSignificantBits()%10000;
				User testUser = new Select().from(User.class).executeSingle();
				Group group = new Group("mygroup"+newid, testUser);
				group.save();
				///////////////
				
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
                //System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                //myAdapter.getFilter().filter(query.toString());
                //System.out.println("on query submit: "+query);
                return false;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		String modelId = myAdapter.getItem(position).getModelId();
		Fragment fragment = GroupTabsFragment.newInstance(modelId);
		
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment)
												.addToBackStack(null).commit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.groups);
		setHasOptionsMenu(true);
	}
}
