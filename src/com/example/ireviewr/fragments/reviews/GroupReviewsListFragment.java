package com.example.ireviewr.fragments.reviews;

import java.util.List;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;


public class GroupReviewsListFragment extends AbstractReviewsListFragment
{
	public GroupReviewsListFragment()
	{}
	
	public GroupReviewsListFragment(String itemId)
	{
		super(itemId);
	}
	
	@Override
	protected ModelLoaderCallbacks<Review> getModelLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, myAdapter)
		{
			@Override
			protected List<Review> getData()
			{
				return Group.getByModelId(Group.class, getArguments().getString(RELATED_ID)).getReviews();
			}
		};
	}

	@Override
	protected int getLoaderId()
	{
		return MainActivity.LOADER_ID.REVIEW;
	}

	@Override
	protected void addItem()
	{
		// TODO choose review from list
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.mainContent, new CreateReviewFragment())
		.addToBackStack(null)
		.commit();
	}
}
