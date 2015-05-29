package com.example.ireviewr.model;

import java.util.Date;

public class Tag {
	private String name;
	private Date dateCreated;
	
	public Tag(String name, Date dateCreated) {
		super();
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
