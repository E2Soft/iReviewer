package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.ImagePagerAdapter;

public abstract class ImagePagerFragment extends Fragment
{
	private ViewPager mViewPager;
	private ImagePagerAdapter imagePagerAdapter;
	private int initialPosition = 0;
	
	public static final String RELATED_ID = "RELATED_ID";
	
	public ImagePagerFragment()
	{}
	
	public ImagePagerFragment(String id, int initialPosition)
	{
		Bundle bundle = new Bundle();
		bundle.putString(RELATED_ID, id);
		setArguments(bundle);
		this.initialPosition = initialPosition;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.image_detail_pager, container, false);
		imagePagerAdapter = getImagePagerAdapter();
        
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(imagePagerAdapter);
        
        //Laja buraz resio :D
        mViewPager.setOffscreenPageLimit(imagePagerAdapter.getCount()-1);
        
        mViewPager.setCurrentItem(initialPosition);
        
        return v;
	}
	
	protected abstract ImagePagerAdapter getImagePagerAdapter();
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.galery);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		imagePagerAdapter.unregisterObservers();
	}
}
