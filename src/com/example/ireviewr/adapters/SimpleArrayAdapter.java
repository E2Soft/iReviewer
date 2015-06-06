package com.example.ireviewr.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Jednostavan adapter koji nasledjuje AbstractArrayAdapter i popunjava jedno text polje u layoutu.
 * Potrebno je redefinisati {@link #getDataToDisplay(T)} za konkretan model.
 * @author LaptopX
 *
 * @param <T> tip modela
 */
public class SimpleArrayAdapter<T> extends AbstractArrayAdapter<T>
{
	public SimpleArrayAdapter(Context context, int resource)
	{
		super(context, resource);
	}
	
	@Override
	protected View createViewFromResource(int position, View convertView, ViewGroup parent, int resource)
	{
		View view;
		TextView text;
		
		if (convertView == null)
		{
			view = mInflater.inflate(resource, parent, false);
		}
		else
		{
			view = convertView;
		}
		
		try
		{
			if (mFieldId == 0)
			{
				// If no custom field is assigned, assume the whole resource is
				// a TextView
				text = (TextView) view;
			}
			else
			{
				// Otherwise, find the TextView field within the layout
				text = (TextView) view.findViewById(mFieldId);
			}
		} catch (ClassCastException e)
		{
			Log.e("ArrayAdapter",
					"You must supply a resource ID for a TextView");
			throw new IllegalStateException(
					"ArrayAdapter requires the resource ID to be a TextView", e);
		}
		
		T item = getItem(position);
		if (item instanceof CharSequence)
		{
			text.setText((CharSequence) item);
		}
		else
		{
			text.setText(getDataToDisplay(item)[0]);
		}
		
		return view;
	}
}
