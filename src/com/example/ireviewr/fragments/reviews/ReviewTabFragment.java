package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.content.Intent;
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
		
		View v = inflater.inflate(R.layout.reviews_tabbed, container, false);
		mReviewsPagerAdapter = new ReviewsPagerAdapter(getArguments().getString(ID), getChildFragmentManager(), getActivity());
        
        mViewPager = (ViewPager) v.findViewById(R.id.review_pager);
        mViewPager.setAdapter(mReviewsPagerAdapter);
        
        mViewPager.setOffscreenPageLimit(mReviewsPagerAdapter.getCount()-1);
        
        return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments)
            {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
