package com.example.ireviewr.fragments.reviews;

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
import com.example.ireviewr.adapters.ReviewsAdapter;
import com.example.ireviewr.model.ReviewItem;

public class ReviewsGroupList extends ListFragment{
	
	private ArrayList<ReviewItem> items;
	private ArrayAdapter<ReviewItem> myAdapter;
	public static String DATA = "DATA";
	
	public static ReviewsGroupList newInstance(ArrayList<ReviewItem> items) {
		ReviewsGroupList fragment = new ReviewsGroupList();
	    
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(DATA, items);
		
		fragment.setArguments(bundle);
		
	    return fragment;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_view_fragment, container, false);
		
		myAdapter = new ReviewsAdapter(getActivity(), R.layout.review_item, items);
		//setListAdapter(new MyListAdapter(getActivity(), R.layout.drawer_list_item, items));
		setListAdapter(myAdapter);
		
		return view; 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.mainContent, new CreateReviewFragment())
				.addToBackStack(null)
				.commit();
				
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
		
		Fragment fragment = ReviewTabFragment.newInstance(position);
		
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment).
												addToBackStack(null).commit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.groups);
		setHasOptionsMenu(true);
	}
	
}
