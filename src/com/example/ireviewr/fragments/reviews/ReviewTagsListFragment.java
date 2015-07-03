package com.example.ireviewr.fragments.reviews;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ireviewr.R;
import com.example.ireviewr.adapters.AbstractArrayAdapter;
import com.example.ireviewr.adapters.TagsAdapter;
import com.example.ireviewr.dialogs.DefaultCancelListener;
import com.example.ireviewr.dialogs.ShowDialog;
import com.example.ireviewr.fragments.AbstractDetailListFragment;
import com.example.ireviewr.loaders.ModelLoaderCallbacks;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.TagToReview;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.FragmentTransition;
import com.example.ireviewr.validators.NameValidator;
import com.example.ireviewr.validators.TextValidator;

public class ReviewTagsListFragment extends AbstractDetailListFragment<Tag>
{
	public static final String RELATED_ID = "RELATED_ID";
	
	public ReviewTagsListFragment()
	{}
	
	public ReviewTagsListFragment(String itemId)
	{
		super(R.id.TAG_LOADER, R.menu.review_tags_list_menu);
		getArguments().putString(RELATED_ID, itemId);
	}

	@Override
	protected AbstractArrayAdapter<Tag> createAdapter()
	{
		return new TagsAdapter(getActivity());
	}

	@Override
	protected void onItemClick(Tag item)
	{
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelLoaderCallbacks<Tag> createLoaderCallbacks()
	{
		return new ModelLoaderCallbacks<Tag>(getActivity(), Tag.class, adapter, TagToReview.class)
		{
			@Override
			protected List<Tag> getData()
			{
				return getReview().getTags();
			}
		};
	}
	
	@Override
	protected void configureMenu(Menu menu, MenuInflater inflater)
	{
		if(getReview().isCreatedBy(CurrentUser.getId(getActivity())))
		{
			menu.findItem(R.id.menu_action)
			.setIcon(R.drawable.ic_action_edit)
			.setTitle(R.string.edit_item);
		}
		else
		{
			menu.removeItem(R.id.menu_action);
			menu.removeItem(R.id.add_action);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// handle item selection
		switch (item.getItemId()) {
			case R.id.menu_action:
				onMenuAction();
				return true;
			case R.id.add_action:
				addTagDialog();
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
		}
	}

	private void addTagDialog()
	{
		final EditText content = new EditText(getActivity());
		final AlertDialog dialog = new AlertDialog.Builder(getActivity())
		.setView(content)
		.setTitle(R.string.tag_name)
		.setPositiveButton(R.string.tag_name, new DialogInterface.OnClickListener()
		{
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String newTag = content.getText().toString().toUpperCase();
				
				Tag tag = new Tag(newTag);
				try
				{
					tag.saveOrThrow();
					getReview().addTag(tag);
				}
				catch(SQLiteConstraintException ex)
				{
					ShowDialog.error(getActivity().getString(R.string.contains_tag_message), getActivity());
				}
			}
		})
		.setNegativeButton(R.string.cancel, new DefaultCancelListener())
		.create();
		
		TextValidator tagValidator = new NameValidator(dialog, content, 10);
		content.addTextChangedListener(tagValidator);
		
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}

	private void onMenuAction()
	{
		FragmentTransition.to(new ReviewTagsCheckListFragment(getArguments().getString(RELATED_ID)), getActivity());
	}
	
	private Review getReview()
	{
		return Review.getByModelId(Review.class, getArguments().getString(RELATED_ID));
	}
}
