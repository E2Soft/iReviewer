package com.example.ireviewr.model;

import java.util.Date;

public class Group {
	
	private String name;
	private Date lastModified;
	
	public Group(String name, Date lastModified) {
		super();
		this.name = name;
		this.lastModified = lastModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
}
