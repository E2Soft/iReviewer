package com.example.ireviewr.fragments.reviews;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.ireviewr.dialogs.ShowDialog;
import com.example.ireviewr.exceptions.ValidationException;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.tools.ImageUtils;
import com.example.ireviewr.tools.ReviewerTools;
import com.example.ireviewr.validators.TextValidator;

public class CreateReviewFragment extends Fragment {
	
	private CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 1;
	private int SELECT_PHOTO = 2;
	
	public static final String ID = "ID";
	public static final String RELATED_ID = "RELATED_ID";
	public static final String NAME ="NAME";
	public static final String DESCRIPTION ="DESCRIPTION";
	public static final String CREATED = "CREATED";
	public static final String LAST_MODIFIED = "LAST MODIFIED";
	public static final String IMAGE = "IMAGE";
	public static final String RATING = "RATING";
	private static final String TAGS = "TAGS";

	private ImageView mImageView;
	private TextView textName;
	private TextView textDesc;
	private TextView textTags;
	private RatingBar ratingBar;
	
	private String id;
	private String revobId;
	private Bitmap bitmap;
	private boolean editedImage = false;
	private String name;
	private String desc;
	private Float rating;
	private ArrayList<String> tags;
	
	private TextValidator nameValidator;
		
	public static CreateReviewFragment newInstance() 
	{
		return new CreateReviewFragment();	
	}
	
	/**
	 * Za create.
	 * @param revobId id ReviewOobjecta
	 */
	public static CreateReviewFragment newCreateInstance(String revobId) 
	{
		CreateReviewFragment newFrag = new CreateReviewFragment();
		Bundle bundle = new Bundle();
		
		bundle.putString(RELATED_ID, revobId);
		
		newFrag.setArguments(bundle);
		return newFrag;	
	}
	
	/**
	 * Za update.
	 * @param modelId postojeceg Reviewa
	 */
	public static CreateReviewFragment newEditInstance(String modelId) 
	{
		CreateReviewFragment newFrag = new CreateReviewFragment();
		Bundle bundle = new Bundle();
		
		Review review = Review.getByModelId(Review.class, modelId);
		
		bundle.putString(ID, modelId);
		bundle.putString(NAME, review.getName());
		bundle.putString(DESCRIPTION, review.getDescription());
		bundle.putFloat(RATING, review.getRating());
		bundle.putString(CREATED,ReviewerTools.preapreDate(review.getDateCreated()));
		bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(review.getDateModified()));
		
		Image image = review.getMainImage();
		if(image != null)
		{
			bundle.putString(IMAGE, image.getPath());
		}

		ArrayList<String> tagnames = new ArrayList<String>();
		for(Tag tag : review.getTags())
		{
			tagnames.add(tag.getName());
		}
		bundle.putStringArrayList(TAGS, tagnames);
		
