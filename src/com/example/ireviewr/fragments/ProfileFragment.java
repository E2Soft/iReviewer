package com.example.ireviewr.fragments;

import com.example.ireviewr.R;
import com.example.ireviewr.tools.CurrentUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.profile_layout, container, false);
		
		String usernameContent = CurrentUser.getName(getActivity());
		
		TextView userName = (TextView) view.findViewById(R.id.user_name_content);
		userName.setText(usernameContent);
		
		String emailContent = CurrentUser.getEmail(getActivity());
		
		TextView email = (TextView) view.findViewById(R.id.user_mail_content);
		email.setText(emailContent);
		
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getActivity().getActionBar().setTitle(R.string.profile);
		setHasOptionsMenu(true);
	}
	
}
