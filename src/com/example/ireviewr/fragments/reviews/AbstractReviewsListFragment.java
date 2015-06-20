package com.example.ireviewr.fragments.reviews;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewsAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.FragmentTransition;

/**
 * Lista reviewova sa detaljima. Na klik otvara detalje za review.
 */
public abstract class AbstractReviewsListFragment extends AbstractDetailListFragment<Review>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public AbstractReviewsListFragment()
	{}
	
	public AbstractReviewsListFragment(String itemId, int menuLayout)
	{
		super(R.id.GROUP_REVIEW_LOADER, menuLayout);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected AbstractArrayAdapter<Review> createAdapter()
	{
		return new ReviewsAdapter(getActivity());
	}

	@Override
	protected void onItemClick(Review item)
	{
		FragmentTransition.to(new ReviewTabFragment(item.getModelId()), getActivity());
	}
}
