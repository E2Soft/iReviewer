package com.example.ireviewr.fragments.reviewobjects;

import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GaleryAdapter;
import com.example.ireviewr.fragments.GaleryGridFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.tools.ImageUtils;

public class ReviewObjectGalleryFragment extends GaleryGridFragment
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewObjectGalleryFragment()
	{}
	
	public ReviewObjectGalleryFragment(String itemId)
	{
		super(R.id.REVOB_IMAGE_LOADER);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected void addImage(Bitmap bitmap)
	{
		ReviewObject revob = getRevob();
		String imagePath = ImageUtils.save(bitmap, revob.getModelId()+"_"+revob.getImages().size(), getActivity());
		new Image(imagePath, false, revob).save();
	}

	@Override
	protected ModelLoaderCallbacks<Image> getModelLoaderCallbacks(GaleryAdapter myAdapter)
	{
		return new ModelLoaderCallbacks<Image>(getActivity(), Image.class, myAdapter)
		{
			@Override
			protected List<Image> getData()
			{
				return getRevob().getImages();
			}
		};
	}
	
	private ReviewObject getRevob()
	{
		return ReviewObject.getByModelId(ReviewObject.class, getArguments().getString(RELATED_ID));
	}

	@Override
	protected void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		FragmentTransition.to(new ReviewObjectImagePagerFragment(getArguments().getString(RELATED_ID), position), getActivity());
	}
}
