package com.example.ireviewr.validators;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

public class NameValidator extends TextValidator
{
	private AlertDialog dialog;
	private int maxLength;
	
	public NameValidator(AlertDialog dialog, TextView textView, int maxLength)
	{
		super(textView);
		this.dialog = dialog;
		this.maxLength = maxLength;
	}
	
	@Override
	public void validate(TextView textView, String text)
	{
		final Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		
		if(text == null || "".equals(text.trim()))
		{
			textView.setError("Name must not be empty!");
			okButton.setEnabled(false);
		}
		else if(!isAlphanumeric(text))
		{
			textView.setError("Name must contain only alphanumeric characters!");
			okButton.setEnabled(false);
		}
		else if(text.length() > maxLength)
		{
			textView.setError("Name can't be longer than {} characters!".replace("{}", Integer.toString(maxLength)));
			okButton.setEnabled(false);
		}
		else
		{
			textView.setError(null);
			okButton.setEnabled(true);
		}
	}
}
