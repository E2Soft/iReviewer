package com.example.ireviewr.adapters.pagers;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ireviewr.fragments.reviews.CommentsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewDetailFragment;
import com.example.ireviewr.model.Comment;

public class ReviewsPagerAdapter extends FragmentPagerAdapter {

	private String[] names ={"Detail","Comments", "Galery"};
	private Context context;
	private Bundle bundle;
	
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

	private ArrayList<Comment> commentsList(){
		ArrayList<Comment> items = new ArrayList<Comment>();
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		
		return items;
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		if(position == 0){
			fragment = new ReviewDetailFragment();
		}else if(position == 1){
			fragment = new CommentsListFragment(commentsList());
		}else if(position == 2){
			fragment = new ReviewDetailFragment();
		}
		
		return fragment;
	}

}
