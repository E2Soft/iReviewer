package com.example.ireviewr.adapters;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

/**
 * Apstraktni adapter za check liste. Treba redefinisati getMainDataToDisplay(T item) koja vraca String koji se ispisuje za svaki red.
 *
 * @param <T>
 */
public abstract class AbstractCheckArrayAdapter<T> extends AbstractArrayAdapter<T>
{
	public AbstractCheckArrayAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_multiple_choice);
	}

	@Override
	protected void populateView(View view, T item)
	{
		((CheckedTextView)view).setText(getMainDataToDisplay(item));
	}
}
