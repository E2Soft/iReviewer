package com.example.ireviewr.fragments.reviewobjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateReviewObjectFragment extends Fragment //implements LocationListener, OnMapReadyCallback
{
	private CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 1;
	private int SELECT_PHOTO = 2;
	private ImageView mImageView;
	private MapView mapView;
	
	private Bitmap bitmap;
	private String name;
	private String desc;
	private ArrayList<String> tags;
	
	private static final String SAVED_PHOTO = "SAVED_IMAGE";
	private static final String SAVED_NAME = "SAVED_NAME";
	private static final String SAVED_DESC = "SAVED_DESC";
	private static final String SAVED_LONGITUDE = "SAVED_LONGITUDE";
	private static final String SAVED_LATITUDE = "SAVED_LATITUDE";
	private static final String SAVED_TAGS = "SAVED_TAGS";
	
	private static final double NULL_COORDINATE = 500; // nemoguca vrednost za long i lat
	
	private GoogleMap map;
	private LocationManager locationManager;
	String provider;
	
	private Marker placeMarker;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
		
		// Get LocationManager object from System Service LOCATION_SERVICE
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		
		provider = locationManager.getBestProvider(new Criteria(), true);
	}
	
	//to save image taken by user when orientation change
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(SAVED_PHOTO, bitmap);
		outState.putString(SAVED_NAME, name);
		outState.putString(SAVED_DESC, desc);
		if(placeMarker != null)
		{
			outState.putDouble(SAVED_LONGITUDE, placeMarker.getPosition().longitude);
			outState.putDouble(SAVED_LATITUDE,  placeMarker.getPosition().latitude);
		}
		else
		{
			outState.putDouble(SAVED_LONGITUDE, NULL_COORDINATE);
			outState.putDouble(SAVED_LATITUDE,  NULL_COORDINATE);
		}
		outState.putStringArrayList(SAVED_TAGS, tags);
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
		
		double longitude = NULL_COORDINATE;
		double latitude = NULL_COORDINATE;
		
		View view = inflater.inflate(R.layout.create_rev_object, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.reviewobject_image);
		TextView textName = (TextView)view.findViewById(R.id.reviewobject_name_edit);
		TextView textDesc = (TextView)view.findViewById(R.id.reviewobject_desc);
		final TextView textTags = (TextView)view.findViewById(R.id.review_object_tags_list);
		
		Button choose_tags = (Button)view.findViewById(R.id.choose_object_tags);
		choose_tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addTagDialog(textTags);
			}
		});
		
		if (savedInstanceState != null) {
			bitmap = (Bitmap) savedInstanceState.getParcelable(SAVED_PHOTO);
			name = savedInstanceState.getString(SAVED_NAME);
			desc = savedInstanceState.getString(SAVED_DESC);
			longitude = savedInstanceState.getDouble(SAVED_LONGITUDE);
			latitude = savedInstanceState.getDouble(SAVED_LATITUDE);
			tags = savedInstanceState.getStringArrayList(SAVED_TAGS);
		}
		
		if(tags == null)
		{
			tags = new ArrayList<String>();
		}
		else
		{
			textTags.setText(getTagsString(tags));
		}
		
		if(bitmap != null){
			mImageView.setImageBitmap(bitmap);
		}
		
		if(name != null){
			textName.setText(name);
		}
		
		if(desc != null){
			textDesc.setText(desc);
		}
		
		Button chooseImage = (Button)view.findViewById(R.id.reviewobject_image_choose);
		chooseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		
		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) view.findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);
		
		// Gets to GoogleMap from the MapView and does initialization stuff
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMyLocationEnabled(true);
		map.setOnMapLongClickListener(new OnMapLongClickListener()
		{
			@Override
			public void onMapLongClick(LatLng loc)
			{
				setMarker(loc);
			}
		});
		
		// iz nekog razloga nece da se updateuje pozicija markera ako mapa nema setovan ovaj listener
		map.setOnMarkerDragListener(new EmptyOnMarkerDragListener());
		
		MapsInitializer.initialize(getActivity());
		
		if(latitude != NULL_COORDINATE && longitude != NULL_COORDINATE) // ako postoje sacuvane koordinate
		{
			setMarker(new LatLng(latitude, longitude)); // podesi marker na njih
		}
		else // inace
		{
			Location location = locationManager.getLastKnownLocation(provider); // podesi na trenutnu lokaciju
			if(location != null && placeMarker == null) setMarker(location);
		}
		
		return view;
	}

	private String getTagsString(List<String> tagList)
	{
		StringBuilder tagsString = new StringBuilder();
		for(String tag : tagList)
		{
			tagsString.append(" #").append(tag);
		}
		return tagsString.toString();
	}
	
	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
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
	
	private void setMarker(Location location)
	{
		LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
		setMarker(loc);
	}
	
	private void setMarker(LatLng loc)
	{
		if(placeMarker != null){
			placeMarker.remove();
		}
		
		placeMarker = map.addMarker(new MarkerOptions()
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
		.position(loc)
		.draggable(true));
		
		CameraPosition cameraPosition = new CameraPosition
				.Builder().target(loc)
				.zoom(12).build();
		
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	private void addTagDialog(final TextView tagContent)
	{
		final EditText content = new EditText(getActivity());
		new AlertDialog.Builder(getActivity())
		.setView(content)
		.setTitle(R.string.tag_name)
		.setPositiveButton(R.string.tag_name, new DialogInterface.OnClickListener()
		{
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String newTag = content.getText().toString().toUpperCase();
				
				if(!tags.contains(newTag))
				{
					tags.add(newTag);
					tagContent.setText(getTagsString(tags));
				}
				else
				{
					Toast.makeText(getActivity(), "Already contains this tag.", Toast.LENGTH_LONG).show();
				}
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		})
		.show();
	}
	
	// iz nekog razloga nece da se updateuje pozicija markera ako mapa nema setovan ovaj listener
	// http://stackoverflow.com/questions/14829195/google-maps-error-markers-position-is-not-updated-after-drag
	private class EmptyOnMarkerDragListener implements OnMarkerDragListener
	{
		public void onMarkerDragStart(Marker arg0) {}
		public void onMarkerDragEnd(Marker arg0) {}
		public void onMarkerDrag(Marker arg0) {}
	}
}
