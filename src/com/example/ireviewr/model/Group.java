package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable{
	
	private String name;
	private Date lastModified;
	private ArrayList<ReviewItem> reviews;
	private ArrayList<UserItem> users;
	
	public Group(String name, Date lastModified, ArrayList<ReviewItem> reviews,
			ArrayList<UserItem> users) {
		super();
		this.name = name;
		this.lastModified = lastModified;
		this.reviews = reviews;
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public ArrayList<ReviewItem> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<ReviewItem> reviews) {
		this.reviews = reviews;
	}

	public ArrayList<UserItem> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserItem> users) {
		this.users = users;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(reviews);
		dest.writeList(users);
		dest.writeString(name);
		dest.writeSerializable(lastModified);
	}
	
}
