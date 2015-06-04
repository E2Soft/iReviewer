package com.example.ireviewr.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable{
	private String name;
	private Date dateCreated;
	
	public Tag(String name, Date dateCreated) {
		super();
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeSerializable(dateCreated);
		
	}
}
