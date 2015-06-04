package com.example.ireviewr.model;

import java.util.Date;

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
}
