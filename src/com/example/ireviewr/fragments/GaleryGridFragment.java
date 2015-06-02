package com.example.ireviewr.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.GaleryAdapter;
import com.example.ireviewr.model.GaleryItem;

public class GaleryGridFragment extends Fragment {

	private CharSequence[] itemNames = { "Take Photo", "Choose from Library", "Cancel" };
	private int REQUEST_CAMERA = 100;
	private int SELECT_PHOTO = 200;
	private ArrayList<GaleryItem> items;
	private ArrayAdapter<GaleryItem> myAdapter;
	
	public GaleryGridFragment(ArrayList<GaleryItem> items) {
		this.items = items;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		//dodati meni
		inflater.inflate(R.menu.activity_itemdetail, menu);
		SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
		
		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                myAdapter.getFilter().filter(newText.toString());
                //System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                //myAdapter.getFilter().filter(query.toString());
                //System.out.println("on query submit: "+query);
                return false;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
			case R.id.add_item:
				Toast.makeText(getActivity(), "Add Galery item", Toast.LENGTH_SHORT).show();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	//Choose image from camera or galery
		private void selectImage() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Add Photo");
			builder.setItems(itemNames, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int position) {
					if (itemNames[position].equals("Take Photo")) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, REQUEST_CAMERA);
					}else if (itemNames[position].equals("Choose from Library")) {
						Intent intent = new Intent(Intent.ACTION_PICK,
													MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, "Select photo"),
												SELECT_PHOTO);
					}else if (itemNames[position].equals("Cancel")) {
						dialog.cancel();
					}
				}
				
			});
			builder.show();
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 View view = inflater.inflate(R.layout.galery_layout, container, false);
		
		 myAdapter = new GaleryAdapter(getActivity(), R.layout.comment_item, items);
			//setListAdapter(new MyListAdapter(getActivity(), R.layout.drawer_list_item, items));
		 GridView gridview = (GridView)view.findViewById(R.id.gridview);
		 gridview.setAdapter(myAdapter);
		 //gridview.setOnItemClickListener(this);
		 
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(R.string.galery);
		setHasOptionsMenu(true);
	}
}
