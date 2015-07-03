package com.example.ireviewr.fragments.groups;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
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
import com.example.ireviewr.dialogs.DefaultCancelListener;
import com.example.ireviewr.loaders.ModelObserver;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.GroupToReview;
import com.example.ireviewr.model.GroupToUser;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.validators.NameValidator;
import com.example.ireviewr.validators.TextValidator;

public class GroupDetailFragment extends Fragment
{
	public static final String NAME = "NAME";
	public static final String USER_COUNT = "USER_COUNT";
	public static final String REVIEW_COUNT = "REVIEW_COUNT";
	public static final String USER_CREATED = "USER_CREATED";
	public static final String ID = "ID";
	
	private ModelObserver modelObserver;
	
	public static GroupDetailFragment newInstance(Group group)
	{
		GroupDetailFragment newFrag = new GroupDetailFragment();
		newFrag.setArguments(new Bundle());
		newFrag.dataToArguments(group);
		return newFrag;
	}
	
	private void dataToArguments(Group group)
	{
		Bundle bundle = getArguments();
		bundle.putString(NAME, group.getName());
		bundle.putInt(USER_COUNT, group.getUsers().size());
		bundle.putInt(REVIEW_COUNT, group.getReviews().size());
		bundle.putString(USER_CREATED, group.getUserCreated().getName());
		bundle.putString(ID, group.getModelId());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//postaviti da fragment ima meni
		setHasOptionsMenu(true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		modelObserver = new ModelObserver(activity, GroupToUser.class, GroupToReview.class, Group.class)
		{
			@Override
			public void onChange(boolean selfChange, Uri uri)
			{
				refreshView(getGroup());
			}
		};
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		modelObserver.unRegister();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		User currentUser = CurrentUser.getModel(getActivity());
		String currentUserId = currentUser.getModelId();
		
		//dodati meni
		inflater.inflate(R.menu.group_detail_menu, menu);
		
		MenuItem joinGroupItem = menu.findItem(R.id.join_group);
		if(currentUser.isInGroup(getArguments().getString(ID)))
		{
			joinGroupItem.setIcon(R.drawable.ic_people_white_36dp);
			joinGroupItem.setTitle(R.string.leave_group);
		}
		else
		{
			joinGroupItem.setIcon(R.drawable.ic_people_outline_white_36dp);
			joinGroupItem.setTitle(R.string.join_group);
		}
		
		if(!getGroup().isCreatedBy(currentUserId)) // ako nije kreirao trenutni user
		{
			menu.removeItem(R.id.edit_item);
			menu.removeItem(R.id.delete_item);
		}
	}
	
	
	@SuppressLint("InflateParams")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.edit_item:
				showEditDialog();
				return true;
			case R.id.delete_item:
				showDeleteDialog();
				return true;
			case R.id.join_group:
				User currentUser = CurrentUser.getModel(getActivity());
				Group group = getGroup();
				
				if(currentUser.isInGroup(getArguments().getString(ID)))
				{
					group.removeUser(currentUser);
					item.setIcon(R.drawable.ic_people_outline_white_36dp);
					item.setTitle(R.string.join_group);
				}
				else
				{
					group.addUser(currentUser);
					item.setIcon(R.drawable.ic_people_white_36dp);
					item.setTitle(R.string.leave_group);
				}
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void showEditDialog()
	{
		final EditText promptView = new EditText(getActivity());
		promptView.setText(getArguments().getString(NAME));
		final AlertDialog dialog = new AlertDialog.Builder(getActivity())
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
				group.setDateModified(new Date());
				
				group.save();
				Toast.makeText(getActivity(), R.string.edited, Toast.LENGTH_SHORT).show();
			}
		})
		.setNegativeButton(R.string.cancel, new DefaultCancelListener())
		.create();
		
		TextValidator nameValidator = new NameValidator(dialog, promptView, 20);
		promptView.addTextChangedListener(nameValidator);
		
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}
	
	private void showDeleteDialog()
	{
		new AlertDialog.Builder(getActivity())
		.setTitle(R.string.remove_item)
		.setMessage(R.string.are_you_sure)
		.setPositiveButton(R.string.remove_item, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// obrisi grupu
				getGroup().deleteSynced();
				
				Toast.makeText(getActivity(), R.string.deleted, Toast.LENGTH_SHORT).show();
				
				// obrisi ovaj fragment
				FragmentTransition.remove(GroupDetailFragment.this, getActivity());
			}
		})
		.setNegativeButton(R.string.cancel, new DefaultCancelListener())
		.show();
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
		
		TextView gUserCount = (TextView)view.findViewById(R.id.group_user_count);
		gUserCount.setText(Integer.toString(bundle.getInt(USER_COUNT)));
		
		TextView gReviewCount = (TextView)view.findViewById(R.id.group_review_count);
		gReviewCount.setText(Integer.toString(bundle.getInt(REVIEW_COUNT)));
		
		TextView gUserCreated = (TextView)view.findViewById(R.id.group_user_created);
		gUserCreated.setText(bundle.getString(USER_CREATED));
	}
	
	private void refreshView(Group group)
	{
		if(group != null) dataToArguments(group);
		if(getView() != null) populateView(getView());
	}
	
	private Group getGroup()
	{
		return Group.getByModelId(Group.class, getArguments().getString(ID));
	}
}
