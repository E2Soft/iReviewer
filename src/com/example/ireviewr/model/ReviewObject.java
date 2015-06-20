package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;


@Table(name = "ReviewObject", id="_id")
public class ReviewObject extends AbstractModel 
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "locationLong")
	private double locationLong;
	
	@Column(name = "locationLat")
	private double locationLat;
	
	@Column(name = "userCreated", notNull=true, onDelete=ForeignKeyAction.CASCADE)
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
	
	public boolean isCreatedBy(String userId)
	{
		return userCreated.getModelId().equals(userId);
	}
	
	public List<Review> getReviews()
	{
        return getMany(Review.class, "reviewObject");
    }
	
	public void addReview(Image toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		toAdd.setReviewObject(this);
		toAdd.save();
	}
	
	public List<Tag> getTags()
	{
		List<TagToReviewObject> manyToMany = getMany(TagToReviewObject.class, "reviewObject");
		List<Tag> ret = new ArrayList<Tag>();
		for(TagToReviewObject m2m : manyToMany)
		{
			ret.add(m2m.getTag());
		}
        return ret;
    }
	
	public void addTag(Tag toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		new TagToReviewObject(this, toAdd).save();
	}
	
	public void removeTag(Tag toRemove)
	{
		ValidationUtils.checkSaved(toRemove, this);
		TagToReviewObject m2m = new Select().from(TagToReviewObject.class)
				.where("reviewObject = ? and tag = ?", getId(), toRemove.getId())
				.executeSingle();
		
		if(m2m != null) 
		{
			m2m.deleteSynced();
		}
	}
	
	public List<Image> getImages()
	{
        return getMany(Image.class, "reviewObject");
    }
	
	public Image getMainImage()
	{
		return new Select().from(Image.class).where("reviewObject = ? and isMain", getId()).executeSingle();
	}
	
	public void addImage(Image toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		toAdd.setReviewObject(this);
		toAdd.save();
	}
	
	public static List<ReviewObject> filterByTags(List<Tag> tags)
	{
		From query = new Select()
		.from(ReviewObject.class).as("r");
		
		int i=0;
		for(Tag tag : tags)
		{
			query = query.join(TagToReviewObject.class).as("t"+i)
					.on("r._id = t"+i+".reviewObject and t"+i+".tag = ?", tag.getId());
			i++;
		}
		
		return query.execute();
	}

	public float getAverageRating()
	{
		float average = 0;
		List<Review> reviews = getReviews();
		for(Review review : reviews)
		{
			average += review.getRating();
		}
		average = average / reviews.size();
		return average;
	}
}
