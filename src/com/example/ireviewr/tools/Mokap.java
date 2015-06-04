package com.example.ireviewr.tools;

import java.util.ArrayList;
import java.util.Date;

import com.example.ireviewr.R;
import com.example.ireviewr.model.Comment;
import com.example.ireviewr.model.GaleryItem;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.NavItem;
import com.example.ireviewr.model.ReviewItem;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.UserItem;

public class Mokap {

	
	/*MOKAP*/
    public static ArrayList<NavItem> getList(){
    	ArrayList<NavItem> items = new ArrayList<NavItem>();
    	
    	items.add(new NavItem("Home", "Meetup review objects", R.drawable.ic_action_map));
    	items.add(new NavItem("Groups", "Meetup groups", R.drawable.ic_action_select_all));
    	items.add(new NavItem("Reviews", "Meetup destination", R.drawable.ic_action_labels));
    	items.add(new NavItem("Preferences", "Change your preferences", R.drawable.ic_action_settings));
    	items.add(new NavItem("About", "Get to know about us", R.drawable.ic_action_about));
    	
    	items.add(new NavItem("Home", "Meetup review objects", R.drawable.ic_action_map));
    	items.add(new NavItem("Groups", "Meetup groups", R.drawable.ic_action_select_all));
    	items.add(new NavItem("Reviews", "Meetup destination", R.drawable.ic_action_labels));
    	items.add(new NavItem("Preferences", "Change your preferences", R.drawable.ic_action_settings));
    	items.add(new NavItem("About", "Get to know about us", R.drawable.ic_action_about));
    	
    	items.add(new NavItem("Home", "Meetup review objects", R.drawable.ic_action_map));
    	items.add(new NavItem("Groups", "Meetup groups", R.drawable.ic_action_select_all));
    	items.add(new NavItem("Reviews", "Meetup destination", R.drawable.ic_action_labels));
    	items.add(new NavItem("Preferences", "Change your preferences", R.drawable.ic_action_settings));
    	items.add(new NavItem("About", "Get to know about us", R.drawable.ic_action_about));
    	
    	return items;
    }
    
    public static ArrayList<Tag> getTags() {
    	
    	ArrayList<Tag> tags = new ArrayList<Tag>();
		tags.add(new Tag("Name1", new Date()));
		tags.add(new Tag("Name2", new Date()));
		tags.add(new Tag("Name3", new Date()));
		tags.add(new Tag("Name4", new Date()));
		tags.add(new Tag("Name5", new Date()));
		tags.add(new Tag("Name6", new Date()));
		tags.add(new Tag("Name1", new Date()));
		tags.add(new Tag("Name2", new Date()));
		tags.add(new Tag("Name3", new Date()));
		tags.add(new Tag("Name4", new Date()));
		tags.add(new Tag("Name5", new Date()));
		tags.add(new Tag("Name6", new Date()));
		
		return tags;
	}
    
    public static ArrayList<Comment> getCommentsList(){
		ArrayList<Comment> items = new ArrayList<Comment>();
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		items.add(new Comment("bla bla truc", new Date(), "Ja"));
		items.add(new Comment("bla bla truc", new Date(), "On"));
		items.add(new Comment("bla bla truc", new Date(), "Ona"));
		
		return items;
	}
    
	public static ArrayList<GaleryItem> gatGaleryList(){
		ArrayList<GaleryItem> items = new ArrayList<GaleryItem>();
		items.add(new GaleryItem("Test1", R.drawable.ic_action_edit));
		items.add(new GaleryItem("Test2", R.drawable.ic_action_labels));
		items.add(new GaleryItem("Test3", R.drawable.ic_action_map));
		items.add(new GaleryItem("Test4", R.drawable.ic_action_picture));
		items.add(new GaleryItem("Test5", R.drawable.ic_action_camera));
		
		return items;
	}
	
	public static ArrayList<UserItem> getUserList(){
		ArrayList<UserItem> items = new ArrayList<UserItem>();
		
		items.add(new UserItem("User1","mail@mail.com",new Date()));
		items.add(new UserItem("User2","mail@mail.com",new Date()));
		items.add(new UserItem("User3","mail@mail.com",new Date()));
		items.add(new UserItem("User4","mail@mail.com",new Date()));
		items.add(new UserItem("User5","mail@mail.com",new Date()));
		items.add(new UserItem("User6","mail@mail.com",new Date()));
		items.add(new UserItem("User7","mail@mail.com",new Date()));
		
		return items;
	}
	
	public static ArrayList<ReviewItem> getReviewList(){
		ArrayList<ReviewItem> items = new ArrayList<ReviewItem>();
		
		items.add(new ReviewItem("Name1", "Bla1", 2, getUserList().get(0), 
				getCommentsList(), new Date(), new Date(), 
				gatGaleryList(), getTags()));
		items.add(new ReviewItem("Name2", "Bla2", 2, getUserList().get(0), 
				getCommentsList(), new Date(), new Date(), 
				gatGaleryList(), getTags()));
		items.add(new ReviewItem("Name3", "Bla3", 2, getUserList().get(0), 
				getCommentsList(), new Date(), new Date(), 
				gatGaleryList(), getTags()));
		items.add(new ReviewItem("Name4", "Bla4", 2, getUserList().get(0), 
				getCommentsList(), new Date(), new Date(), 
				gatGaleryList(), getTags()));
		
		return items;
	}
	
	public static ArrayList<Group> getGroupList(){
		ArrayList<Group> items = new ArrayList<Group>();
		
		items.add(new Group("Group1", new Date(), getReviewList(), getUserList()));
		items.add(new Group("Group2", new Date(), getReviewList(), getUserList()));
		items.add(new Group("Group3", new Date(), getReviewList(), getUserList()));
		items.add(new Group("Group4", new Date(), getReviewList(), getUserList()));
		items.add(new Group("Group5", new Date(), getReviewList(), getUserList()));
		
		return items;
	}
	
	public static Group getSingleGroup(){
		return getGroupList().get(0);
	}
    
}
