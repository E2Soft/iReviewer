package com.example.ireviewr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User", id="_id")
public class User extends AbstractModel
{
	@Column(name = "name", notNull=true, unique=true)
	private String name;
	
	@Column(name = "email", notNull=true, unique=true)
	private String email;

	public User() {} // required by activeandroid
	
	public User(String modelId, Date dateModified, String name, String email)
	{
		super(modelId, dateModified);
		this.name = name;
		this.email = email;
	}

	public User(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Grupe koje je kreirao.
	 */
	public List<Group> getGroupsCreated()
	{
        return getMany(Group.class, "userCreated");
    }
	
	/**
	 * Grupe u kojima je clan.
	 */
	public List<Group> getGroupsMember()
	{
		List<GroupToUser> manyToMany = getMany(GroupToUser.class, "user");
		List<Group> ret = new ArrayList<Group>();
		for(GroupToUser m2m : manyToMany)
		{
			ret.add(m2m.getGroup());
		}
		return ret;
    }
	
	public List<Comment> getComments()
	{
        return getMany(Comment.class, "userCreated");
    }
	
	public List<Review> getReviews()
	{
        return getMany(Review.class, "userCreated");
    }
	
	public List<ReviewObject> getReviewObjects()
	{
        return getMany(ReviewObject.class, "userCreated");
    }
}
