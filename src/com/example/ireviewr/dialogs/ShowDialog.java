package com.example.ireviewr.dialogs;

import android.app.AlertDialog;
import android.content.Context;

import com.example.ireviewr.R;

public class ShowDialog
{
	public static AlertDialog error(String message, Context context)
	{
		return new AlertDialog.Builder(context)
		.setIcon(R.drawable.ic_stat_alert_error)
		.setTitle(R.string.error)
		.setMessage(message)
		.show();
	}
	
	public static AlertDialog error(int message, Context context)
	{
		return new AlertDialog.Builder(context)
		.setIcon(R.drawable.ic_stat_alert_error)
		.setTitle(R.string.error)
		.setMessage(message)
		.show();
	}
}
