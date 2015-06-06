package com.example.ireviewr.adapters.pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ireviewr.fragments.GaleryGridFragment;
import com.example.ireviewr.fragments.TagsFragmentList;
import com.example.ireviewr.fragments.groups.GroupTabsFragment;
import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;
import com.example.ireviewr.model.ReviewItem;
import com.example.ireviewr.tools.ReviewerTools;

public class ReviewsPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail", "Comments", "Galery", "Tags"};
	private Context context;
	private Bundle bundle;
	
	public static String NAME ="NAME";
	public static String DESCRIPTION ="DESCRIPTION";
	public static String CREATED = "CREATED";
	public static String LAST_MODIFIED = "LAST MODIFIED";
	public static String IMAGE = "IMAGE";
	public static String RATING = "RATING";
	
	public ReviewsPagerAdapter(Bundle bundle, FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		this.bundle = bundle;
	}
	
	@Override
	public int getCount() {
		return names.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return names[position];
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		ReviewItem review = bundle.getParcelable(GroupTabsFragment.DATA);
		
		if(position == 0){
			Bundle bundle = new Bundle();
			bundle.putString(NAME, review.getName());
			bundle.putString(DESCRIPTION, review.getDescription());
			bundle.putString(CREATED,ReviewerTools.preapreDate(review.getCreated()));
			bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(review.getLast_modified()));
			bundle.putString(IMAGE, review.getPicture().getPath());
			bundle.putDouble(RATING, review.getRating());
			
			fragment = new ReviewDetailFragment();
			fragment.setArguments(bundle);
		}else if(position == 1){
			fragment = CommentsListFragment.newInstance(review.getComments());
		}else if(position == 2){
			fragment = GaleryGridFragment.newInstance(review.getImages());
		}else if(position == 3){
			fragment = TagsFragmentList.newInstance(review.getTags());
		}
		
		return fragment;
	}

}
