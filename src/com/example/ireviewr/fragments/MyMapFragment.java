package com.example.ireviewr.fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.dialogs.LocationDialog;
import com.example.ireviewr.fragments.reviewobjects.CreateReviewObjectFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends Fragment implements LocationListener, OnMapReadyCallback{
	
	private GoogleMap map;
	private SupportMapFragment mMapFragment;
	private LocationManager locationManager;
	String provider;
	
	private Marker home;
	
	public static MyMapFragment newInstance() {
		MyMapFragment mpf = new MyMapFragment();
		
		return mpf;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// Get LocationManager object from System Service LOCATION_SERVICE
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, true);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();
		
		getActivity().getActionBar().setTitle(R.string.home);
	    setHasOptionsMenu(true);
	    
	    if ( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			
	    	Toast.makeText(getActivity(), "hasService", Toast.LENGTH_SHORT).show();
	    }else{
	    	Toast.makeText(getActivity(), "noService", Toast.LENGTH_SHORT).show();
	    	
	    	new LocationDialog(getActivity()).prepareDialog().show();
	    }
	    
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
        
        mMapFragment.getMapAsync(this);
        
		return view;

    }
    
    
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    	locationManager.removeUpdates(this);
    }
    
    @Override
    public void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
    	locationManager.removeUpdates(this);
    }

    private void addMarker(Location location){
    	Toast.makeText(getActivity(), "addMarker", Toast.LENGTH_SHORT).show();
    	LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
    	
    	if(home != null){
			home.remove();
		}
		
		home = map.addMarker(new MarkerOptions()
			.title("Title")
			.snippet("Contnet")
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
			.position(loc));
			
			
			CameraPosition cameraPosition = new CameraPosition
					.Builder().target(loc)
					.zoom(12).build();
			
			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    
	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(getActivity(), "onLocationChanged()"+location.toString(), Toast.LENGTH_SHORT).show();
		
		addMarker(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		Toast.makeText(getActivity(), "onMapReady()", Toast.LENGTH_SHORT).show();
		Location location = locationManager.getLastKnownLocation(provider);
		
		map = googleMap;
		
		if (location != null) {
			addMarker(location);
		}
		
		locationManager.requestLocationUpdates(provider,0,0,this);
		
	}
	
}
