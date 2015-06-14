package com.example.ireviewr.fragments.reviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.ReviewsPagerAdapter;

public class ReviewTabFragment extends Fragment {
	
	private ReviewsPagerAdapter mReviewsPagerAdapter;
	private ViewPager mViewPager;
	
	private static String ID = "ID";
	
	public ReviewTabFragment()
	{}
	
	public ReviewTabFragment(String id)
	{
		Bundle bundle = new Bundle();
		bundle.putString(ID, id);
		setArguments(bundle);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.group_fragment_tabbed, container, false);
		mReviewsPagerAdapter = new ReviewsPagerAdapter(getArguments().getString(ID), getChildFragmentManager());
        
        mViewPager = (ViewPager) v.findViewById(R.id.group_pager);
        mViewPager.setAdapter(mReviewsPagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(mReviewsPagerAdapter.getCount()-1);
        
        return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
	}
}
