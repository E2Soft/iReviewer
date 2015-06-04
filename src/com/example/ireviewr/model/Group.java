package com.example.ireviewr.model;

import java.util.Date;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;

@Table(name = "UserGroup", id="_id")
public class Group extends Model 
{
	@Column(name = "group_id", 
			index=true, 
			notNull=true, 
			unique=true, 
			onUniqueConflict=ConflictAction.REPLACE)
	private int groupId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "last_modified")
	private Date lastModified;
	
	public Group() {} // required by activeandroid
	
	public Group(int groupId, String name, Date lastModified) 
	{
		this.name = name;
		this.lastModified = lastModified;
		this.groupId = groupId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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
