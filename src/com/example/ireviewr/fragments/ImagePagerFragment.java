package com.example.ireviewr.fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.pagers.ImagePagerAdapter;
import com.example.ireviewr.tools.FragmentTransition;

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
		
		imagePagerAdapter.registerDataSetObserver(new DataSetObserver()
		{
			@Override
			public void onChanged()
			{
				if(imagePagerAdapter.getCount() == 0) // kad nema vise slika obrisi fragment
				{
					FragmentTransition.remove(ImagePagerFragment.this, getActivity());
				}
			}
		});
        
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(imagePagerAdapter);
        
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
