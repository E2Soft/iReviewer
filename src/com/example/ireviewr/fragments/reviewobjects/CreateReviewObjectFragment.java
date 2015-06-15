package com.example.ireviewr.fragments.reviewobjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.dialogs.LocationDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateReviewObjectFragment extends Fragment implements LocationListener, OnMapReadyCallback{
	
	private CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 1;
	private int SELECT_PHOTO = 2;
	private ImageView mImageView;
	
	private Bitmap bitmap;
	private String name;
	private String desc;
	private String tags;
	
	private String SAVED_PHOTO = "SAVED_IMAGE";
	private String SAVED_NAME = "SAVED_NAME";
	private String SAVED_DESC = "SAVED_DESC";
	private String SAVED_TAGS = "SAVED_TAGS";
	
	private GoogleMap map;
	private SupportMapFragment mMapFragment;
	private LocationManager locationManager;
	String provider;
	
	private Marker home;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
		
		// Get LocationManager object from System Service LOCATION_SERVICE
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
	}
	
	//to save image taken by user when orientation change
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		if(bitmap != null){
			outState.putParcelable(SAVED_PHOTO, bitmap);
		}
		
		if(name != null){
			outState.putString(SAVED_NAME, name);
		}
		
		if(desc != null){
			outState.putString(SAVED_DESC, desc);
		}
		
		if(tags != null){
			outState.putString(SAVED_TAGS, tags);
		}
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.create_review_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.save_item:
				Toast.makeText(getActivity(), "Save ReviewObject item pressed", Toast.LENGTH_LONG).show();
				return true;
			case R.id.cancel_item:
				Toast.makeText(getActivity(), "Cancel ReviewObject item pressed", Toast.LENGTH_LONG).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.create_rev_object, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.reviewobject_image);
		TextView textName = (TextView)view.findViewById(R.id.reviewobject_name_edit);
		TextView textDesc = (TextView)view.findViewById(R.id.reviewobject_desc);
		final TextView textTags = (TextView)view.findViewById(R.id.review_object_tags_list);
		
		Button choose_tags = (Button)view.findViewById(R.id.choose_object_tags);
		choose_tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Tags clicked", Toast.LENGTH_LONG).show();
				addTagDialog(textTags);
			}
		});
		
		if (savedInstanceState != null) {
			bitmap = (Bitmap) savedInstanceState.getParcelable(SAVED_PHOTO);
			name = savedInstanceState.getString(SAVED_NAME);
			desc = savedInstanceState.getString(SAVED_DESC);
			tags = savedInstanceState.getString(SAVED_TAGS);
			
			if(bitmap != null){
				mImageView.setImageBitmap(bitmap);
			}
			
			if(name != null){
				textName.setText(name);
			}
			
			if(desc != null){
				textDesc.setText(desc);
			}
			
			if(tags != null){
				textTags.setText(tags);
			}
		}
		
		Button chooseImage = (Button)view.findViewById(R.id.reviewobject_image_choose);
		chooseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		
		mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.reviewobject_map, mMapFragment).commit();
        
        mMapFragment.getMapAsync(this);
		
		return view;
	}
	
	private void addTagDialog(final TextView tagContent){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		final View promptView = layoutInflater.inflate(R.layout.add_tag_layout, null);
		alertDialogBuilder.setView(promptView);
		
		alertDialogBuilder.setCancelable(false)
			.setPositiveButton(R.string.tag_name, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText content = (EditText)promptView.findViewById(R.id.tag_content);
					
					String oldValue = tagContent.getText().toString();
					String newValue = oldValue+" #"+content.getText().toString();
					
					tagContent.setText(newValue);
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
			});
		
		
		// create an alert dialog
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
	
	private void selectImage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int position) {
				if (items[position].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, REQUEST_CAMERA);
				}else if (items[position].equals("Choose from Library")) {
					Intent intent = new Intent(Intent.ACTION_PICK,
												MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(Intent.createChooser(intent, "Select photo"),
											SELECT_PHOTO);
				}else if (items[position].equals("Cancel")) {
					dialog.cancel();
				}
			}
			
		});
		builder.show();
	}
	
	private void setUpImage(Intent data){
		InputStream stream = null;
		try {
			// recyle unused bitmaps
	        if (bitmap != null) {
	        	bitmap.recycle();
	        }
	        
	        stream = getActivity().getContentResolver().openInputStream(data.getData());

	        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(stream), 
	        									mImageView.getWidth(), mImageView.getHeight(), 
	        									true);
	        
	        mImageView.setImageBitmap(bitmap);
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if (stream != null){
				try {
					stream.close();
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
			}
		}
		
	}
	
	private void takePhoto(Bundle extras){
		// recyle unused bitmaps
        if (bitmap != null) {
        	bitmap.recycle();
        }
		
        bitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(bitmap);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if(requestCode == REQUEST_CAMERA){
				takePhoto(data.getExtras());
			}else if(requestCode == SELECT_PHOTO){
				setUpImage(data);
			}
	    }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.home);
		//postaviti da fragment ima meni
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
	public void onMapReady(GoogleMap googleMap) {
		Toast.makeText(getActivity(), "onMapReady()", Toast.LENGTH_SHORT).show();
		Location location = locationManager.getLastKnownLocation(provider);
		
		map = googleMap;
		
		if (location != null) {
			addMarker(location);
		}
		
		locationManager.requestLocationUpdates(provider,0,0,this);
		
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
	
}
