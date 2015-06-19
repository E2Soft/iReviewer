package com.example.ireviewr.fragments.reviewobjects;

import com.example.ireviewr.adapters.pagers.ImagePagerAdapter;
import com.example.ireviewr.adapters.pagers.ReviewObjectImagePagerAdapter;
import com.example.ireviewr.fragments.ImagePagerFragment;

public class ReviewObjectImagePagerFragment extends ImagePagerFragment
{
	public ReviewObjectImagePagerFragment()
	{}
	
	public ReviewObjectImagePagerFragment(String id, int initialPosition)
	{
		super(id, initialPosition);
	}

	@Override
	protected ImagePagerAdapter getImagePagerAdapter()
	{
		return new ReviewObjectImagePagerAdapter(getArguments().getString(RELATED_ID), getChildFragmentManager(), getActivity());
	}
}
