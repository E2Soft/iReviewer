package com.example.ireviewr.fragments.tags;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ireviewr.R;
import com.example.ireviewr.activities.TagFilterChooser;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.TagsCheckAdapter;
import com.example.ireviewr.fragments.AbstractCheckListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Tag;

public class TagFilterFragment extends AbstractCheckListFragment<Tag>
{
	public static final String CHECKED_ITEMS = "CHECKED_ITEMS";
	private ArrayList<String> checkedItems;
	
	public TagFilterFragment()
	{}
	
	public TagFilterFragment(ArrayList<String> checkedList)
	{
		super(R.id.GROUP_REVIEW_CHECK_LOADER, R.menu.standard_list_menu);
		getArguments().putStringArrayList(CHECKED_ITEMS, checkedList);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putStringArrayList(CHECKED_ITEMS, checkedItems);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null)
		{
			checkedItems = getArguments().getStringArrayList(CHECKED_ITEMS);
		}
		else
		{
			checkedItems = savedInstanceState.getStringArrayList(CHECKED_ITEMS);
		}
	}

	@Override
	protected ModelLoaderCallbacks<Tag> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Tag>(getActivity(), Tag.class, adapter);
	}
	
	@Override
	protected void onItemCheck(Tag item, boolean checked)
	{
		if(checked)
		{
			checkedItems.add(item.getModelId());
		}
		else
		{
			checkedItems.remove(item.getModelId());
		}
	}

	@Override
	protected AbstractArrayAdapter<Tag> createAdapter()
	{
		return new TagsCheckAdapter(getActivity())
		{
			@Override
			protected boolean isChecked(Tag item)
			{
				return checkedItems.contains(item.getModelId());
			}
		};
	}

	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		menu.findItem(R.id.menu_action)
		.setIcon(R.drawable.ic_action_accept)
		.setTitle(R.string.accept);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.menu_action:
				onMenuAction();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}
	
	private void onMenuAction()
	{
		((TagFilterChooser) getActivity()).returnFilterAsResult(checkedItems);
	}
}