		newFrag.setArguments(bundle);
		return newFrag;
	}

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
				save();
				return true;
			case R.id.cancel_item:
				FragmentTransition.remove(this, getActivity());
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
			
		outState.putString(ID, id);
		outState.putString(RELATED_ID, revobId);
		
		if(bitmap != null)
		{
			outState.putParcelable(IMAGE, bitmap);
		}
			
		if(name != null)
		{
			outState.putString(NAME, name);
		}
			
		if(desc != null)
		{
			outState.putString(DESCRIPTION, desc);
		}
			
		if(tags != null)
		{
			outState.putStringArrayList(TAGS, tags);
		}
			
		if(rating != null)
		{
			outState.putFloat(RATING, rating);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		container.removeAllViews();
		
		View view = inflater.inflate(R.layout.create_review, container, false);
		
		mImageView = (ImageView)view.findViewById(R.id.review_image);
		textName = (TextView)view.findViewById(R.id.review_name_edit);
		textDesc = (TextView)view.findViewById(R.id.review_desc);
		textTags = (TextView)view.findViewById(R.id.review_tags_list);
	    ratingBar = (RatingBar)view.findViewById(R.id.review_rating_choose);
		
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
	    
		if (savedInstanceState != null) 
		{
			id = savedInstanceState.getString(ID);
			revobId = savedInstanceState.getString(RELATED_ID);
			bitmap = (Bitmap) savedInstanceState.getParcelable(IMAGE);
			name = savedInstanceState.getString(NAME);
			desc = savedInstanceState.getString(DESCRIPTION);
			tags = savedInstanceState.getStringArrayList(TAGS);
			rating = savedInstanceState.getFloat(RATING);
		}
		else
		{
			Bundle arguments = getArguments();
			if(arguments != null)
			{
				id = arguments.getString(ID);
				revobId = arguments.getString(RELATED_ID);
				name = arguments.getString(NAME);
				desc = arguments.getString(DESCRIPTION);
				String imagePath = arguments.getString(IMAGE);
				if(imagePath != null)
				{
					bitmap = ImageUtils.loadImageFromStorage(imagePath);
				}
				tags = arguments.getStringArrayList(TAGS);
				rating = arguments.getFloat(RATING);
			}
			
		}
		
		if(bitmap != null)
		{
			mImageView.setImageBitmap(bitmap);
		}
			
		if(name != null)
		{
			textName.setText(name);
		}
			
		if(desc != null)
		{
			textDesc.setText(desc);
		}
			
		if(tags == null)
		{
			tags = new ArrayList<String>();
		}
		else
		{
			textTags.setText(ReviewerTools.getTagsString(tags));
		}
			
		if(rating != null)
		{
			ratingBar.setRating(rating);
		}		
		
		Button chooseButton =  (Button)view.findViewById(R.id.choose_review_picture);
		chooseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		
		Button choose_tags = (Button)view.findViewById(R.id.choose_tags);
		choose_tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addTagDialog(textTags);
			}
		});
		
		Button removeTags = (Button)view.findViewById(R.id.remove_review_tags);
		removeTags.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tags.size() > 0) 
				{
					tags.remove(tags.size()-1);
					textTags.setText(ReviewerTools.getTagsString(tags));
				}
			}
		});

		return view;
	}
	
	private void save()
	{
		String name = textName.getText().toString();
		String desc = textDesc.getText().toString();
		Float rating = ratingBar.getRating();
			
		try
		{
			nameValidator.validate();
		}
		catch(ValidationException ex)
		{
			return;
		}
		
		try
		{
			Review newReview;
			ReviewObject revob;
				
			if(id != null)
			{
				newReview = Review.getByModelId(Review.class, id);		
				newReview.setName(name);
				newReview.setDescription(desc);
				newReview.setRating(rating);
				newReview.setDateModified(new Date());
			}
			else 
			{
				revob = ReviewObject.getByModelId(ReviewObject.class, revobId);	
				Log.d("revob", "revob :"+revob);
				newReview = new Review(name, desc, rating, new Date(), CurrentUser.getModel(getActivity()), revob);
			}
	
			newReview.saveOrThrow();
			Log.d("EDIT", "edited review : "+newReview.getModelId()+" "+newReview.getName()+" "
					+ ""+newReview.getRating()+" "+newReview.getDateModified()+" "+newReview.getDescription()+" "
					+newReview.getUserCreated().getName()+" "+newReview.getReviewObject().getName());	
			
			List<Tag> existingTagModels = newReview.getTags();
			List<String> existingTagNames = new ArrayList<String>();
				
			for(Tag tag : existingTagModels)
			{
				
				if(!tags.contains(tag.getName())) 
				{
					newReview.removeTag(tag); 
				}
				else
				{
					existingTagNames.add(tag.getName());
				}
			}
				
			for(String tagName : tags)
			{
				if(!existingTagNames.contains(tagName)) 
				{
					Tag tag = Tag.getByName(tagName);
					if(tag == null)
					{
						tag = new Tag(tagName);
						tag.saveOrThrow();
					}
						
					newReview.addTag(tag);
				}
			}
				
			if(bitmap != null && editedImage)
			{
				Image oldMainImage = newReview.getMainImage();
				if(oldMainImage != null)
				{
					oldMainImage.setMain(false);
					oldMainImage.save();
				}
					
				String imagePath = ImageUtils.save(bitmap, newReview.getModelId()+"_"+newReview.getImages().size(), 
						getActivity());
				new Image(imagePath, true, newReview).saveOrThrow();
			}
				
			if(id == null)
			{
				Toast.makeText(getActivity(), R.string.created, Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getActivity(), R.string.edited, Toast.LENGTH_SHORT).show();
			}
				
			FragmentTransition.remove(this, getActivity());
		}
		catch(SQLiteConstraintException ex)
		{
			ShowDialog.error("Error while saving.", getActivity());
		}
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
	
	@Override
	public void onDestroyView() {
		super.onDestroy();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	
	private void setUpImage(Intent data){
		InputStream stream = null;
		try {
	        if (bitmap != null) 
	        {
	        	bitmap.recycle();
	        }
	        
	        stream = getActivity().getContentResolver().openInputStream(data.getData());

			bitmap = BitmapFactory.decodeStream(stream);
			editedImage = true;
			
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

	    image.getAbsolutePath();
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

        if (bitmap != null)
        {
        	bitmap.recycle();
        }
		
        bitmap = (Bitmap) extras.get("data");
        editedImage = true;
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
