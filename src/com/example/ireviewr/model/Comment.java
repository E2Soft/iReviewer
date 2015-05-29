package com.example.ireviewr.model;

import java.util.Date;

public class Comment {
	
	private String content;
	private Date dateCreated;
	private String userCreated;
	
	public Comment(String content, Date dateCreated, String userCreated) {
		super();
		this.content = content;
		this.dateCreated = dateCreated;
		this.userCreated = userCreated;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getUserCreated() {
		return userCreated;
	}
	
	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}
	
}
