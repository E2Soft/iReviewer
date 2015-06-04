package com.example.ireviewr.model;

import java.util.Date;

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
}
