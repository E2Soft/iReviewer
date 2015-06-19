package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.example.ireviewr.R;
import com.example.ireviewr.loaders.BitmapWorkerTask;
import com.example.ireviewr.model.Image;

public abstract class ImageDetailFragment extends Fragment
{
	public static final String ID = "ID";
	public static final String PATH = "PATH";
	private ImageView mImageView;
	
	public ImageDetailFragment()
	{}
	
	public ImageDetailFragment(Image image)
	{
		final Bundle args = new Bundle();
		args.putString(ID, image.getModelId());
		args.putString(PATH, image.getPath());
		setArguments(args);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.image, menu);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// image_detail_fragment.xml contains just an ImageView
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.imageView);
		
		// Load image into ImageView
		ViewTreeObserver viewTreeObserver = mImageView.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		    public boolean onPreDraw() {
		        // Remove after the first run so it doesn't fire forever
		    	mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
		        int height = mImageView.getMeasuredHeight();
		        int width = mImageView.getMeasuredWidth();
		        // load in background
		        BitmapWorkerTask.loadBitmap(getArguments().getString(PATH), mImageView, width, height, 
		        		R.drawable.ic_action_picture, ImageDetailFragment.this.getActivity());
		        return true;
		    }
		});
		
		return v;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.delete_item:
				delete();
				return true;
			case R.id.set_as_main:
				setAsMain();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected abstract void setAsMain();
	protected abstract void delete();
}
