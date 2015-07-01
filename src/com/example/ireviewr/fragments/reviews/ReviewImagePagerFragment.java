package com.example.ireviewr.fragments.reviews;

import com.example.ireviewr.adapters.pagers.ImagePagerAdapter;
import com.example.ireviewr.adapters.pagers.ReviewImagePagerAdapter;
import com.example.ireviewr.fragments.ImagePagerFragment;

public class ReviewImagePagerFragment extends ImagePagerFragment {

	public ReviewImagePagerFragment()
	{}
	
	public ReviewImagePagerFragment(String id, int initialPosition)
	{
		super(id, initialPosition);
	}

	@Override
	protected ImagePagerAdapter getImagePagerAdapter()
	{
		return new ReviewImagePagerAdapter(getArguments().getString(RELATED_ID), getChildFragmentManager(), getActivity());
	}
}
