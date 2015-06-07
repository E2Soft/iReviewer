package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

@Table(name = "UserGroup", id="_id")
public class Group extends AbstractModel 
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "userCreated", notNull=true, onDelete=ForeignKeyAction.CASCADE)
	private User userCreated;
	
	public Group() {} // required by activeandroid
	
	public Group(String modelId, Date dateModified, String name, User userCreated) {
		super(modelId, dateModified);
		this.name = name;
		this.userCreated = userCreated;
	}

	public Group(String name, User userCreated)
	{
		this.name = name;
		this.userCreated = userCreated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(User userCreated) {
		this.userCreated = userCreated;
	}
	
	public List<User> getUsers()
	{
		List<GroupToUser> manyToMany = getMany(GroupToUser.class, "group");
		List<User> ret = new ArrayList<User>();
		for(GroupToUser m2m : manyToMany)
		{
			ret.add(m2m.getUser());
		}
        return ret;
    }
	
	public void addUser(User toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		new GroupToUser(toAdd, this).save();
	}
	
	public List<Review> getReviews()
	{
		List<GroupToReview> manyToMany = getMany(GroupToReview.class, "group");
		List<Review> ret = new ArrayList<Review>();
		for(GroupToReview m2m : manyToMany)
		{
			ret.add(m2m.getReview());
		}
        return ret;
    }
	
	public void addReview(Review toAdd)
	{
		ValidationUtils.checkSaved(toAdd, this);
		new GroupToReview(toAdd, this).save();
	}
}
