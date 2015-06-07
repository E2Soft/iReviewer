package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tag", id="_id")
public class Tag extends AbstractModel
{
	@Column(name = "name", notNull=true, unique=true)
	private String name;

	public Tag() {} // required by activeandroid
	
	public Tag(String modelId, Date dateModified, String name)
	{
		super(modelId, dateModified);
		this.name = name;
	}

	public Tag(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Review> getReviews()
	{
		List<TagToReview> manyToMany = getMany(TagToReview.class, "tag");
		List<Review> ret = new ArrayList<Review>();
		for(TagToReview m2m : manyToMany)
		{
			ret.add(m2m.getReview());
		}
        return ret;
    }
	
	public List<ReviewObject> getReviewObjects()
	{
		List<TagToReviewObject> manyToMany = getMany(TagToReviewObject.class, "tag");
		List<ReviewObject> ret = new ArrayList<ReviewObject>();
		for(TagToReviewObject m2m : manyToMany)
		{
			ret.add(m2m.getReviewObject());
		}
        return ret;
    }
}
