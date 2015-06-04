package com.example.ireviewr.fragments.reviewobjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ireviewr.R;

public class CreateReviewObjectFragment extends Fragment {
	
	private CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 1;
	private int SELECT_PHOTO = 2;
	private String SAVED_PHOTO = "SAVED_IMAGE";
	private ImageView mImageView;
	private Bitmap bitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	//to save image taken by user when orientation change
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		/*if(bitmap != null){
			outState.putParcelable(SAVED_PHOTO, bitmap);
		}*/
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
		
		View view = inflater.inflate(R.layout.frag_4, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.reviewobject_image);
		
		/*if (savedInstanceState != null) {
			bitmap = (Bitmap) savedInstanceState.getParcelable(SAVED_PHOTO);
			if(bitmap != null){
				mImageView.setImageBitmap(bitmap);
			}
		}*/
		
		if(mImageView == null){
			Toast.makeText(getActivity(), "NULL", Toast.LENGTH_LONG).show();
		}
		
		Button chooseImage = (Button)view.findViewById(R.id.reviewobject_image_choose);
		chooseImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		
		return view;
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
	}
	
}
