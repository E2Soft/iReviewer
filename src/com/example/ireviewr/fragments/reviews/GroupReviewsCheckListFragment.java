package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.ReviewsCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;

/**
 * Check lista reviewova koje je kreirao korisnik, cekirani se nalaze u grupi.
 */
public class GroupReviewsCheckListFragment extends AbstractCheckListFragment<Review>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public GroupReviewsCheckListFragment()
	{}
	
	public GroupReviewsCheckListFragment(String itemId)
	{
		super(MainActivity.LOADER_ID.REVIEW, R.menu.standard_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected ModelLoaderCallbacks<Review> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Review>(getActivity(), Review.class, adapter)
		{
			@Override
			protected List<Review> getData()
			{
				// TODO da vraca samo one od trenutnog usera i cekira one koji su u grupi
				return Group.getByModelId(Group.class, getArguments().getString(RELATED_ID)).getReviews();
			}
		};
	}
	
	@Override
	protected void onItemCheck(Review item, boolean checked)
	{
		Log.d("MULTISELECT", "selected "+item.getName()+" "+checked);
		// TODO da na cekiranje doda, na odcekiranje brise vezu
	}

	@Override
	protected AbstractArrayAdapter<Review> createAdapter()
	{
		return new ReviewsCheckAdapter(getActivity());
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		reloadData();
	}
	
	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		menu.findItem(R.id.menu_action)
		.setIcon(R.drawable.ic_action_accept)
		.setTitle(R.string.accept);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.menu_action:
				onMenuAction();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void onMenuAction()
	{
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(this)
		.commit();
		getActivity().getSupportFragmentManager().popBackStack();
	}
}
