package com.example.ireviewr.fragments.reviews;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ireviewr.R;

public class CreateReviewFragment extends Fragment {
	
	private CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 1;
	private int SELECT_PHOTO = 2;
	
	private ImageView mImageView;
	private Bitmap bitmap;
	private String name;
	private String desc;
	private String tags;
	private Float rating;
	
	private String SAVED_PHOTO = "SAVED_IMAGE";
	private String SAVED_NAME = "SAVED_NAME";
	private String SAVED_DESC = "SAVED_DESC";
	private String SAVED_RATING = "SAVED_RATING";
	private String SAVED_TAGS = "SAVED_TAGS";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
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
				Toast.makeText(getActivity(), "Save Review item pressed", Toast.LENGTH_LONG).show();
				return true;
			case R.id.cancel_item:
				Toast.makeText(getActivity(), "Cancel Review item pressed", Toast.LENGTH_LONG).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
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
			
			if(rating != null){
				outState.putFloat(SAVED_RATING, rating);
			}
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		container.removeAllViews();
		
		View view = inflater.inflate(R.layout.frag_1, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.review_image);
		TextView textName = (TextView)view.findViewById(R.id.review_name_edit);
		TextView textDesc = (TextView)view.findViewById(R.id.review_desc);
		TextView textTags = (TextView)view.findViewById(R.id.review_tags_list);
		RatingBar ratingBar = (RatingBar)view.findViewById(R.id.review_rating_choose);
		
		if (savedInstanceState != null) {
			bitmap = (Bitmap) savedInstanceState.getParcelable(SAVED_PHOTO);
			name = savedInstanceState.getString(SAVED_NAME);
			desc = savedInstanceState.getString(SAVED_DESC);
			tags = savedInstanceState.getString(SAVED_TAGS);
			rating = savedInstanceState.getFloat(SAVED_RATING);
			
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
			
			if(rating != null){
				ratingBar.setRating(rating);
			}
		}
		
		Button chooseButton =  (Button)view.findViewById(R.id.choose_review_picture);
		chooseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		
		final TextView tagContent = (TextView)view.findViewById(R.id.review_tags_list);
		
		Button choose_tags = (Button)view.findViewById(R.id.choose_tags);
		choose_tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Tags clicked", Toast.LENGTH_LONG).show();
				addTagDialog(tagContent);
			}
		});
		
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
	
	//Choose image from camera or galery
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
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.create_review);
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	//on return set up image obtained
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
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    String mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	private void galleryAddPic() throws IOException {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(createImageFile().getAbsolutePath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    getActivity().sendBroadcast(mediaScanIntent);
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
	
}
