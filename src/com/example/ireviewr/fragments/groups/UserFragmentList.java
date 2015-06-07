package com.example.ireviewr.fragments.groups;

import java.util.List;

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

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.UserAdapter;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.Mokap;
import com.example.ireviewr.tools.ReviewerTools;

public class UserFragmentList extends ListFragment
{
	private UserAdapter myAdapter;
	
	public static String DATA = "DATA";
	public static String NAME = "NAME";
	public static String LAST_MODIFIED = "LAST MODIFIED";
	
	public static UserFragmentList newInstance(String itemId)
	{
		UserFragmentList fragment = new UserFragmentList();
	    return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.users_fragment, container, false);
		
		myAdapter = new UserAdapter(getActivity());
		
		getActivity().getSupportLoaderManager().initLoader(MainActivity.LOADER_ID.USER, null, 
				new ModelLoaderCallbacks<User>(getActivity(), 
				User.class, 
				myAdapter)
				{
					@Override
					protected List<User> getData()
					{
						return Mokap.getUserModelList();
					}
				});
		
		setListAdapter(myAdapter);
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				Toast.makeText(getActivity(), "Add User item pressed", Toast.LENGTH_LONG).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		User item = myAdapter.getItem(position);
		
		Bundle bundle = new Bundle();
		bundle.putString(NAME, item.getName());
		bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(item.getDateModified()));
		
		Fragment fragment = new UserDetailFragment();
		fragment.setArguments(bundle);
		
		getActivity().getSupportFragmentManager().beginTransaction().
					replace(R.id.mainContent, fragment).addToBackStack(null).commit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.users);
		setHasOptionsMenu(true);
	}
	
}
