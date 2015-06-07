package com.example.ireviewr.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.TagsAdapter;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.Mokap;

public class TagsFragmentList extends ListFragment {
	private ArrayAdapter<Tag> myAdapter;
	
	// TODO da prima id rev objekta ili reviewa za koji trazi sve tagove, ili da trazi sve tagove
	public static TagsFragmentList newInstance(ArrayList<Tag> items) {
		TagsFragmentList fragment = new TagsFragmentList();
	    return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tags_list, container, false);
		
		myAdapter = new TagsAdapter(getActivity(), R.layout.tags_item, Mokap.getTags());
		//setListAdapter(new MyListAdapter(getActivity(), R.layout.drawer_list_item, items));
		setListAdapter(myAdapter);
		
		return view; 
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
				tagsDialog();
				
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void tagsDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		final View promptView = layoutInflater.inflate(R.layout.comment_dialog, null);
		alertDialogBuilder.setView(promptView);
		
		alertDialogBuilder.setCancelable(false)
			.setPositiveButton(R.string.comment, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//get data
					EditText editText = (EditText) promptView.findViewById(R.id.edittext);
					//create object
					//Tag tag = new Tag(editText.getText().toString(), new Date());
					//add to list
					//items.add(tag);
					// TODO add to database
					
					//update original list
					//((TagsAdapter) myAdapter).getItemsOriginal().add(tag);
					
					//notify adapter
					myAdapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
			});
		
		
		// create an alert dialog
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.review_tags);
		setHasOptionsMenu(true);
	}
	
}
