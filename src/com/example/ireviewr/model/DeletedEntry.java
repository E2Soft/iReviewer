package com.example.ireviewr.model;

import java.util.Date;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DeletedEntry", id="_id")
public class DeletedEntry extends Model
{
	@Column(name = "tableName", notNull=true)
	private String tableName;
	
	@Column(name = "deletedModelId", notNull=true)
	private String deletedModelId;
	
	@Column(name = "dateDeleted", notNull=true)
	private Date dateDeleted;

	public DeletedEntry() {} // required by activeandroid
	
	public DeletedEntry(String tableName, String deletedModelId, Date dateDeleted)
	{
		this.tableName = tableName;
		this.deletedModelId = deletedModelId;
		this.dateDeleted = dateDeleted;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getDeletedModelId()
	{
		return deletedModelId;
	}

	public void setDeletedModelId(String deletedModelId)
	{
		this.deletedModelId = deletedModelId;
	}

	public Date getDateDeleted()
	{
		return dateDeleted;
	}

	public void setDateDeleted(Date dateDeleted)
	{
		this.dateDeleted = dateDeleted;
	}
}
