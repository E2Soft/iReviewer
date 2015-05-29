package com.example.ireviewr.fragments.groups;

import com.example.ireviewr.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.edit_item:
				Toast.makeText(getActivity(), "Edit User item pressed", Toast.LENGTH_LONG).show();
				return true;
			case R.id.delete_item:
				Toast.makeText(getActivity(), "Delete User item pressed", Toast.LENGTH_LONG).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.fragment_detail_menu, menu);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.user_detail_fragment, container, false);
		Bundle bundle = getArguments();
		
		TextView gTitle = (TextView)view.findViewById(R.id.user_title);
		gTitle.setText(bundle.getString("TITLE"));
		
		TextView gText = (TextView)view.findViewById(R.id.user_subTitle);
		gText.setText(bundle.getString("TEXT"));
		
		ImageView gImage = (ImageView)view.findViewById(R.id.user_icon);
		gImage.setImageResource(bundle.getInt("ICON"));
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
	}
	
}
