package com.example.ireviewr.model;

import java.util.Date;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ForeignKeyAction;

@Table(name = "UserGroup", id="_id")
public class Group extends AbstractModel 
{
	@Column(name = "name", notNull=true)
	private String name;
	
	@Column(name = "user_created", notNull=true, onDelete=ForeignKeyAction.CASCADE)
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
}
