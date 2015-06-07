package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

@Table(name = "Review", id="_id")
public class Review extends AbstractModel
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "dateCreated", notNull=true)
	private Date dateCreated;
	
	@Column(name = "userCreated", notNull=true, onDelete=ForeignKeyAction.CASCADE)
	private User userCreated;
	
	@Column(name = "reviewObject", notNull=true, onDelete=ForeignKeyAction.CASCADE)
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
	
	public List<Group> getGroups()
	{
		List<GroupToReview> manyToMany = getMany(GroupToReview.class, "review");
		List<Group> ret = new ArrayList<Group>();
		for(GroupToReview m2m : manyToMany)
		{
			ret.add(m2m.getGroup());
		}
        return ret;
    }
	
	public List<Comment> getComments()
	{
        return getMany(Comment.class, "review");
    }
	
	public void addComment(Comment toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		toAdd.setReview(this);
		toAdd.save();
	}
	
	public List<Tag> getTags()
	{
		List<TagToReview> manyToMany = getMany(TagToReview.class, "review");
		List<Tag> ret = new ArrayList<Tag>();
		for(TagToReview m2m : manyToMany)
		{
			ret.add(m2m.getTag());
		}
        return ret;
    }
	
	public void addTag(Tag toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		new TagToReview(this, toAdd).save();
	}
	
	public List<Image> getImages()
	{
        return getMany(Image.class, "review");
    }
	
	public void addImage(Image toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		toAdd.setReview(this);
		toAdd.save();
	}
	
	public static List<Review> filterByTags(List<Tag> tags)
	{
		From query = new Select()
		.from(Review.class).as("r");
		
		int i=0;
		for(Tag tag : tags)
		{
			query = query.join(TagToReview.class).as("t"+i)
					.on("r._id = t"+i+".review and t"+i+".tag = ?", tag.getId());
			i++;
		}
		
		return query.execute();
	}
}