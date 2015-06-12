package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.AbstractModel;

public abstract class AbstractListFragment<T extends AbstractModel> extends ListFragment
{
	protected AbstractArrayAdapter<T> myAdapter;
	
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
		View view = new ListView(getActivity());
		
		myAdapter = getAdapter();
		
		getActivity().getSupportLoaderManager().initLoader(getLoaderId(), null, getModelLoaderCallbacks());
		
		setListAdapter(myAdapter);
		
		return view;
	}
	
	public void reloadData()
	{
		getActivity().getSupportLoaderManager().restartLoader(getLoaderId(), null, getModelLoaderCallbacks());
	}
	
	protected abstract ModelLoaderCallbacks<T> getModelLoaderCallbacks();
	protected abstract AbstractArrayAdapter<T> getAdapter();
	protected abstract int getLoaderId();
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				addItem();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	protected abstract void addItem();

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
		onItemClick(modelId);
	}
	
	protected abstract void onItemClick(String modelId);
}
