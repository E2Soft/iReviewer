package com.example.ireviewr.model;

import java.util.Date;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ForeignKeyAction;

@Table(name = "ReviewObject", id="_id")
public class ReviewObject extends AbstractModel 
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location_long")
	private double locationLong;
	
	@Column(name = "location_lat")
	private double locationLat;
	
	@Column(name = "user_created", notNull=true, onDelete=ForeignKeyAction.CASCADE)
	private User userCreated;

	public ReviewObject() {} // required by activeandroid
	
	public ReviewObject(String modelId, Date dateModified, String name,
			String description, double locationLong, double locationLat, User userCreated)
	{
		super(modelId, dateModified);
		this.name = name;
		this.description = description;
		this.locationLong = locationLong;
		this.locationLat = locationLat;
		this.userCreated = userCreated;
	}

	public ReviewObject(String name, String description,
			double locationLong, double locationLat, User userCreated)
	{
		this.name = name;
		this.description = description;
		this.locationLong = locationLong;
		this.locationLat = locationLat;
		this.userCreated = userCreated;
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

	public double getLocationLong() {
		return locationLong;
	}

	public double getLocationLat() {
		return locationLat;
	}
	
	public void setLocation(double locationLong, double locationLat) {
		this.locationLong = locationLong;
		this.locationLat = locationLat;
	}

	public User getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(User userCreated) {
		this.userCreated = userCreated;
	}
}
