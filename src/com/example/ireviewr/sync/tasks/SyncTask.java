package com.example.ireviewr.sync.tasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.elevated_surge_702.crud.Crud;
import com.appspot.elevated_surge_702.crud.model.MessagesCommentMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesCommentMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupToReviewMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupToReviewMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupToUserMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesGroupToUserMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesImageMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesImageMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesReviewMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesReviewMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesReviewObjectMessage;
import com.appspot.elevated_surge_702.crud.model.MessagesReviewObjectMessageCollection;
import com.appspot.elevated_surge_702.crud.model.MessagesUserMessage;
import com.appspot.elevated_surge_702.sync.Sync;
import com.example.ireviewr.model.Comment;
import com.example.ireviewr.model.DeletedEntry;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.GroupToReview;
import com.example.ireviewr.model.GroupToUser;
import com.example.ireviewr.model.Image;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.User;
import com.example.ireviewr.tools.ImageUtils;
import com.example.ireviewr.tools.ReviewerTools;
import com.example.ireviewr.tools.SyncUtils;
import com.google.api.client.util.DateTime;

public class SyncTask extends AsyncTask<Void, Void, Void>
{
	private Context context;
	
	private Crud crudApi;
	private Sync syncApi;
	
	private Date dateOfSynchronization;
	private Date dateLastSynchronized;
	private SimpleDateFormat dateFormat;
	private String dateLastSynchronizedString;
	
	private List<MessagesCommentMessage> serverComments;
	private List<MessagesGroupMessage> serverGroups;
	private List<MessagesImageMessage> serverImages;
	private List<MessagesUserMessage> serverUsers;
	private List<MessagesReviewMessage> serverReviews;
	private List<MessagesReviewObjectMessage> serverRevobs;
	private List<MessagesGroupToReviewMessage> serverGroupToReviews;
	private List<MessagesGroupToUserMessage> serverGroupToUsers;
	
	public SyncTask(Context context)
	{
		this.context = context;
	}
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onPreExecute()
	{
		crudApi = SyncUtils.buildCrudApi(); 
		syncApi = SyncUtils.buildSyncApi();
		dateOfSynchronization = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		dateLastSynchronized = new Date(1); // TODO from params
		dateLastSynchronizedString = dateFormat.format(dateLastSynchronized);
		
    	try
		{
    		download();
    		upload();
    		deleteFromServer();
    		persist();
    		// TODO set dateLastSynchronized in shared prefs
		}
		catch(IOException e)
		{
			Log.e("SYNC", e.getMessage());
		}
		return null;
	}
	
	/*
	 * DOWNLOAD DATA FROM SERVER
	 */
	
	private void download() throws IOException
	{
		serverComments = syncApi.comment().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverGroups = syncApi.group().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverImages = syncApi.image().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverReviews = syncApi.review().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverRevobs = syncApi.revobject().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverUsers = syncApi.user().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverGroupToReviews = syncApi.grouptoreview().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
		serverGroupToUsers = syncApi.grouptouser().syncdown().setDate(dateLastSynchronizedString).execute().getItems();
	}
	
	/*
	 * UPLOAD DATA TO SERVER
	 */
	
	private void upload() throws IOException
	{
		uploadGroups();
		uploadComments();
		uploadImages();
		uploadReviews();
		uploadReviewObjects();
		uploadGroupToReview();
		uploadGroupToUser();
	}
	
