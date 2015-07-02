package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Comment;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.CurrentUser;

public class CommentsListFragment extends AbstractCommentsListFragment
{	
	
	public CommentsListFragment()
	{}
	
	// prima id reviewa i dobavlja komentare za njega
	public CommentsListFragment(String itemId)
	{
		super(itemId, R.menu.activity_itemdetail);
	}
	
	@Override
	protected ModelLoaderCallbacks<Comment> createLoaderCallbacks() 
	{
		return new ModelLoaderCallbacks<Comment>(getActivity(), Comment.class, adapter)
		{
			@Override
			protected List<Comment> getData()
			{	
				Log.d("KOMENTARI REVIEWA", "review_id="+getReview().getModelId()+"komentari="+getReview().getComments());
				return getReview().getComments();
			}
		};
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
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	private void commentDialog(){
		
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		final View promptView = layoutInflater.inflate(R.layout.comment_dialog, null);
		new AlertDialog.Builder(getActivity())
			.setView(promptView)		
			.setCancelable(false)
			.setPositiveButton(R.string.comment, new OnClickListener() 
			{	
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					//get data
					EditText editText = (EditText) promptView.findViewById(R.id.edittext);
					
					Review review = getReview();
					
					//create object
					Comment newComment = new Comment(editText.getText().toString(), CurrentUser.getModel(getActivity()), review);
					
					// save to database
					newComment.saveOrThrow();
					
					//Log.d("COMMENT","commnet  "+newComment.getReview().getName());
					Toast.makeText(getActivity(), R.string.created, Toast.LENGTH_SHORT).show();
					
					//update original list
					//((CommentsAdapter) myAdapter).getItemsOriginal().add(newComment);
					
					//notify adapter
					//myAdapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int id) 
				{
					dialog.cancel();
				}
			})
			.show();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.comments);
		setHasOptionsMenu(true);
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
	}
	
}
