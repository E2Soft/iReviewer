package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ireviewr.R;
import com.example.ireviewr.tools.CurrentUser;

public class AboutFragment extends Fragment {
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_2, container, false);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.about);
		setHasOptionsMenu(true);
	}
	
}
