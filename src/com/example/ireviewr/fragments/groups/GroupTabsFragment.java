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
	
	public static final String ID = "ID";
	
	public GroupTabsFragment()
	{}
	
	public GroupTabsFragment(String id)
	{
		Bundle bundle = new Bundle();
		bundle.putString(ID, id);
		setArguments(bundle);
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
		getActivity().getActionBar().setTitle(R.string.group_details);
	}
}
