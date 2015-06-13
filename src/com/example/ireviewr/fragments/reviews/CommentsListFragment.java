package com.example.ireviewr.fragments.reviews;

import java.util.List;

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
import android.widget.EditText;
import android.widget.SearchView;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.CommentsAdapter;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Comment;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.Mokap;

public class CommentsListFragment extends ListFragment
{
	private CommentsAdapter myAdapter;
	
	// TODO da prima id reviewa i dobavlja komentare za njega
	public static CommentsListFragment newInstance(String itemId)
	{
		CommentsListFragment fragment = new CommentsListFragment();
	    return fragment;
	}
	
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
		View view = inflater.inflate(R.layout.comments_list, container, false);
		
		myAdapter = new CommentsAdapter(getActivity());
		
		// TODO da prima id reviewa i dobavlja komentare za njega
		getActivity().getSupportLoaderManager().initLoader(R.id.COMMENT_LOADER, null, 
				new ModelLoaderCallbacks<Comment>(getActivity(), 
				Comment.class, 
				myAdapter)
				{
					@Override
					protected List<Comment> getData()
					{
						return Mokap.getCommentsList();
					}
				});
		
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
				//Toast.makeText(getActivity(), "Add Comment item pressed", Toast.LENGTH_LONG).show();
				commentDialog();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void commentDialog(){
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
					Comment newComment = new Comment(editText.getText().toString(), new User(), null);
					//add to list
					//items.add(newComment);
					// TODO save to database
					
					//update original list
					//((CommentsAdapter) myAdapter).getItemsOriginal().add(newComment);
					
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
		getActivity().getActionBar().setTitle(R.string.comments);
		setHasOptionsMenu(true);
	}
	
}
