/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ireviewr;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ireviewr.adapters.DrawerListAdapter;
import com.example.ireviewr.fragments.AboutFragment;
import com.example.ireviewr.fragments.MyMapFragment;
import com.example.ireviewr.fragments.PreferencesFragment;
import com.example.ireviewr.fragments.groups.GroupsListFragment;
import com.example.ireviewr.fragments.reviews.ReviewsFragmentList;
import com.example.ireviewr.model.NavItem;
import com.example.ireviewr.sync.SyncReceiver;
import com.example.ireviewr.sync.auto.SyncService;
import com.example.ireviewr.tools.Mokap;

public class MainActivity extends FragmentActivity{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    
    //Sync stuff
    private PendingIntent pendingIntent;
	private AlarmManager manager;
	
	private SyncReceiver sync;
	public static String SYNC_DATA = "SYNC_DATA";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prepareMenu(mNavItems);
        
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        
        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItemFromDrawer(0);
        }
        
        setUpReceiver();
        
    }
    
    private void setUpReceiver(){
    	sync = new SyncReceiver();
    	
    	// Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, SyncService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	//Za slucaj da referenca nije postavljena da se izbegne problem sa androidom!
    	if (manager == null) {
    		setUpReceiver();
		}
    	
    	int interval = 10000; // 10 seconds
    	manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    	
    	registerReceiver(sync, new IntentFilter(SYNC_DATA));
    }
    
    private void prepareMenu(ArrayList<NavItem> mNavItems ){
    	mNavItems.add(new NavItem("Home", "Meetup review objects", R.drawable.ic_action_map));
        mNavItems.add(new NavItem("Groups", "Meetup groups", R.drawable.ic_action_group));
        mNavItems.add(new NavItem("Places", "Meetup destination", R.drawable.ic_action_place));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.ic_action_settings));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.ic_action_about));
        mNavItems.add(new NavItem("Sync data", "Sync data from repo", R.drawable.ic_action_refresh));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// If the nav drawer is open, hide action items related to the content view
    	boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerPane);
    	menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
    	return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        /*switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	selectItemFromDrawer(position);
        }
    }
    
    
    private void selectItemFromDrawer(int position) {
    	FragmentManager fragmentManager = getSupportFragmentManager();
    	if(position == 0){
    		fragmentManager.beginTransaction().replace(R.id.mainContent, MyMapFragment.newInstance()).commit();
    	}else if(position == 1){
    		fragmentManager.beginTransaction().
        	replace(R.id.mainContent, new GroupsListFragment()).addToBackStack(null).commit();
        }else if(position == 2){
        	fragmentManager.beginTransaction().
        	replace(R.id.mainContent, ReviewsFragmentList.newInstance(Mokap.getList())).addToBackStack(null).commit();
        }else if(position == 3){
        	fragmentManager.beginTransaction().
        	replace(R.id.mainContent, new PreferencesFragment()).addToBackStack(null).commit();
        }else if(position == 4){	
        	fragmentManager.beginTransaction().
        	replace(R.id.mainContent, new AboutFragment()).addToBackStack(null).commit();
        }else if(position == 5){
        	Toast.makeText(MainActivity.this, "Call sync", Toast.LENGTH_LONG).show();
        }else{
        	Toast.makeText(MainActivity.this, "Nesto van opsega!", Toast.LENGTH_LONG).show();
        }
        
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).getmTitle());
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void getProfile(View view){
    	Toast.makeText(this, "User", Toast.LENGTH_LONG).show();
    }
    
    public static final class LOADER_ID // TODO prebaciti u res/values/ids.xml kao id mozda
    {
    	public static final int GROUP 	= 0;
    	public static final int TAG		= 1;
    	public static final int REVIEW	= 2;
		public static final int	COMMENT	= 3;
		public static final int	USER	= 4;
		public static final int	IMAGE	= 5;
    };
    
    @Override
    protected void onPause() {
    	if (manager != null) {
			manager.cancel(pendingIntent);
	        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
		}

    	if(sync != null){
    		unregisterReceiver(sync);
    	}
    	
    	super.onPause();
    	
    }
}
