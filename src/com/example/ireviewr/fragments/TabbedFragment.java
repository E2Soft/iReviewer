package com.example.ireviewr.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.SectionsPagerAdapter;
import com.example.ireviewr.sta.SlidingTabLayout;

public class TabbedFragment extends Fragment{

	private  SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private Context context;
	
	
	public TabbedFragment(Context context) {
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),context);
        
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount()-1);
        
        // Initialize the Sliding Tab Layout
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.sliding_tabs);
 
        // Connect the viewPager with the sliding tab layout
        slidingTabLayout.setViewPager(mViewPager);
         
        return v;
	}
	
}
