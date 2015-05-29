package com.example.ireviewr.fragments.groups;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.GroupPagerAdapter;
import com.example.ireviewr.model.NavItem;

public class GroupTabsFragment extends Fragment {

	private GroupPagerAdapter mGroupPagerAdapter;
	private ViewPager mViewPager;
	private Context context;
	private ArrayList<NavItem> groups;
	private ArrayList<NavItem> users;
	
	public GroupTabsFragment(Context context,
			ArrayList<NavItem> groups, ArrayList<NavItem> users) {
		this.context = context;
		this.groups = groups;
		this.users = users;
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
		mGroupPagerAdapter = new GroupPagerAdapter(getArguments(),getChildFragmentManager(),context,groups,users);
        
        mViewPager = (ViewPager) v.findViewById(R.id.group_pager);
        mViewPager.setAdapter(mGroupPagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(mGroupPagerAdapter.getCount()-1);
        
        // Initialize the Sliding Tab Layout
        /*SlidingTabLayout slidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.group_sliding_tabs);
 
        // Connect the viewPager with the sliding tab layout
        slidingTabLayout.setViewPager(mViewPager);*/
        
        return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
	}
	
}
