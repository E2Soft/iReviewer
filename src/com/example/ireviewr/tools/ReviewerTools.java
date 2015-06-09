package com.example.ireviewr.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.example.ireviewr.R;

public class ReviewerTools {

	public static String PATTERN = "dd.MM.yyyy";
	
	@SuppressLint("SimpleDateFormat")
	public static String preapreDate(Date date) {
		
		SimpleDateFormat formater = new SimpleDateFormat(PATTERN);
		
		return formater.format(date);
	}
	
	public static void setImageFromPath(ImageView imageView, String path)
	{
		if(path != null)
		{
			File imgFile = new  File(path);
			if(imgFile.exists())
			{
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    imageView.setImageBitmap(myBitmap);
			    return ;
			}
		}
		
		imageView.setImageResource(R.drawable.ic_action_picture);
	}
}
