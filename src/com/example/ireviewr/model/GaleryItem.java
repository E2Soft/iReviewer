package com.example.ireviewr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GaleryItem implements Parcelable{

	private String name;
	private String path;
	
	public GaleryItem(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(name);
		dest.writeString(path);
	}
	
	
	
}