	private void uploadGroups() throws IOException
	{
		List<Group> models = Group.getNewerThan(Group.class, dateLastSynchronized);
		MessagesGroupMessageCollection messageCollection = new MessagesGroupMessageCollection();
		List<MessagesGroupMessage> messages = new ArrayList<MessagesGroupMessage>();
		
		for(Group model : models)
		{
			MessagesGroupMessage item = new MessagesGroupMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setName(model.getName());
			item.setOwner(model.getUserCreated().getModelId());
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.group().insert(messageCollection).execute();
	}
	
	private void uploadComments() throws IOException
	{
		List<Comment> models = Comment.getNewerThan(Comment.class, dateLastSynchronized);
		MessagesCommentMessageCollection messageCollection = new MessagesCommentMessageCollection();
		List<MessagesCommentMessage> messages = new ArrayList<MessagesCommentMessage>();
		
		for(Comment model : models)
		{
			MessagesCommentMessage item = new MessagesCommentMessage();
			
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setContent(model.getContent());
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.comment().insert(messageCollection).execute();
	}
	
	private void uploadImages() throws IOException
	{
		List<Image> models = Image.getNewerThan(Image.class, dateLastSynchronized);
		MessagesImageMessageCollection messageCollection = new MessagesImageMessageCollection();
		List<MessagesImageMessage> messages = new ArrayList<MessagesImageMessage>();
		
		for(Image model : models)
		{
			MessagesImageMessage item = new MessagesImageMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setName(model.getPath());
			item.setIsMain(model.isMain());
			
			Bitmap bitmap = ImageUtils.loadImageFromStorage(model.getPath());
			item.setImage(ReviewerTools.fromBitmapToString(bitmap));
			
			Review review = model.getReview();
			ReviewObject revob = model.getReviewObject();
			
			if(review == null) item.setReviewUuid(null);
			else item.setReviewUuid(review.getModelId());
			
			if(revob == null) item.setRevobjUuid(null);
			else item.setRevobjUuid(revob.getModelId());
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.image().insert(messageCollection).execute();
	}
	
	private void uploadReviews() throws IOException
	{
		List<Review> models = Review.getNewerThan(Review.class, dateLastSynchronized);
		MessagesReviewMessageCollection messageCollection = new MessagesReviewMessageCollection();
		List<MessagesReviewMessage> messages = new ArrayList<MessagesReviewMessage>();
		
		for(Review model : models)
		{
			MessagesReviewMessage item = new MessagesReviewMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setCreator(model.getUserCreated().getModelId());
			item.setDateCreated(new DateTime(model.getDateCreated()));
			item.setDescription(model.getDescription());
			item.setName(model.getName());
			item.setRating((double)model.getRating());
			item.setRevobjUuid(model.getReviewObject().getModelId());
			item.setTags(getTagNames(model.getTags()));
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.review().insert(messageCollection).execute();
	}
	
	private void uploadReviewObjects() throws IOException
	{
		List<ReviewObject> models = ReviewObject.getNewerThan(ReviewObject.class, dateLastSynchronized);
		MessagesReviewObjectMessageCollection messageCollection = new MessagesReviewObjectMessageCollection();
		List<MessagesReviewObjectMessage> messages = new ArrayList<MessagesReviewObjectMessage>();
		
		for(ReviewObject model : models)
		{
			MessagesReviewObjectMessage item = new MessagesReviewObjectMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setCreator(model.getUserCreated().getModelId());
			item.setDescription(model.getDescription());
			item.setLat(model.getLocationLat());
			item.setLon(model.getLocationLong());
			item.setName(model.getName());
			item.setTags(getTagNames(model.getTags()));
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.revobject().insert(messageCollection).execute();
	}
	
	private void uploadGroupToReview() throws IOException
	{
		List<GroupToReview> models = GroupToReview.getNewerThan(GroupToReview.class, dateLastSynchronized);
		MessagesGroupToReviewMessageCollection messageCollection = new MessagesGroupToReviewMessageCollection();
		List<MessagesGroupToReviewMessage> messages = new ArrayList<MessagesGroupToReviewMessage>();
		
		for(GroupToReview model : models)
		{
			MessagesGroupToReviewMessage item = new MessagesGroupToReviewMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setGroup(model.getGroup().getModelId());
			item.setReview(model.getReview().getModelId());
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.grouptoreview().insert(messageCollection).execute();
	}
	
	private void uploadGroupToUser() throws IOException
	{
		List<GroupToUser> models = GroupToUser.getNewerThan(GroupToUser.class, dateLastSynchronized);
		MessagesGroupToUserMessageCollection messageCollection = new MessagesGroupToUserMessageCollection();
		List<MessagesGroupToUserMessage> messages = new ArrayList<MessagesGroupToUserMessage>();
		
		for(GroupToUser model : models)
		{
			MessagesGroupToUserMessage item = new MessagesGroupToUserMessage();
			
			item.setDeleted(false);
			item.setUuid(model.getModelId());
			item.setLastModified(new DateTime(dateOfSynchronization));
			item.setGroup(model.getGroup().getModelId());
			item.setUser(model.getUser().getModelId());
			
			messages.add(item);
		}
		messageCollection.setItems(messages);
		
		crudApi.grouptouser().insert(messageCollection).execute();
	}
	
	private List<String> getTagNames(List<Tag> tags)
	{
		List<String> tagNames = new ArrayList<String>();
		for(Tag tag : tags)
		{
			tagNames.add(tag.getName());
		}
		return tagNames;
	}
	
	/*
	 * DELETE FROM SERVER
	 */
	
	private void deleteFromServer() throws IOException
	{
		DeleteRequest deleteGroups = new DeleteRequest();
		DeleteRequest deleteImages = new DeleteRequest();
		DeleteRequest deleteUsers = new DeleteRequest();
		DeleteRequest deleteReviews = new DeleteRequest();
		DeleteRequest deleteRevobs = new DeleteRequest();
		DeleteRequest deleteGroupToReviews = new DeleteRequest();
		DeleteRequest deleteGroupToUsers = new DeleteRequest();
		
		for(DeletedEntry entry : DeletedEntry.getNewerThan(dateLastSynchronized))
		{
			if(Group.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteGroups.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(Image.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteImages.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(User.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteUsers.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(Review.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteReviews.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(ReviewObject.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteRevobs.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(GroupToReview.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteGroupToReviews.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			else if(GroupToUser.class.getSimpleName().equals(entry.getTableName()))
			{
				deleteGroupToUsers.add(entry.getDeletedModelId(), dateOfSynchronization);
			}
			
			entry.delete();
		}
		
		crudApi.group().delete().setItemsUuid(deleteGroups.getModelIds())
			.setItemsLastModified(deleteGroups.getDates()).execute();
		crudApi.image().delete().setItemsUuid(deleteImages.getModelIds())
			.setItemsLastModified(deleteImages.getDates()).execute();
		crudApi.user().delete().setItemsUuid(deleteUsers.getModelIds())
			.setItemsLastModified(deleteUsers.getDates()).execute();
		crudApi.review().delete().setItemsUuid(deleteReviews.getModelIds())
			.setItemsLastModified(deleteReviews.getDates()).execute();
		crudApi.revobject().delete().setItemsUuid(deleteRevobs.getModelIds())
			.setItemsLastModified(deleteRevobs.getDates()).execute();
		crudApi.grouptoreview().delete().setItemsUuid(deleteGroupToReviews.getModelIds())
			.setItemsLastModified(deleteGroupToReviews.getDates()).execute();
		crudApi.grouptouser().delete().setItemsUuid(deleteGroupToUsers.getModelIds())
			.setItemsLastModified(deleteGroupToUsers.getDates()).execute();
	}
	
	class DeleteRequest
	{
		List<String> modelIds;
		List<String> dates;
		
		public DeleteRequest()
		{
			modelIds = new ArrayList<String>();
			dates = new ArrayList<String>();
		}
		
		public void add(String modelId, Date date)
		{
			modelIds.add(modelId);
			dates.add(dateFormat.format(date));
		}

		public List<String> getModelIds()
		{
			return modelIds;
		}

		public List<String> getDates()
		{
			return dates;
		}
	}
	
	/*
	 * PERSIST CHANGES TO DATABASE
	 */
	
	private void persist()
	{
		persistComments();
		persistGroups();
		persistImages();
		persistReviews();
		persistReviewObjects();
		persistUsers();
		persistGroupToReviews();
		persistGroupToUsers();
	}
	
	private void persistComments()
	{
		if(serverComments == null) return;
		
		for(MessagesCommentMessage msg : serverComments)
		{
			Comment model = Comment.getByModelId(Comment.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				model.setContent(msg.getContent());
				model.setDateModified(convertToDate(msg.getLastModified()));
				model.save();
			}
			else // ako ne postoji kreiraj
			{
				User user = User.getByModelId(User.class, msg.getCreator());
				Review review = Review.getByModelId(Review.class, msg.getReviewUuid());
				new Comment(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getContent(), user, review).save();
			}
		}
	}
	
	private void persistGroups()
	{
		if(serverGroups == null) return;
		
		for(MessagesGroupMessage msg : serverGroups)
		{
			Group model = Group.getByModelId(Group.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					model.setName(msg.getName());
					model.setDateModified(convertToDate(msg.getLastModified()));
					model.save();
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					User user = User.getByModelId(User.class, msg.getOwner());
					new Group(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getName(), user).save();
				}
			}
		}
	}
	
	private void persistImages()
	{
		if(serverImages == null) return;
		
		for(MessagesImageMessage msg : serverImages)
		{
			Image model = Image.getByModelId(Image.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					model.setMain(msg.getIsMain());
					model.save();
				}
				if(msg.getDeleted())
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					Bitmap bitmap = ReviewerTools.fromStringToBitmap(msg.getImage());
					ImageUtils.saveTo(bitmap, msg.getName(), context);
					
					if(msg.getReviewUuid() != null)
					{
						Review review = Review.getByModelId(Review.class, msg.getReviewUuid());
						new Image(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getName(), msg.getIsMain(), review)
							.save();
					}
					else if(msg.getRevobjUuid() != null)
					{
						ReviewObject revob = ReviewObject.getByModelId(ReviewObject.class, msg.getRevobjUuid());
						new Image(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getName(), msg.getIsMain(), revob)
							.save();
					}
					else
					{
						Log.e("SYNC", "Image ne pripada nikome.");
					}
				}
			}
		}
	}
	
	private void persistReviews()
	{
		if(serverReviews == null) return;
		
		for(MessagesReviewMessage msg : serverReviews)
		{
			Review model = Review.getByModelId(Review.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					model.setName(msg.getName());
					model.setDescription(msg.getDescription());
					model.setRating(msg.getRating().floatValue());
					model.setDateModified(convertToDate(msg.getLastModified()));
					model.save();
					
					addTagsToReview(msg, model);
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					User user = User.getByModelId(User.class, msg.getCreator());
					ReviewObject revob  = ReviewObject.getByModelId(ReviewObject.class, msg.getRevobjUuid());
					model = new Review(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getName(), msg.getDescription(), 
							msg.getRating().floatValue(), convertToDate(msg.getDateCreated()), user, revob);
					model.save();
					
					addTagsToReview(msg, model);
				}
			}
		}
	}

	private void addTagsToReview(MessagesReviewMessage msg, Review model)
	{
		if(msg.getTags() == null) return;
		
		List<Tag> existingTagModels = model.getTags();
		List<String> existingTagNames = new ArrayList<String>();
		
		// obrisi sve tagove koji su izbaceni
		for(Tag tag : existingTagModels)
		{
			if(!msg.getTags().contains(tag.getName())) // sve koji nisu vise u tags listi
			{
				model.removeTag(tag); // obrisi
			}
			else
			{
				existingTagNames.add(tag.getName()); // dodaj u listu postojecih imena za kasnije
			}
		}
		
		// dodaj sve tagove koji su dodati
		for(String tagName : msg.getTags())
		{
			if(!existingTagNames.contains(tagName)) // ako nije vec dodat
			{
				Tag tag = Tag.getByName(tagName);
				if(tag == null) // ako vec ne postji tag
				{
					tag = new Tag(tagName);
					tag.saveOrThrow(); // dodaj nov
				}
				
				model.addTag(tag); // dodaj tag u newReviewObject
			}
		}
	}
	
	private void persistReviewObjects()
	{
		if(serverRevobs == null) return;
		
		for(MessagesReviewObjectMessage msg : serverRevobs)
		{
			ReviewObject model = ReviewObject.getByModelId(ReviewObject.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					model.setName(msg.getName());
					model.setDescription(msg.getDescription());
					model.setLocation(msg.getLon(), msg.getLat());
					model.setDateModified(convertToDate(msg.getLastModified()));
					model.save();
					
					addTagsToReviewObject(msg, model);
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					User user = User.getByModelId(User.class, msg.getCreator());
					model = new ReviewObject(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getName(), msg.getDescription(), 
							msg.getLon(), msg.getLat(), user);
					model.save();
					
					addTagsToReviewObject(msg, model);
				}
			}
		}
	}
	
	private void addTagsToReviewObject(MessagesReviewObjectMessage msg, ReviewObject model)
	{
		if(msg.getTags() == null) return;
		
		List<Tag> existingTagModels = model.getTags();
		List<String> existingTagNames = new ArrayList<String>();
		
		// obrisi sve tagove koji su izbaceni
		for(Tag tag : existingTagModels)
		{
			if(!msg.getTags().contains(tag.getName())) // sve koji nisu vise u tags listi
			{
				model.removeTag(tag); // obrisi
			}
			else
			{
				existingTagNames.add(tag.getName()); // dodaj u listu postojecih imena za kasnije
			}
		}
		
		// dodaj sve tagove koji su dodati
		for(String tagName : msg.getTags())
		{
			if(!existingTagNames.contains(tagName)) // ako nije vec dodat
			{
				Tag tag = Tag.getByName(tagName);
				if(tag == null) // ako vec ne postji tag
				{
					tag = new Tag(tagName);
					tag.saveOrThrow(); // dodaj nov
				}
				
				model.addTag(tag); // dodaj tag u newReviewObject
			}
		}
	}
	
	private void persistUsers()
	{
		if(serverUsers == null) return;
		
		for(MessagesUserMessage msg : serverUsers)
		{
			User model = User.getByModelId(User.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					model.setName(msg.getUserName());
					model.setEmail(msg.getEmail());
					model.setDateModified(convertToDate(msg.getLastModified()));
					model.save();
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					new User(msg.getUuid(), convertToDate(msg.getLastModified()), msg.getUserName(), msg.getEmail()).save();
				}
			}
		}
	}
	
	private void persistGroupToReviews()
	{
		if(serverGroupToReviews == null) return;
		
		for(MessagesGroupToReviewMessage msg : serverGroupToReviews)
		{
			GroupToReview model = GroupToReview.getByModelId(GroupToReview.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					Log.e("SYNC", "GroupToReview nemoze da se edituje.");
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					Group group = Group.getByModelId(Group.class, msg.getGroup());
					Review review = Review.getByModelId(Review.class, msg.getReview());
					new GroupToReview(msg.getUuid(), convertToDate(msg.getLastModified()), review, group).save();
				}
			}
		}
	}
	
	private void persistGroupToUsers()
	{
		if(serverGroupToUsers == null) return;
		
		for(MessagesGroupToUserMessage msg : serverGroupToUsers)
		{
			GroupToUser model = GroupToUser.getByModelId(GroupToUser.class, msg.getUuid());
			
			if(model != null) // ako vec postoji updateuj
			{
				if(!msg.getDeleted())
				{
					Log.e("SYNC", "GroupToUser nemoze da se edituje.");
				}
				else
				{
					model.delete();
				}
			}
			else // ako ne postoji kreiraj
			{
				if(!msg.getDeleted())
				{
					Group group = Group.getByModelId(Group.class, msg.getGroup());
					User user = User.getByModelId(User.class, msg.getUser());
					new GroupToUser(msg.getUuid(), convertToDate(msg.getLastModified()), user, group).save();
				}
			}
		}
	}
	
	private Date convertToDate(DateTime dateTime)
	{
		return new Date(dateTime.getValue());
	}
}
