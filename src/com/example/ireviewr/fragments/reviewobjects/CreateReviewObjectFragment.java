package com.example.ireviewr.fragments.reviewobjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.dialogs.ShowDialog;
import com.example.ireviewr.exceptions.ValidationException;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.tools.ImageUtils;
import com.example.ireviewr.tools.ReviewerTools;
import com.example.ireviewr.validators.TextValidator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapView;
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
	TextView textName;
	TextView textDesc;
	private ImageView mImageView;
	private MapView mapView;
	
	private Bitmap bitmap;
	private ArrayList<String> tags;
	
	private static final String SAVED_PHOTO = "SAVED_IMAGE";
	private static final String SAVED_LONGITUDE = "SAVED_LONGITUDE";
	private static final String SAVED_LATITUDE = "SAVED_LATITUDE";
	private static final String SAVED_TAGS = "SAVED_TAGS";
	
	private static final double NULL_COORDINATE = 500; // nemoguca vrednost za long i lat
	
	private GoogleMap map;
	private LocationManager locationManager;
	String provider;
	
	private Marker placeMarker;
	
	TextValidator nameValidator;
	
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
				save();
				return true;
			case R.id.cancel_item:
				FragmentTransition.remove(this, getActivity());
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void save()
	{
		String name = textName.getText().toString();
		String desc = textDesc.getText().toString();
		
		// validate
		try
		{
			if(placeMarker == null)
			{
				ShowDialog.error("Please enter place location.", getActivity());
				return;
			}
			nameValidator.validate();
		}
		catch(ValidationException ex)
		{
			return;
		}
		
		// save
		try
		{
			ReviewObject newReviewObject = new ReviewObject(name, desc, placeMarker.getPosition().longitude, 
					placeMarker.getPosition().latitude, 
					CurrentUser.getModel(getActivity()));
			newReviewObject.saveOrThrow();
			
			for(String tagName : tags)
			{
				Tag tag = Tag.getByName(tagName);
				if(tag == null)
				{
					tag = new Tag(tagName);
					tag.saveOrThrow();
				}
				
				newReviewObject.addTag(tag);
			}
			
			if(bitmap != null)
			{
				// ime slike je <id_rev_objekta>_0, ostale slike su <id_rev_objekta>_<broj_slika + 1>
				String imagePath = ImageUtils.save(bitmap, newReviewObject.getModelId()+"_0", getActivity());
				new Image(imagePath, true, newReviewObject).saveOrThrow();
			}
			
			Toast.makeText(getActivity(), R.string.created, Toast.LENGTH_SHORT).show();
			FragmentTransition.remove(this, getActivity());
		}
		catch(SQLiteConstraintException ex)
		{
			ShowDialog.error("Error while saving.", getActivity());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		double longitude = NULL_COORDINATE;
		double latitude = NULL_COORDINATE;
		
		View view = inflater.inflate(R.layout.create_rev_object, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.reviewobject_image);
		textName = (TextView)view.findViewById(R.id.reviewobject_name_edit);
		textDesc = (TextView)view.findViewById(R.id.reviewobject_desc);
		final TextView textTags = (TextView)view.findViewById(R.id.review_object_tags_list);
		
		ImageButton choose_tags = (ImageButton)view.findViewById(R.id.choose_object_tags);
		choose_tags.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addTagDialog(textTags);
			}
		});
		
		ImageButton remove_tags = (ImageButton)view.findViewById(R.id.remove_object_tags);
		remove_tags.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tags.size() > 0) 
				{
					tags.remove(tags.size()-1);
					textTags.setText(ReviewerTools.getTagsString(tags));
				}
			}
		});
		
		nameValidator = new TextValidator(textName)
		{
			@Override
			public void validate(TextView textView, String text)
			{
				if(text == null || "".equals(text.trim()))
				{
					textView.setError("Name must not be empty!");
					throw new ValidationException();
				}
				else
				{
					textView.setError(null);
				}
			}
		};
		textName.addTextChangedListener(nameValidator);
		
		if (savedInstanceState != null) {
			bitmap = (Bitmap) savedInstanceState.getParcelable(SAVED_PHOTO);
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
			textTags.setText(ReviewerTools.getTagsString(tags));
		}
		
		if(bitmap != null){
			mImageView.setImageBitmap(bitmap);
		}
		
		ImageButton chooseImage = (ImageButton)view.findViewById(R.id.reviewobject_image_choose);
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
					tagContent.setText(ReviewerTools.getTagsString(tags));
				}
				else
				{
					ShowDialog.error("Already contains this tag.", getActivity());
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
