package com.vit.gravitas2014;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Fetching extends Activity {
static DatabaseHandler db;
HttpClient client;
JSONObject json;
final static String URL = "http://vitgravitas.com/app/events.php";
Events[] day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12;
static boolean fail = false;
	SharedPreferences preferences;
	String sharedPrefKey = "database";
	String sharedPrefKeyRefresh="refresh";
	SharedPreferences preferencesRef;
	static Typeface font;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.loading);
	TextView yourTextView;
    font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");


	try
	{
		int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		yourTextView = (TextView) findViewById(titleId);
		

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
	}
	yourTextView = (TextView) findViewById(R.id.tvLoading);
	
	db=new DatabaseHandler(this);
	preferences = getSharedPreferences(sharedPrefKey, 0);
	preferencesRef=getSharedPreferences(sharedPrefKeyRefresh,0);
	String state = preferences.getString("status", "Couldn't load data!");
	String stateRef = preferencesRef.getString("status", "On destroy");
	final ConnectivityManager conMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
	final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();


	//if(stateRef.equals("On destroy"))
	//if(true)
	{
		if(activeNetwork != null && activeNetwork.isAvailable())
		{
			if(state.equals("Done!"))
			{	

				Intent intent = new Intent(Fetching.this,EventsList.class);
				startActivity(intent);
				this.finish();
			/*	mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);*/

			}		
			else
			{
				
				db.createTable();

			}
			Log.e("Network state", "Connected");

			client = new DefaultHttpClient();
			Toast.makeText(getApplication(), "Refreshing", Toast.LENGTH_SHORT).show();
			new Read().execute("fetch data");
		}
		else
		{


			if(state.equals("Done!"))
			{
				Toast.makeText(getApplicationContext(), "Failed to refresh", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(Fetching.this,EventsList.class);
				startActivity(intent);
				this.finish();
				/*
				mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);*/
				Log.e("Network state", "Disconnected");
			}		
			else
			{
				Toast.makeText(getApplicationContext(), "Internet conection required", Toast.LENGTH_LONG).show();
				finish();
				Intent intent = new Intent(Fetching.this,HomeActivity.class);
				startActivity(intent);
				this.finish();
				
				fail = true;

			}
		}
	}
	
}

public void getJSONArray() throws ClientProtocolException, IOException, JSONException
{
	StringBuilder url = new StringBuilder(URL);
	HttpGet get = new HttpGet(url.toString());
	System.out.println("Gonna execute");
	HttpClient client = new DefaultHttpClient();
	HttpResponse r = client.execute(get);
	System.out.println("Done execute");
	int status = r.getStatusLine().getStatusCode();
	if(status == 200)
	{
		HttpEntity e = r.getEntity();
		String JSONstring = EntityUtils.toString(e);
		System.out.println(JSONstring);
		System.out.println("Status execute 200");
		json = new JSONObject(JSONstring);
		JSONObject recieved = new JSONObject(JSONstring);
		//JSONArray eventsDay1 = recieved.getJSONArray("eventsday"+eventDay);



	}
	else
	{
		Log.e("API Error", "API Cannot be reached");
		
	}
} 
public class Read extends AsyncTask<String, Integer, String>
{

