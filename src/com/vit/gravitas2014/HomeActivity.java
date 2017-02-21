package com.vit.gravitas2014;




import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener
{

	SharedPreferences preferencesRef,preferences;
	String sharedPrefKey = "recieved_data";
	String state;
	ImageView ivEvents, ivAbout, ivRegistration, ivContact, ivTopBanner;
	Typeface font;
	TextView tvUpdateNews;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		 if (checkPlayServices()) {
		      gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
		            regid = getRegistrationId(getApplicationContext());
		            if(!regid.isEmpty()){
		             //button.setEnabled(false);
		            }else{
		             //button.setEnabled(true);
		            }
		  }
		 if (checkPlayServices()) {
		        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
		              regid = getRegistrationId(getApplicationContext());
		              
		              if (regid.isEmpty()) {
		              // button.setEnabled(false);
		                  new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();
		              }else{
		               //Toast.makeText(getApplicationContext(), "Device already Registered", Toast.LENGTH_SHORT).show();
		              }
		       } else {
		              Log.i(TAG, "No valid Google Play Services APK found.");
		       }



		setContentView(R.layout.activity_home);

		
		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");
		
		tvUpdateNews=(TextView)findViewById(R.id.tvUpdateNews);
		tvUpdateNews.setTypeface(font);
		
		try
		{
			int titleId = getResources().getIdentifier("action_bar_title", "id",
					"android");
			TextView yourTextView = (TextView) findViewById(titleId);
			yourTextView.setTypeface(font);
			
			getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		}
		catch(NullPointerException e )
		{
			e.printStackTrace();
		}
		preferencesRef=getSharedPreferences("refresh",0);
		SharedPreferences.Editor editor = preferencesRef.edit();
		editor.putString("status", "On destroy");
		editor.commit();

		ivEvents = (ImageView)findViewById(R.id.ivEvents);
		ivEvents.setOnClickListener(this);

		ivAbout = (ImageView)findViewById(R.id.ivAboutGrav);
		ivAbout.setOnClickListener(this);

		ivRegistration = (ImageView)findViewById(R.id.ivRegistration);
		ivRegistration.setOnClickListener(this);
		
		
		ivContact = (ImageView)findViewById(R.id.ivContact);
		ivContact.setOnClickListener(this);
		
		


	}



	@Override
	public void onClick(View view)
	{

		Intent intent;
		switch(view.getId())
		{
		case R.id.ivEvents:
			intent = new Intent(HomeActivity.this,Fetching.class);
			startActivity(intent);
			this.finish();
			break;

		case R.id.ivAboutGrav:
			intent = new Intent(HomeActivity.this,About_Grav.class);
			startActivity(intent);
			
			break;

		case R.id.ivRegistration:
			intent = new Intent(HomeActivity.this,Registration.class);
			startActivity(intent);
			
			break;



		case R.id.ivContact:
			intent = new Intent(HomeActivity.this,Contact.class);
			startActivity(intent);
			
			break;
			
		
			
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences(sharedPrefKey, 0);
		 state = preferences.getString("status", "Time,Date and Venue will be updated soon");
		tvUpdateNews.setText(state);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		this.finish();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.privacypolicy, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Intent intent = new Intent(this, PrivacyPolicy.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

//GCMMMMMMMMMMMMMMMMMm
	
	
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	 public static final String EXTRA_MESSAGE = "message";
	 public static final String PROPERTY_REG_ID = "registration_id";
	 private static final String PROPERTY_APP_VERSION = "appVersion";
	 private static final String TAG = "GCMRelated";
	 GoogleCloudMessaging gcm;
	 AtomicInteger msgId = new AtomicInteger();
	 String regid;

	 private boolean checkPlayServices() {
	     int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	     if (resultCode != ConnectionResult.SUCCESS) {
	         if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	             GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                     PLAY_SERVICES_RESOLUTION_REQUEST).show();
	         } else {
	             Log.i(TAG, "This device is not supported.");
	             finish();
	         }
	         return false;
	     }
	     return true;
	 }
	 private String getRegistrationId(Context context) {
	     final SharedPreferences prefs = getGCMPreferences(context);
	     String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	     if (registrationId.isEmpty()) {
	         Log.i(TAG, "Registration not found.");
	         return "";
	     }
	     // Check if app was updated; if so, it must clear the registration ID
	     // since the existing regID is not guaranteed to work with the new
	     // app version.
	     int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	     int currentVersion = getAppVersion(getApplicationContext());
	     if (registeredVersion != currentVersion) {
	         Log.i(TAG, "App version changed.");
	         return "";
	     }
	     return registrationId;
	 }
	 
	 private SharedPreferences getGCMPreferences(Context context) {
		  // This sample app persists the registration ID in shared preferences, but
		     // how you store the regID in your app is up to you.
		     return getSharedPreferences(MainActivity.class.getSimpleName(),
		             Context.MODE_PRIVATE);
		 }
		 
		  private static int getAppVersion(Context context) {
		     try {
		         PackageInfo packageInfo = context.getPackageManager()
		                 .getPackageInfo(context.getPackageName(), 0);
		         return packageInfo.versionCode;
		     } catch (NameNotFoundException e) {
		         // should never happen
		         throw new RuntimeException("Could not get package name: " + e);
		     }
		 }
		
}
