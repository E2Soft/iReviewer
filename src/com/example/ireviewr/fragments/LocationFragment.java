package com.example.ireviewr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
	
	//mapview
	private MapView mMapView;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
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
				.replace(R.id.mainContent, new CreateReviewObjectFragment()).
					addToBackStack(null).commit();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void setUpMapIfNeeded(View inflatedView) {
        if (mMapView == null) {
        	mMapView = ((MapView) inflatedView.findViewById(R.id.mapview));
        }
    }

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		
	    // inflat and return the layout
	    View v = inflater.inflate(R.layout.location_fragment, container, false);
	    
	    setUpMapIfNeeded(v);
	    mMapView.onCreate(savedInstanceState);
	    
	    //init map
	    try {
	        MapsInitializer.initialize(getActivity().getApplicationContext());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    mMapView.getMapAsync(this);
	    
	    return v;
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    mMapView.onResume();
	    getActivity().getActionBar().setTitle(R.string.home);
	    setHasOptionsMenu(true);
	}
	

	@Override
	public void onPause() {
	    super.onPause();
	    mMapView.onPause();
	}
	

	@Override
	public void onDestroy() {
		mMapView.onDestroy();
	    super.onDestroy();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		// latitude and longitude
	    double latitude = 17.385044;
	    double longitude = 78.486671;
	    
	    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
	    googleMap.setMyLocationEnabled(true);
	    
	    // create marker
	    MarkerOptions marker = new MarkerOptions().position(
	            new LatLng(latitude, longitude)).title("Hello Maps").snippet("Snippet");

	    // Changing marker icon
	    marker.icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

	    // adding marker
	    googleMap.addMarker(marker);
	    CameraPosition cameraPosition = new CameraPosition.Builder()
	            .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
	    googleMap.animateCamera(CameraUpdateFactory
	            .newCameraPosition(cameraPosition));
	    //googleMap.setOnMarkerClickListener(new MapMarkerEvent());
	}
	
}
