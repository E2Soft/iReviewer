package com.example.ireviewr.fragments.reviews;

import android.support.v4.app.Fragment;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewsAdapter;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.model.Review;

/**
 * Lista reviewova sa detaljima. Na klik otvara detalje za review.
 */
public abstract class AbstractReviewsListFragment extends AbstractDetailListFragment<Review>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public AbstractReviewsListFragment()
	{}
	
	public AbstractReviewsListFragment(String itemId, int menuIcon)
	{
		super(MainActivity.LOADER_ID.REVIEW, menuIcon);
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
		Fragment fragment = ReviewTabFragment.newInstance(item.getModelId());
		getActivity().getSupportFragmentManager()
												.beginTransaction()
												.replace(R.id.mainContent, fragment).
												addToBackStack(null).commit();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		// kad se pokrene sa novim povezanim id da se reloaduje
		reloadData();
	}
}
