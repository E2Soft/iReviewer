package com.example.ireviewr.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils
{
	public static final String	IMAGE_DIRECTORY	= "images";
	
	public static String save(Bitmap bitmapImage, String name, Context context)
	{
		ContextWrapper cw = new ContextWrapper(context);
		
		File directory = cw.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
		
		// Create imageDir
		File mypath = new File(directory, name + ".png");
		
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(mypath);
			
			// Use the compress method on the BitMap object to write image to
			// the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return mypath.getAbsolutePath();
	}
	
	public static Bitmap loadImageFromStorage(String path)
	{
		try
		{
			File f = new File(path);
			return BitmapFactory.decodeStream(new FileInputStream(f));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
