package com.example.ireviewr.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Koristiti {@link #User}
 */
@Deprecated
public class UserItem implements Parcelable{
	
	private String username;
	private String email;
	private Date lastModified;
	
	public UserItem(String username, String email, Date lastModified) {
		super();
		this.username = username;
		this.email = email;
		this.lastModified = lastModified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(username);
		dest.writeString(username);
		dest.writeSerializable(lastModified);
		
	}
	
}
