package com.example.ireviewr.activities;

import java.util.Date;
import java.util.List;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.ireviewr.MainActivity;
import com.example.ireviewr.R;
import com.example.ireviewr.model.Group;
import com.example.ireviewr.model.Review;
import com.example.ireviewr.model.ReviewObject;
import com.example.ireviewr.model.Tag;
import com.example.ireviewr.model.User;
import com.example.ireviewr.sync.tasks.RegisterTask;
import com.example.ireviewr.sync.tasks.SyncTask;
import com.example.ireviewr.tools.CurrentUser;
import com.example.ireviewr.tools.SyncUtils;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

public class SplashScreenActivity extends Activity
{
	private static final int REQUEST_CODE_EMAIL = 1;
	private static int SPLASH_TIME_OUT = 3000; // splash ce biti vidljiv minimum SPLASH_TIME_OUT milisekundi
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		// uradi inicijalizaciju u pozadinksom threadu
		new InitTask().execute();
	}
	
	private class InitTask extends AsyncTask<Void, Void, Void>
	{
		private long startTime;
		
		@Override
        protected void onPreExecute()
        {
			startTime = System.currentTimeMillis();
        }
		
		@Override
		protected Void doInBackground(Void... arg0)
		{
			initTestData();
			
			if(SyncUtils.getLastSyncronizationDate(SplashScreenActivity.this) == null) // ako jos nije sinhronizovan
			{
				new SyncTask(SplashScreenActivity.this)
				{
					protected void onPostExecute(Void result) // kad se sinhronizuje
					{
						continueLogin(); // nastavi login
					};
				}
				.execute(); // sinhronizuj sad i sacekaj da zavrsi
			}
			else // inace
			{
				continueLogin(); // samo nastavi login
			}
			
			return null;
		}
		
		private void continueLogin()
		{
			// sacekaj tako da splash bude vidljiv minimum SPLASH_TIME_OUT milisekundi
			long timeLeft = SPLASH_TIME_OUT - (System.currentTimeMillis() - startTime);
			if(timeLeft < 0) timeLeft = 0;
			SystemClock.sleep(timeLeft); // TODO odkomentarisati ovo, zakomentarisano zbog testiranja
			
			// uloguj se
			login(); // TODO odkomentarisati ovaj, obrisati ovaj ispod
			//User test_user = new Select().from(User.class).where("name = ?", "test_user").executeSingle();
			//login(test_user); // dummy login za testiranje
		}
	}
	
    /**
     * Proveri da li je logovan user, ako nije registruj ga.
     */
	private void login()
	{
		if(!CurrentUser.exists(this))
		{
			register(); // ako nije logovan registruj ga
		}
		else
		{
			startMainActivity();
		}
	}

	/**
	 * Zatrazi od korisnika da odabere nalog sa validnim emailom.
	 */
	private void register()
	{
		try
		{
			Intent intent = AccountPicker.newChooseAccountIntent(null, null,
					new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, false, null, null, null, null);
			startActivityForResult(intent, REQUEST_CODE_EMAIL);
	    }
		catch (ActivityNotFoundException e)
		{
			Toast.makeText(this, "iReviewr cannot aquire account information. Closing now.", Toast.LENGTH_LONG).show();
			finish();
	    }
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE_EMAIL)
        {
        	if(resultCode == RESULT_OK) // korisnik je odabrao nalog sa emailom
        	{
            	String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            	if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            	{
            		User existingUser = User.getByEmail(email);
            		if(existingUser != null) // ako vec postoji user sa ovim emailom
            		{
            			login(existingUser); // uloguj ga
            		}
            		else // inace
            		{
            			register(email); // registruj sa emailom
            		}
            	}
            	else
            	{
            		Toast.makeText(this, "Selected account doesn't have a valid email, please select another one.", Toast.LENGTH_LONG).show();
            		register(); // probaj ovo opet
            	}
        	}
        	else
        	{
        		Toast.makeText(this, "Please select an account with a valid email address.", Toast.LENGTH_LONG).show();
        		register(); // probaj ovo opet
        	}
        }
    }
	
    /**
     * Znamo email, pitaj korisnika za jedinstveni username.
     * @param currentUserEmail
     */
	private void register(String currentUserEmail)
	{
		createRegisterDialog(currentUserEmail); // pitaj za username
	}
	
	/**
	 * Pitaj korisnika za jedinstveni username.
	 * @param currentUserEmail
	 */
	@SuppressLint("InflateParams")
	private void createRegisterDialog(String currentUserEmail)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		final View promptView = layoutInflater.inflate(R.layout.register_dialog, null);
		new AlertDialog.Builder(this)
			.setView(promptView)
			.setCancelable(false)
			.setPositiveButton(R.string.register, new OnClickListener()
			{
				private String email;
				public OnClickListener setEmail(String email)
				{
					this.email = email;
					return this;
				}
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//get data
					EditText editText = (EditText) promptView.findViewById(R.id.edittext);
					String newUserName = editText.getText().toString();
					// TODO validirati username
					register(email, newUserName); // registruj korisnika sa email i username
				}
			}
			.setEmail(currentUserEmail))
			.show();
	}

	/**
	 * Znamo i email i username, pokusaj da dodas korisnika u bazu.
	 * @param currentUserEmail
	 * @param newUserName
	 */
	private void register(final String currentUserEmail, String newUserName)
	{
		final User newUser = new User(newUserName, currentUserEmail);
		
		try
		{
			newUser.saveOrThrow(); // snimi novog usera u bazu
			new RegisterTask() // sinhronizuj sa serverom
			{
				protected void onPostExecute(String result) 
				{
					if(OK.equals(result))
					{
						login(newUser); // dodavanje je uspelo, uloguj novog korisnika
					}
					else if(NAME_EXISTS.equals(result))
					{
						Toast.makeText(SplashScreenActivity.this, "Username already exists.", Toast.LENGTH_LONG).show();
						register(currentUserEmail); // probaj opet da se registrujes
					}
					else if(EMAIL_EXISTS.equals(result))
					{
						Toast.makeText(SplashScreenActivity.this, "Email already exists. Try restarting the application or choosing another account.", Toast.LENGTH_LONG).show();
						register(); // probaj opet da pitas za mail
					}
					else if(NO_CONNECTION.equals(result))
					{
						Toast.makeText(SplashScreenActivity.this, "Could not register at this moment, try again later.", Toast.LENGTH_LONG).show();
						register(currentUserEmail); // probaj opet da se registrujes
					}
					else
					{
						Log.e("SYNC", "Internal error, unknown return code from RegisterTask.");
					}
					
					if(!OK.equals(result)) // ako nije uspelo obrisi usera
					{
						newUser.delete();
					}
				}
			}
			.execute(newUser.getName(), newUser.getEmail(), newUser.getModelId());
		}
		catch(SQLiteConstraintException ex) // ako nije prosla registracija
		{
			newUser.delete();
			Toast.makeText(this, "Username already exists.", Toast.LENGTH_LONG).show();
			register(currentUserEmail); // probaj opet da se registrujes
		}
		catch(Exception ex) // ako nije prosla registracija
		{
			newUser.delete();
			Toast.makeText(this, "Could not register at this moment, try again later.", Toast.LENGTH_LONG).show();
			register(currentUserEmail); // probaj opet da se registrujes
		}
	}
	
	/**
	 * Uloguj korisnika user, smesti podatke u shared preferences.
	 * @param user
	 */
	private void login(User user)
	{
		CurrentUser.login(user, this);
		startMainActivity();
	}
	
	private void startMainActivity()
	{
		startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
		finish(); // da nebi mogao da ode back na splash
	}

	private void initTestData()
	{
		User testUser = new User("test_user", "test@user.com");
		testUser.save();
		
		User testUser2 = new User("test_user2", "test@user2.com");
		testUser2.save();
		
		Log.d("DATABASE", "starting save test");
		Group group = new Group("mygroup", testUser);
		group.save();
		group = new Group("mygroup2", testUser2);
		group.save();
		group = new Group("mygroup3", testUser);
		group.save();
		Log.d("DATABASE", "saved");
		
		Log.d("DATABASE", "starting query test");
		List<Group> existingGroups = (new Select())
		.from(Group.class)
		.execute();
		for(Group existingGroup : existingGroups)
		{
			Log.d("DATABASE", "got group with name: "+existingGroup.getName());
		}
		/////////////////////////////////////
		
		ReviewObject ro = new ReviewObject("ro1", "dro1", 1, 1, testUser);
        ro.save();
        
        Tag tag1 = new Tag("tag1");
        tag1.save();
        Tag tag2 = new Tag("tag2");
        tag2.save();
        
        ro.addTag(tag1);
        ro.addTag(tag2);
        
        List<ReviewObject> existingRevObjects = (new Select())
                .from(ReviewObject.class)
                .execute();
        for(ReviewObject existingReviewObject : existingRevObjects)
        {
        	Log.d("DATABASE", "got ReviewObject with name: "+existingReviewObject.getName());
        	for(Tag existingTag : existingReviewObject.getTags())
        	{
        		Log.d("DATABASE", "got tag with name: "+existingTag.getName());
        	}
        }
        
        ReviewObject ro2 = new ReviewObject("ro2", "dro2asdsdfas dafsfs dfasds fsdasfsgvdf sfvdsvsd", 2, 2, testUser);
        ro2.save();
        ro2.addTag(tag1);
        
        ReviewObject ro3 = new ReviewObject("ro3", "dro3", 2, 2, testUser);
        ro3.save();
	        
	    //////////////////////////////////////
		
		Review rev1 = new Review("test_id", new Date(), "review1", "desc1sdfsdf", 2, new Date(), testUser, ro);
		rev1.save();
		
		Review rev2 = new Review("test_id2", new Date(), "review2", "desc1sdfsdf2", 3, new Date(), testUser, ro);
		rev2.save();
		
		Review rev3 = new Review("test_id3", new Date(), "review3", "desc1sdfsdf3", 2, new Date(), testUser, ro);
		rev3.save();
		
		Review rev4 = new Review("test_id4", new Date(), "review4", "desc1sdfsdf4", 4, new Date(), testUser2, ro);
		rev4.save();
		
		group.addReview(rev1);
		group.addReview(rev2);
		group.addUser(testUser2);
	}
}
