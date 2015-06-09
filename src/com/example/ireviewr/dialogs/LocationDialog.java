package com.example.ireviewr.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class LocationDialog extends AlertDialog.Builder{

	public LocationDialog(Context context) {
		super(context, AlertDialog.THEME_HOLO_LIGHT);
		
		setUpDialog();
	}
	
	private void setUpDialog(){
		setTitle("Ooops");
	    setMessage("Your Locations seems to be disabled, do you want to enable it?");
	    setCancelable(false);
	    
	    setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
         	   getContext().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
               dialog.dismiss();
            }
        });
	    
	    setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 dialog.cancel();
            }
        });
	    
	}
	
	public AlertDialog prepareDialog(){
		AlertDialog dialog = create();
		dialog.setCanceledOnTouchOutside(false);
		
		return dialog;
	}
	
}
