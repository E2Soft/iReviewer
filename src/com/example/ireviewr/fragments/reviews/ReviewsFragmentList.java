package com.example.ireviewr.fragments.reviews;

import java.util.ArrayList;

import android.os.Bundle;
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

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.MyListAdapter;
import com.example.ireviewr.model.NavItem;
import com.example.ireviewr.tools.FragmentTransition;

public class ReviewsFragmentList extends ListFragment {
	
	private ArrayList<NavItem> items;
	private ArrayAdapter<NavItem> myAdapter;
	public static String DATA = "DATA";
	
	public static ReviewsFragmentList newInstance(ArrayList<NavItem> items) {
		ReviewsFragmentList fragment = new ReviewsFragmentList();
	    
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(DATA, items);
		
		fragment.setArguments(bundle);
		
	    return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.reviews_fragment, container, false);
		
		myAdapter = new MyListAdapter(getActivity(), R.layout.drawer_list_item, items);
		//setListAdapter(new MyListAdapter(getActivity(), R.layout.drawer_list_item, items));
		setListAdapter(myAdapter);
		
		return view; 
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		items = getArguments().getParcelableArrayList(DATA);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				FragmentTransition.to(new CreateReviewFragment(), getActivity());
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
		
		NavItem item = items.get(position);
		
		FragmentTransition.to(new ReviewTabFragment("test_id"), getActivity());
	}
}