	@Override
	protected String doInBackground(String... params)
	{
		try 
		{	

			getJSONArray();
			if(json != null)
			{
				db.deleteTable();
				db.createTable();
			}
			JSONArray eventsDay1 = json.getJSONArray("Premium");
			JSONArray eventsDay2 = json.getJSONArray("Robomania");
			JSONArray eventsDay3 = json.getJSONArray("Workshop");
			JSONArray eventsDay4 = json.getJSONArray("BitsAndBytes");
			JSONArray eventsDay5 = json.getJSONArray("Builtrix");
			JSONArray eventsDay6 = json.getJSONArray("AppliedEngineering");
			JSONArray eventsDay7 = json.getJSONArray("Circuitrix");
			JSONArray eventsDay8 = json.getJSONArray("Bioxyn");
			JSONArray eventsDay9 = json.getJSONArray("Management");
			JSONArray eventsDay10 = json.getJSONArray("Informals");
			JSONArray eventsDay11= json.getJSONArray("Quiz");
			JSONArray eventsDay12= json.getJSONArray("Online");
			
			day1 = new Events[eventsDay1.length()];
			day2 = new Events[eventsDay2.length()];
			day3 = new Events[eventsDay3.length()];
			day4 = new Events[eventsDay4.length()];
			day5 = new Events[eventsDay5.length()];
			day6 = new Events[eventsDay6.length()];
			day7 = new Events[eventsDay7.length()];
			day8 = new Events[eventsDay8.length()];
			day9 = new Events[eventsDay9.length()];
			day10 = new Events[eventsDay10.length()];
			day11= new Events[eventsDay11.length()];
			day12 = new Events[eventsDay12.length()];
			
			int i =0;
			for(i = 0 ; i < day1.length ; i++)
			{	
				day1[i] = new Events(i,eventsDay1.getJSONObject(i),"Premium");
				db.addEvent(day1[i]);
				System.out.println("addinevents");

			}

			for(i = 0 ; i < day2.length ; i++)
			{
				day2[i] = new Events(i,eventsDay2.getJSONObject(i),"Robomania");
				db.addEvent(day2[i]);
			}

			for(i = 0 ; i < day3.length ; i++)
			{
				day3[i] = new Events(i,eventsDay3.getJSONObject(i),"Workshops");
				db.addEvent(day3[i]);
			}

			for(i = 0 ; i < day4.length ; i++)
			{
				day4[i] = new Events(i,eventsDay4.getJSONObject(i),"BitsAndBytes");
				db.addEvent(day4[i]);
			}
			
			for(i = 0 ; i < day5.length ; i++)
			{
				day5[i] = new Events(i,eventsDay5.getJSONObject(i),"Builtrix");
				db.addEvent(day5[i]);
			}

			for(i = 0 ; i < day6.length ; i++)
			{
				day6[i] = new Events(i,eventsDay6.getJSONObject(i),"AppliedEngineering");
				db.addEvent(day6[i]);
			}
			
			for(i = 0 ; i < day7.length ; i++)
			{
				day7[i] = new Events(i,eventsDay7.getJSONObject(i),"Circuitrix");
				db.addEvent(day7[i]);
			}
			
			for(i = 0 ; i < day8.length ; i++)
			{
				day8[i] = new Events(i,eventsDay8.getJSONObject(i),"Bioxyn");
				db.addEvent(day8[i]);
			}
			
			for(i = 0 ; i < day9.length ; i++)
			{
				day9[i] = new Events(i,eventsDay9.getJSONObject(i),"Management");
				db.addEvent(day9[i]);
			}
			
			for(i = 0 ; i < day10.length ; i++)
			{
				day10[i] = new Events(i,eventsDay10.getJSONObject(i),"Informals");
				db.addEvent(day10[i]);
			}
			
			for(i = 0 ; i < day11.length ; i++)
			{
				day11[i] = new Events(i,eventsDay11.getJSONObject(i),"Quiz");
				db.addEvent(day11[i]);
			}
			
			for(i = 0 ; i < day12.length ; i++)
			{
				day12[i] = new Events(i,eventsDay12.getJSONObject(i),"Online");
				db.addEvent(day12[i]);
			}

			/*for( int i = 0 ; i < json.length(); i++)
					{
						JSONObject object = json.getJSONObject(i);
						sb.append(object.getString("title"));
						sb.append(" by ");
						sb.append(object.getString("by"));
						sb.append(" \n ");
						System.out.println(sb.toString());
					}
					return sb.toString();*/
		} 
		catch (ClientProtocolException e) 
		{

			e.printStackTrace();
		}
		catch (IOException e) 
		{

			e.printStackTrace();
		}
		catch (JSONException e) 
		{

			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) 
	{

		//setContentView(R.layout.loading);
		if(json != null)
		{	
			Toast.makeText(getApplication(), "Refreshed", Toast.LENGTH_SHORT).show();
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("status", "Done!");
			editor.commit();
			//setContentView(R.layout.culturalsonreload);
			Intent intent = new Intent(Fetching.this,EventsList.class);
			startActivity(intent);
			done();
			/*mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager2);
			mViewPager.setAdapter(mSectionsPagerAdapter);*/
			
			
		}
		else
		{
			Toast.makeText(getApplication(), "Refreshing failed. Not connected to internet.", Toast.LENGTH_SHORT).show();
			//finish();
			
			//fail= true;
		}
		
	}



}
public void done() {
	this.finish();
}
@Override
protected void onDestroy() 
{
	if(fail != true)
	{
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = preferencesRef.edit();
		editor.putString("status", "Done!");
		editor.commit();

	}
	super.onDestroy();
}

@Override
protected void onPause()
{
	if(fail != true)
	{
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = preferencesRef.edit();
		editor.putString("status", "Done!");
		editor.commit();

	}
	super.onPause();
}
}
