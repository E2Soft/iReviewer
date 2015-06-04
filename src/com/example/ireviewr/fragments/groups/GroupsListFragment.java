package com.example.ireviewr.fragments.groups;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GroupAdapter;
import com.example.ireviewr.model.Group;

public class GroupsListFragment extends ListFragment {
	private ArrayList<Group> items;
	private ArrayAdapter<Group> myAdapter;
	public static String DATA = "DATA";
	
	public static GroupsListFragment newInstance(ArrayList<Group> items) {
		GroupsListFragment fragment = new GroupsListFragment();
	    
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(DATA, items);
		
		fragment.setArguments(bundle);
		
	    return fragment;
	  }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		/*if(savedInstanceState == null || !savedInstanceState.containsKey(DATA)) {
			items = getArguments().getParcelableArrayList(DATA);
		}else{
			items = savedInstanceState.getParcelableArrayList(DATA);
		}*/
		
		items = getArguments().getParcelableArrayList(DATA);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	/*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(DATA, items);
        super.onSaveInstanceState(outState);
    }*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.groups_list, container, false);
		
		myAdapter = new GroupAdapter(getActivity(), R.layout.group_item, items);
		//setListAdapter(new MyListAdapter(getActivity(), R.layout.drawer_list_item, items));
		setListAdapter(myAdapter);
		
		return view; 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				Toast.makeText(getActivity(), "Add Group item pressed", Toast.LENGTH_LONG).show();
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
		
		Fragment fragment = GroupTabsFragment.newInstance(position);
		
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
