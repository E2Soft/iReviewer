package com.example.ireviewr.fragments.groups;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.ReviewerTools;

public class GroupDetailFragment extends Fragment
{
	public static final String NAME = "NAME";
	public static final String LAST_MODIFIED = "LAST MODIFIED";
	public static final String ID = "ID";
	
	public GroupDetailFragment()
	{}
	
	public GroupDetailFragment(Group group)
	{
		setArguments(new Bundle());
		dataToArguments(group);
	}
	
	private void dataToArguments(Group group)
	{
		Bundle bundle = getArguments();
		bundle.putString(NAME, group.getName());
		bundle.putString(LAST_MODIFIED, ReviewerTools.preapreDate(group.getDateModified()));
		bundle.putString(ID, group.getModelId());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// handle item selection
		switch (item.getItemId()) {
			case R.id.edit_item:
				showEditDialog();
				return true;
			case R.id.delete_item:
				Toast.makeText(getActivity(), "Delete Group item pressed", Toast.LENGTH_LONG).show();
				// TODO
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void showEditDialog()
	{
		final EditText promptView = new EditText(getActivity());
		promptView.setText(getArguments().getString(NAME));
		new AlertDialog.Builder(getActivity())
		.setView(promptView)
		.setTitle(R.string.edit_group)
		.setPositiveButton(R.string.edit_item, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//get data
				String text = promptView.getText().toString();
				//create object
				Group group = getGroup();
				group.setName(text);
				try
				{
					group.saveOrThrow();
					refreshView(group);
				}
				catch(SQLiteConstraintException ex)
				{
					Toast.makeText(getActivity(), "A group with name: "+text+" already exists.", Toast.LENGTH_LONG).show();
					showEditDialog();
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		String currentUserId = CurrentUser.getId(getActivity());
		
		if(getGroup().isCreatedBy(currentUserId)) // samo ako je kreirao trenutni user
		{
			//dodati meni
			inflater.inflate(R.menu.fragment_detail_menu, menu);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.group_detail, container, false);
		
		populateView(view);
		
		return view;
	}
	
	private void populateView(View view)
	{
		Bundle bundle = getArguments();
		
		TextView gTitle = (TextView)view.findViewById(R.id.group_title);
		gTitle.setText(bundle.getString(NAME));
		
		TextView gText = (TextView)view.findViewById(R.id.group_lastModified);
		gText.setText(bundle.getString(LAST_MODIFIED));
	}
	
	private void refreshView(Group group)
	{
		dataToArguments(group);
		populateView(getView());
	}
	
	private Group getGroup()
	{
		return Group.getByModelId(Group.class, getArguments().getString(ID));
	}
}
