package com.example.ireviewr.fragments.reviewobjects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.ReviewObjectPagerAdapter;

public class ReviewObjectTabsFragment extends Fragment
{
	private ReviewObjectPagerAdapter mReviewObjectPagerAdapter;
	private ViewPager mViewPager;
	
	public static final String ID = "ID";
	
	public static ReviewObjectTabsFragment newInstance(String id)
	{
		ReviewObjectTabsFragment newFrag = new ReviewObjectTabsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ID, id);
		newFrag.setArguments(bundle);
		return newFrag;
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
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.revob_fragment_tabbed, container, false);
		mReviewObjectPagerAdapter = new ReviewObjectPagerAdapter(getArguments().getString(ID), getChildFragmentManager());
        
        mViewPager = (ViewPager) v.findViewById(R.id.revob_pager);
        mViewPager.setAdapter(mReviewObjectPagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(mReviewObjectPagerAdapter.getCount()-1);
        
        return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.review_object_details);
	}
}