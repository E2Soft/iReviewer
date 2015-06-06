package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ireviewr.R;
import com.example.ireviewr.fragments.reviewobjects.CreateReviewObjectFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends Fragment{
	
	private GoogleMap map;
	private SupportMapFragment mMapFragment;

	public static MyMapFragment newInstance() {
		MyMapFragment mpf = new MyMapFragment();
		
		return mpf;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getActivity().getActionBar().setTitle(R.string.home);
	    setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.home_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
			case R.id.home_add_item:
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.mainContent, new CreateReviewObjectFragment())
				.addToBackStack(null).commit();
				
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
    	
        View view = inflater.inflate(R.layout.map_layout, vg, false);
        
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mMapFragment).commit();
        
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
			
			@Override
			public void onMapReady(GoogleMap googleMap) {
				map = googleMap;
                initMap();
			}
		});
        
		return view;

    }
    
    private void initMap(){
    	double latitude = 17.385044;
	    double longitude = 78.486671;
	    
	    map.getUiSettings().setMyLocationButtonEnabled(true);
	    map.setMyLocationEnabled(true);
	    
	    // create marker
	    MarkerOptions marker = new MarkerOptions().position(
	            new LatLng(latitude, longitude)).title("Hello Maps").snippet("Snippet");

	    // Changing marker icon
	    marker.icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

	    // adding marker
	    map.addMarker(marker);
	    CameraPosition cameraPosition = new CameraPosition.Builder()
	            .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
	    map.animateCamera(CameraUpdateFactory
	            .newCameraPosition(cameraPosition));
	    //googleMap.setOnMarkerClickListener(new MapMarkerEvent());
    }
	
}
