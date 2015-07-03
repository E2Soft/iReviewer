package com.example.ireviewr.fragments.reviews;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.CommentsAdapter;
import com.example.ireviewr.fragments.AbstractListFragment;
import com.example.ireviewr.model.Comment;

public abstract class AbstractCommentsListFragment extends AbstractListFragment<Comment>{

	public static final String RELATED_ID = "RELATED_ID";
	
	public AbstractCommentsListFragment()
	{}
	
	public AbstractCommentsListFragment(String itemId, int menuLayout)
	{
		super(R.id.COMMENT_LOADER, menuLayout);
		getArguments().putString(RELATED_ID, itemId);
	}
	
	@Override
	protected AbstractArrayAdapter<Comment> createAdapter()
	{
		return new CommentsAdapter(getActivity());
	}
	
}
