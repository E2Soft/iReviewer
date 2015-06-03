package com.example.ireviewr.fragments.groups;

import java.util.ArrayList;

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
	private ArrayList<NavItem> groups;
	private ArrayList<NavItem> users;
	private Bundle bundleArgs;
	
	public static String GROUPS = "GROUPS";
	public static String USERS = "USERS";
	public static String ARGS = "ARGS";
	
	public static GroupTabsFragment newInstance(Bundle args, ArrayList<NavItem> groups, ArrayList<NavItem> users) {
		GroupTabsFragment fragment = new GroupTabsFragment();
	    
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(GROUPS, groups);
		bundle.putParcelableArrayList(USERS, users);
		bundle.putBundle(ARGS, args);
		
		fragment.setArguments(bundle);
		
	    return fragment;
	  }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*if(savedInstanceState == null || !savedInstanceState.containsKey(GROUPS)
				|| !savedInstanceState.containsKey(USERS)) {
			groups = getArguments().getParcelableArrayList(GROUPS);
			users = getArguments().getParcelableArrayList(USERS);
		}else{
			groups = savedInstanceState.getParcelableArrayList(GROUPS);
			users = savedInstanceState.getParcelableArrayList(USERS);
		}*/
		
		groups = getArguments().getParcelableArrayList(GROUPS);
		users = getArguments().getParcelableArrayList(USERS);
		bundleArgs = getArguments().getBundle(ARGS);
		
		setHasOptionsMenu(true);
	}
	
	/*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(GROUPS, users);
        outState.putParcelableArrayList(USERS, groups);
        super.onSaveInstanceState(outState);
    }*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.group_fragment_tabbed, container, false);
		mGroupPagerAdapter = new GroupPagerAdapter(bundleArgs,
													getChildFragmentManager(),
													getActivity(),groups,users);
        
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
