package com.example.ireviewr.fragments.groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.GroupPagerAdapter;

public class GroupTabsFragment extends Fragment {

	private GroupPagerAdapter mGroupPagerAdapter;
	private ViewPager mViewPager;
	
	public static String ID = "ID";
	
	public static GroupTabsFragment newInstance(String id) {
		GroupTabsFragment fragment = new GroupTabsFragment();
	    
		Bundle bundle = new Bundle();
		bundle.putString(ID, id);
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
		
		/*groups = getArguments().getParcelableArrayList(GROUPS);
		users = getArguments().getParcelableArrayList(USERS);
		bundleArgs = getArguments().getBundle(ARGS);*/
		
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
		mGroupPagerAdapter = new GroupPagerAdapter(getArguments().getString(ID), getChildFragmentManager());
        
        mViewPager = (ViewPager) v.findViewById(R.id.group_pager);
        mViewPager.setAdapter(mGroupPagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(mGroupPagerAdapter.getCount()-1);
        
        return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.detail);
		setHasOptionsMenu(true);
	}
	
}
