package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GaleryAdapter;
import com.example.ireviewr.fragments.GaleryGridFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.tools.ImageUtils;

public class ReviewGalleryFragment extends GaleryGridFragment{

	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewGalleryFragment()
	{}
	
	public ReviewGalleryFragment(String itemId)
	{
		super(R.id.REVIEW_IMAGE_LOADER);
		getArguments().putString(RELATED_ID, itemId);
	}
	
	@Override
	protected void addImage(Bitmap bitmap) 
	{
		Review review = getReview();
		String imagePath = ImageUtils.save(bitmap, review.getModelId()+"_"+review.getImages().size(), getActivity());
		new Image(imagePath, false, review).save();		
	}

	@Override
	protected ModelLoaderCallbacks<Image> getModelLoaderCallbacks(GaleryAdapter myAdapter) 
	{
		return new ModelLoaderCallbacks<Image>(getActivity(), Image.class, myAdapter)
		{
			@Override
			protected List<Image> getData()
			{
				return getReview().getImages();
			}
		};
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
	}

	@Override
	protected void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		FragmentTransition.to(new ReviewImagePagerFragment(getArguments().getString(RELATED_ID), position), getActivity());
	}

}
