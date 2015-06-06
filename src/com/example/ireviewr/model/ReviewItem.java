package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewItem implements Parcelable{
	
	private String name;
	private String description;
	private double rating;
	private UserItem creator;
	private ArrayList<Comment> comments;
	private Date created;
	private Date last_modified;
	private ArrayList<GaleryItem> images;
	private ArrayList<Tag> tags;
	private GaleryItem picture;
	
	public ReviewItem() {
		// TODO Auto-generated constructor stub
	}
	
	public ReviewItem(String name, String description, double rating,
			UserItem creator, ArrayList<Comment> comments, Date created,
			Date last_modified, ArrayList<GaleryItem> images,
			ArrayList<Tag> tags, GaleryItem picture) {
		super();
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.creator = creator;
		this.comments = comments;
		this.created = created;
		this.last_modified = last_modified;
		this.images = images;
		this.tags = tags;
		this.picture = picture;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public UserItem getCreator() {
		return creator;
	}

	public void setCreator(UserItem creator) {
		this.creator = creator;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}

	public ArrayList<GaleryItem> getImages() {
		return images;
	}

	public void setImages(ArrayList<GaleryItem> images) {
		this.images = images;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public GaleryItem getPicture() {
		return picture;
	}
	
	public void setPicture(GaleryItem picture) {
		this.picture = picture;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
		dest.writeDouble(rating);
		dest.writeParcelable(creator, flags);
		dest.writeList(comments);
		dest.writeList(images);
		dest.writeList(tags);
		dest.writeSerializable(last_modified);
		dest.writeSerializable(created);
		dest.writeParcelable(picture, flags);
	}
	
}
