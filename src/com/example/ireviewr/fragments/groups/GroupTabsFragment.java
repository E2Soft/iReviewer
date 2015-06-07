package com.example.ireviewr.fragments.groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.GroupPagerAdapter;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.Mokap;

public class GroupTabsFragment extends Fragment {

	private GroupPagerAdapter mGroupPagerAdapter;
	private ViewPager mViewPager;
	private int id;//key of a chosen group
	
	public static String ID = "ID";
	public static String DATA = "DATA";
	
	public static GroupTabsFragment newInstance(int id) {
		GroupTabsFragment fragment = new GroupTabsFragment();
	    
		Bundle bundle = new Bundle();
		bundle.putInt(ID, id);
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
		id = getArguments().getInt(ID);
		
		setHasOptionsMenu(true);
	}
	
	/*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(GROUPS, users);
        outState.putParcelableArrayList(USERS, groups);
        super.onSaveInstanceState(outState);
    }*/
	
	private Bundle getDetailBundleByID(){
		Bundle bundle = new Bundle();
		//Group group = getGroupByID(id);
		//bundle.putParcelable(DATA, group);
		
		return bundle;
	}
	
	//TODO:Mokap ali kasnije bi trebali citati iz baze
	private Group getGroupByID(int id){
		return new Group("test group", new User("test user", "test@test.com"));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.group_fragment_tabbed, container, false);
		mGroupPagerAdapter = new GroupPagerAdapter(getDetailBundleByID(),getChildFragmentManager(),getActivity());
        
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
		getActivity().getActionBar().setTitle(R.string.detail);
		setHasOptionsMenu(true);
	}
	
}
