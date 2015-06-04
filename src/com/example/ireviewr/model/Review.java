package com.example.ireviewr.model;

import java.util.Date;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ForeignKeyAction;

@Table(name = "Review", id="_id")
public class Review extends AbstractModel
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "date_created", notNull=true)
	private Date dateCreated;
	
	@Column(name = "user_created", notNull=true, onDelete=ForeignKeyAction.CASCADE)
	private User userCreated;
	
	@Column(name = "review_object", notNull=true, onDelete=ForeignKeyAction.CASCADE)
	private ReviewObject reviewObject;

	public Review() {} // required by activeandroid
	
	public Review(String modelId, Date dateModified, String name,
			String description, int rating, Date dateCreated,
			User userCreated, ReviewObject reviewObject) 
	{
		super(modelId, dateModified);
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.dateCreated = dateCreated;
		this.userCreated = userCreated;
		this.reviewObject = reviewObject;
	}

	public Review(String name, String description, int rating, Date dateCreated, 
			User userCreated, ReviewObject reviewObject) 
	{
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.dateCreated = dateCreated;
		this.userCreated = userCreated;
		this.reviewObject = reviewObject;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(User userCreated) {
		this.userCreated = userCreated;
	}

	public ReviewObject getReviewObject() {
		return reviewObject;
	}

	public void setReviewObject(ReviewObject reviewObject) {
		this.reviewObject = reviewObject;
	}
}
