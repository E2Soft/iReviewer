package com.example.ireviewr.model;

import java.util.Date;
import java.util.UUID;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.query.Select;

public abstract class AbstractModel extends Model 
{
	@Column(name = "modelId", 
			index=true, 
			notNull=true, 
			unique=true, 
			onUniqueConflict=ConflictAction.REPLACE)
	private String modelId = null;
	
	@Column(name = "dateModified", notNull=true)
	private Date dateModified;

	public AbstractModel() 
	{
		this.modelId = UUID.randomUUID().toString(); // ako je nov objekat generisi random id
		this.dateModified = new Date(); // dateModified je sad kad je kreiran
	}
	
	public AbstractModel(String modelId, Date dateModified) 
	{
		this.modelId = modelId;
		this.dateModified = dateModified;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public String getModelId() {
		return modelId;
	}
	
	public static <T extends AbstractModel> T getByModelId(Class<T> modelClass,String modelId)
	{
		return new Select().from(modelClass).where("modelId = ?", modelId).executeSingle();
	}
}
