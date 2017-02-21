package com.vit.gravitas2014;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Culturals extends FragmentActivity
{
	HttpClient client;
	JSONObject json;
	final static String URL = "http://vitgravitas.com/app/events.php";
	Events[] day1,day2,day3,day4;
	static Typeface font;
	static DatabaseHandler db;
	static boolean fail = false;

	TextView day1_text,day2_text,day3_text,day4_text;

	String sharedPrefKey = "database";
	String sharedPrefKeyRefresh="refresh";
	SharedPreferences preferencesRef;
	Context context;
	SectionsPagerAdapter mSectionsPagerAdapter;
	SharedPreferences preferences;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");


		TextView yourTextView;


		try
		{
			int titleId = getResources().getIdentifier("action_bar_title", "id",
					"android");
			yourTextView = (TextView) findViewById(titleId);
			yourTextView.setTypeface(font);
			

			getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		yourTextView = (TextView) findViewById(R.id.tvLoading);
		yourTextView.setTypeface(font);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
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

					setContentView(R.layout.culturals);
					mSectionsPagerAdapter = new SectionsPagerAdapter(
							getSupportFragmentManager());

					// Set up the ViewPager with the sections adapter.
					mViewPager = (ViewPager) findViewById(R.id.pager);
					mViewPager.setAdapter(mSectionsPagerAdapter);

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
					setContentView(R.layout.culturals);
					mSectionsPagerAdapter = new SectionsPagerAdapter(
							getSupportFragmentManager());

					// Set up the ViewPager with the sections adapter.
					mViewPager = (ViewPager) findViewById(R.id.pager);
					mViewPager.setAdapter(mSectionsPagerAdapter);
					Log.e("Network state", "Disconnected");
				}		
				else
				{
					Toast.makeText(getApplicationContext(), "Internet conection required", Toast.LENGTH_LONG).show();
					finish();
					Intent intent = new Intent(Culturals.this,HomeActivity.class);
					startActivity(intent);
					fail = true;

				}
			}
		}
		/*else
		{
			setContentView(R.layout.culturals);
			mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			Toast.makeText(getApplicationContext(), "Internet conection required", Toast.LENGTH_LONG).show();
			finish();
			Intent intent = new Intent(Culturals.this,HomeActivity.class);
			startActivity(intent);
		}*/
		/*setContentView(R.layout.culturals);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);*/

	}



	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		Bundle args;
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch (position) {

			case 0:
				Fragment fragment = new DummySectionFragment();
				args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;

			case 1:
				Fragment fragment2 = new DummySectionFragment2();
				args = new Bundle();
				args.putInt(DummySectionFragment2.ARG_SECTION_NUMBER, position + 2);
				fragment2.setArguments(args);
				return fragment2;
			case 2:
				Fragment fragment3 = new DummySectionFragment3();
				args = new Bundle();
				args.putInt(DummySectionFragment3.ARG_SECTION_NUMBER, position + 3);
				fragment3.setArguments(args);
				return fragment3;
			case 3:
				Fragment fragment4 = new DummySectionFragment4();
				args = new Bundle();
				args.putInt(DummySectionFragment4.ARG_SECTION_NUMBER, position + 4);
				fragment4.setArguments(args);
				return fragment4;



			default:
				return null;
			}


		}


		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);

			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment implements OnItemClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment()
		{

		}

		public String[] titles;
		public String[] location;
		public String[] category;
		public String[] time;
		public String[] description;
		public String[] rules;
		public String[] cord1_name;
		public String[] cord2_name;
		public String[] cord1_contact;
		public String[] cord2_contact;
		public String[] googleMaps;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.culday1, container, false);
			ListView listView1=(ListView)rootView.findViewById(R.id.listViewday1);



			try
			{
				List<Events> event_1=db.getEvents("1");



				titles = new String[event_1.size()];
				location = new String[event_1.size()];
				category = new String[event_1.size()];
				time = new String[event_1.size()];
				description = new String[event_1.size()];
				rules = new String[event_1.size()];
				cord1_name = new String[event_1.size()];
				cord2_name = new String[event_1.size()];
				cord1_contact = new String[event_1.size()];
				cord2_contact = new String[event_1.size()];
				googleMaps = new String[event_1.size()];
				int i=0;
				for (Events cn : event_1)
				{

					titles[i]=""+cn.getTitle();
					time[i]=""+cn.getTime();
					location[i]=""+cn.getLoc();
					category[i]=""+cn.getCat();
					description[i]=""+cn.getDesc();
					rules[i]=""+cn.getRules();
					cord1_name[i]=""+cn.getCor1Name();
					cord2_name[i]=""+cn.getCor2Name();
					cord1_contact[i]=""+cn.getCor1phone();
					cord2_contact[i]=""+cn.getCor2phone();
					googleMaps[i] = ""+cn.getGoogleMapsURL();
					i++;

				}
				List<RowItem> rowItems;
				rowItems = new ArrayList<RowItem>();
				for ( i = 0; i<event_1.size(); i++) {
					RowItem item = new RowItem(titles[i],time[i],location[i],category[i],"#CCCCCC");
					rowItems.add(item);
				}
				CustomBaseAdapter adapter = new CustomBaseAdapter(this.getActivity(), rowItems,font);
				listView1.setAdapter(adapter);
				listView1.setOnItemClickListener(this);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				fail= true;
			}
			return rootView;
		}

		String googleMapsLink;

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			String heading = titles[position];
			String time1 = time[position];
			String desc = description[position];
			String rules1 = rules[position];
			String venue = location[position];
			String coordinator1 = cord1_name[position];
			String coordinator1_contact = cord1_contact[position];
			String coordinator2 = cord2_name[position];
			String coordinator2_contact = cord2_contact[position];
			ImageView imageView;
			googleMapsLink = googleMaps[position];

			Activity a = getActivity();
			Dialog d = new Dialog(a);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.cultural_dialog);
			((TextView)d.findViewById(R.id.tvEventName)).setText(heading);
			((TextView)d.findViewById(R.id.tvEventName)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDates)).setText("Date : 6th February");
			((TextView)d.findViewById(R.id.tvEventDates)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventTime)).setText("Time: "+time1);
			((TextView)d.findViewById(R.id.tvEventTime)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventVenue)).setText("Venue: "+venue+"\n");
			((TextView)d.findViewById(R.id.tvEventVenue)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDesc)).setText("\nDescription: \n"+desc);
			((TextView)d.findViewById(R.id.tvEventDesc)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvHeadingCoordinators)).setTypeface(font);
			if(rules1.equals("NULL"))
			{
				rules1 = "Not Available";
			}
			else
			{
				if(rules1.length() == 6 )
				{
					rules1 = "Not Available";
				}
			}
			((TextView)d.findViewById(R.id.tvEventRules)).setText("\nRules : \n"+rules1);
			((TextView)d.findViewById(R.id.tvEventRules)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord1)).setText(coordinator1 + " : ");
			((TextView)d.findViewById(R.id.tvCoord1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setText(coordinator1_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord2)).setText(coordinator2 + " : ");
			((TextView)d.findViewById(R.id.tvCoord2)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setText(coordinator2_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setTypeface(font);

			imageView = (ImageView)d.findViewById(R.id.ivImageLocation);
			imageView.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View view) 
				{
					try
					{
						Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
						startActivity(openMaps);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println(googleMapsLink);
					}
				}
			}); 

			d.show();

		}



	}
	public static class DummySectionFragment2 extends Fragment implements OnItemClickListener
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment2() {
		}

		public String[] titles;
		public String[] location;
		public String[] category;
		public String[] time;
		public String[] description;
		public String[] rules;
		public String[] cord1_name;
		public String[] cord2_name;
		public String[] cord1_contact;
		public String[] cord2_contact;
		public String[] googleMaps;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.culday2, container, false);
			ListView listView1=(ListView)rootView.findViewById(R.id.listViewday2);


			try
			{
				List<Events> event_2=db.getEvents("2");

				titles = new String[event_2.size()];
				location = new String[event_2.size()];
				category = new String[event_2.size()];
				time = new String[event_2.size()];
				description = new String[event_2.size()];
				rules = new String[event_2.size()];
				cord1_name = new String[event_2.size()];
				cord2_name = new String[event_2.size()];
				cord1_contact = new String[event_2.size()];
				cord2_contact = new String[event_2.size()];
				googleMaps = new String[event_2.size()];



				int i=0;
				for (Events cn : event_2){
					titles[i]=""+cn.getTitle();
					time[i]=""+cn.getTime();
					location[i]=""+cn.getLoc();
					category[i]=""+cn.getCat();
					description[i]=""+cn.getDesc();
					rules[i]=""+cn.getRules();
					cord1_name[i]=""+cn.getCor1Name();
					cord2_name[i]=""+cn.getCor2Name();
					cord1_contact[i]=""+cn.getCor1phone();
					cord2_contact[i]=""+cn.getCor2phone();
					googleMaps[i] = ""+cn.getGoogleMapsURL();
					i++;

				}
				List<RowItem> rowItems;
				rowItems = new ArrayList<RowItem>();
				for ( i = 0; i<event_2.size(); i++) {
					RowItem item = new RowItem(titles[i],time[i],location[i],category[i],"#CCCCCC");
					rowItems.add(item);
				}
				CustomBaseAdapter adapter = new CustomBaseAdapter(this.getActivity(), rowItems,font);
				listView1.setAdapter(adapter);
				listView1.setOnItemClickListener(this);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				fail= true;
			}
			return rootView;
		}


		String googleMapsLink;

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			String heading = titles[position];
			String time1 = time[position];
			String desc = description[position];
			String rules1 = rules[position];
			String venue = location[position];
			String coordinator1 = cord1_name[position];
			String coordinator1_contact = cord1_contact[position];
			String coordinator2 = cord2_name[position];
			String coordinator2_contact = cord2_contact[position];
			ImageView imageView;
			googleMapsLink = googleMaps[position];

			Activity a = getActivity();
			Dialog d = new Dialog(a);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.cultural_dialog);
			((TextView)d.findViewById(R.id.tvEventName)).setText(heading);
			((TextView)d.findViewById(R.id.tvEventName)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDates)).setText("Date : 7th February");
			((TextView)d.findViewById(R.id.tvEventDates)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventTime)).setText("Time: "+time1);
			((TextView)d.findViewById(R.id.tvEventTime)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventVenue)).setText("Venue: "+venue+"\n");
			((TextView)d.findViewById(R.id.tvEventVenue)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDesc)).setText("\nDescription: \n"+desc);
			((TextView)d.findViewById(R.id.tvEventDesc)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvHeadingCoordinators)).setTypeface(font);
			if(rules1.equals("NULL"))
			{
				rules1 = "Not Available";
			}
			else
			{
				if(rules1.length() == 6 )
				{
					rules1 = "Not Available";
				}
			}
			((TextView)d.findViewById(R.id.tvEventRules)).setText("\nRules : \n"+rules1);
			((TextView)d.findViewById(R.id.tvEventRules)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord1)).setText(coordinator1 + " : ");
			((TextView)d.findViewById(R.id.tvCoord1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setText(coordinator1_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord2)).setText(coordinator2 + " : ");
			((TextView)d.findViewById(R.id.tvCoord2)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setText(coordinator2_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setTypeface(font);

			imageView = (ImageView)d.findViewById(R.id.ivImageLocation);
			imageView.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View view) 
				{
					try
					{
						Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
						startActivity(openMaps);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println(googleMapsLink);
					}
				}
			}); 

			d.show();

		}



	}
	public static class DummySectionFragment3 extends Fragment implements OnItemClickListener
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment3() {
		}


		public String[] titles;
		public String[] location;
		public String[] category;
		public String[] time;
		public String[] description;
		public String[] rules;
		public String[] cord1_name;
		public String[] cord2_name;
		public String[] cord1_contact;
		public String[] cord2_contact;
		public String[] googleMaps;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.culday3, container, false);
			ListView listView1=(ListView)rootView.findViewById(R.id.listViewday3);


			try
			{
				List<Events> event_3=db.getEvents("3");

				titles = new String[event_3.size()];
				location = new String[event_3.size()];
				category = new String[event_3.size()];
				time = new String[event_3.size()];
				description = new String[event_3.size()];
				rules = new String[event_3.size()];
				cord1_name = new String[event_3.size()];
				cord2_name = new String[event_3.size()];
				cord1_contact = new String[event_3.size()];
				cord2_contact = new String[event_3.size()];
				googleMaps = new String[event_3.size()];


				int i=0;
				for (Events cn : event_3)
				{
					titles[i]=""+cn.getTitle();
					time[i]=""+cn.getTime();
					location[i]=""+cn.getLoc();
					category[i]=""+cn.getCat();
					description[i]=""+cn.getDesc();
					rules[i]=""+cn.getRules();
					cord1_name[i]=""+cn.getCor1Name();
					cord2_name[i]=""+cn.getCor2Name();
					cord1_contact[i]=""+cn.getCor1phone();
					cord2_contact[i]=""+cn.getCor2phone();
					googleMaps[i] = ""+cn.getGoogleMapsURL();
					i++;

				}

				List<RowItem> rowItems;
				rowItems = new ArrayList<RowItem>();
				for ( i = 0; i<event_3.size(); i++) {
					RowItem item = new RowItem( titles[i],time[i],location[i],category[i],"#CCCCCC");
					rowItems.add(item);
				}
				CustomBaseAdapter adapter = new CustomBaseAdapter(this.getActivity(), rowItems,font);
				listView1.setAdapter(adapter);
				listView1.setOnItemClickListener(this);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				fail= true;
			}
			return rootView;
		}



		String googleMapsLink;

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			String heading = titles[position];
			String time1 = time[position];
			String desc = description[position];
			String rules1 = rules[position];
			String venue = location[position];
			String coordinator1 = cord1_name[position];
			String coordinator1_contact = cord1_contact[position];
			String coordinator2 = cord2_name[position];
			String coordinator2_contact = cord2_contact[position];
			ImageView imageView;
			googleMapsLink = googleMaps[position];

			Activity a = getActivity();
			Dialog d = new Dialog(a);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.cultural_dialog);
			((TextView)d.findViewById(R.id.tvEventName)).setText(heading);
			((TextView)d.findViewById(R.id.tvEventName)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDates)).setText("Date : 8th February");
			((TextView)d.findViewById(R.id.tvEventDates)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventTime)).setText("Time: "+time1);
			((TextView)d.findViewById(R.id.tvEventTime)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventVenue)).setText("Venue: "+venue+"\n");
			((TextView)d.findViewById(R.id.tvEventVenue)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDesc)).setText("\nDescription: \n"+desc);
			((TextView)d.findViewById(R.id.tvEventDesc)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvHeadingCoordinators)).setTypeface(font);
			if(rules1.equals("NULL"))
			{
				rules1 = "Not Available";
			}
			else
			{
				if(rules1.length() == 6 )
				{
					rules1 = "Not Available";
				}
			}
			((TextView)d.findViewById(R.id.tvEventRules)).setText("\nRules : \n"+rules1);
			((TextView)d.findViewById(R.id.tvEventRules)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord1)).setText(coordinator1 + " : ");
			((TextView)d.findViewById(R.id.tvCoord1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setText(coordinator1_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord2)).setText(coordinator2 + " : ");
			((TextView)d.findViewById(R.id.tvCoord2)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setText(coordinator2_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setTypeface(font);

			imageView = (ImageView)d.findViewById(R.id.ivImageLocation);
			imageView.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View view) 
				{
					try
					{
						Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
						startActivity(openMaps);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println(googleMapsLink);
					}
				}
			}); 

			d.show();

		}
	}
	public static class DummySectionFragment4 extends Fragment implements OnItemClickListener
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment4() 
		{
		}


		public String[] titles;
		public String[] location;
		public String[] category;
		public String[] time;
		public String[] description;
		public String[] rules;
		public String[] cord1_name;
		public String[] cord2_name;
		public String[] cord1_contact;
		public String[] cord2_contact;
		public String[] googleMaps;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.culday4, container, false);
			ListView listView1=(ListView)rootView.findViewById(R.id.listViewday4);

			try
			{
				List<Events> event_4=db.getEvents("4");

				titles = new String[event_4.size()];
				location = new String[event_4.size()];
				category = new String[event_4.size()];
				time = new String[event_4.size()];
				description = new String[event_4.size()];
				rules = new String[event_4.size()];
				cord1_name = new String[event_4.size()];
				cord2_name = new String[event_4.size()];
				cord1_contact = new String[event_4.size()];
				cord2_contact = new String[event_4.size()];
				googleMaps = new String[event_4.size()];

				int i=0;
				for (Events cn : event_4)
				{
					titles[i]=""+cn.getTitle();
					time[i]=""+cn.getTime();
					location[i]=""+cn.getLoc();
					category[i]=""+cn.getCat();
					description[i]=""+cn.getDesc();
					rules[i]=""+cn.getRules();
					cord1_name[i]=""+cn.getCor1Name();
					cord2_name[i]=""+cn.getCor2Name();
					cord1_contact[i]=""+cn.getCor1phone();
					cord2_contact[i]=""+cn.getCor2phone();
					googleMaps[i] = ""+cn.getGoogleMapsURL();
					i++;

				}
				List<RowItem> rowItems;
				rowItems = new ArrayList<RowItem>();
				for ( i = 0; i<event_4.size(); i++) {
					RowItem item = new RowItem( titles[i],time[i],location[i],category[i],"#CCCCCC");
					rowItems.add(item);
				}
				CustomBaseAdapter adapter = new CustomBaseAdapter(this.getActivity(), rowItems,font);
				listView1.setAdapter(adapter);
				listView1.setOnItemClickListener(this);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				fail = true;
			}
			return rootView;
		}



		String googleMapsLink;

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			String heading = titles[position];
			String time1 = time[position];
			String desc = description[position];
			String rules1 = rules[position];
			String venue = location[position];
			String coordinator1 = cord1_name[position];
			String coordinator1_contact = cord1_contact[position];
			String coordinator2 = cord2_name[position];
			String coordinator2_contact = cord2_contact[position];
			ImageView imageView;
			googleMapsLink = googleMaps[position];

			Activity a = getActivity();
			Dialog d = new Dialog(a);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.cultural_dialog);
			((TextView)d.findViewById(R.id.tvEventName)).setText(heading);
			((TextView)d.findViewById(R.id.tvEventName)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDates)).setText("Date : 9th February");
			((TextView)d.findViewById(R.id.tvEventDates)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventTime)).setText("Time: "+time1);
			((TextView)d.findViewById(R.id.tvEventTime)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventVenue)).setText("Venue: "+venue+"\n");
			((TextView)d.findViewById(R.id.tvEventVenue)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvEventDesc)).setText("\nDescription: \n"+desc);
			((TextView)d.findViewById(R.id.tvEventDesc)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvHeadingCoordinators)).setTypeface(font);
			if(rules1.equals("NULL"))
			{
				rules1 = "Not Available";
			}
			else
			{
				if(rules1.length() == 6 )
				{
					rules1 = "Not Available";
				}
			}
			((TextView)d.findViewById(R.id.tvEventRules)).setText("\nRules: \n"+rules1);
			((TextView)d.findViewById(R.id.tvEventRules)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord1)).setText(coordinator1 + " : ");
			((TextView)d.findViewById(R.id.tvCoord1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setText(coordinator1_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone1)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoord2)).setText(coordinator2 + " : ");
			((TextView)d.findViewById(R.id.tvCoord2)).setTypeface(font);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setText(coordinator2_contact);
			((TextView)d.findViewById(R.id.tvCoordPhone2)).setTypeface(font);

			imageView = (ImageView)d.findViewById(R.id.ivImageLocation);
			imageView.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View view) 
				{
					try
					{
						Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
						startActivity(openMaps);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println(googleMapsLink);
					}
				}
			}); 

			d.show();

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
				JSONArray eventsDay1 = json.getJSONArray("eventsday1");
				JSONArray eventsDay2 = json.getJSONArray("eventsday2");
				JSONArray eventsDay3 = json.getJSONArray("eventsday3");
				JSONArray eventsDay4 = json.getJSONArray("eventsday4");

				day1 = new Events[eventsDay1.length()];
				day2 = new Events[eventsDay2.length()];
				day3 = new Events[eventsDay3.length()];
				day4 = new Events[eventsDay4.length()];

				int i =0;
				for(i = 0 ; i < day1.length ; i++)
				{
					day1[i] = new Events(i,eventsDay1.getJSONObject(i),"");
					db.addEvent(day1[i]);

				}

				for(i = 0 ; i < day2.length ; i++)
				{
					day2[i] = new Events(i,eventsDay2.getJSONObject(i),"");
					db.addEvent(day2[i]);
				}

				for(i = 0 ; i < day3.length ; i++)
				{
					day3[i] = new Events(i,eventsDay3.getJSONObject(i),"");
					db.addEvent(day3[i]);
				}

				for(i = 0 ; i < day4.length ; i++)
				{
					day4[i] = new Events(i,eventsDay4.getJSONObject(i),"");
					db.addEvent(day4[i]);
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
				setContentView(R.layout.culturalsonreload);
				mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager2);
				mViewPager.setAdapter(mSectionsPagerAdapter);
				Toast.makeText(getApplication(), "Refreshed", Toast.LENGTH_SHORT).show();
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("status", "Done!");
				editor.commit();
			}
			else
			{
				Toast.makeText(getApplication(), "Refreshing failed. Not connected to internet.", Toast.LENGTH_SHORT).show();
				//finish();
				//Intent intent = new Intent(Culturals.this,HomeActivity.class);
				//startActivity(intent);
				//fail= true;
			}
		}



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

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.splash, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		String sharedPrefKey = "searchbuddy";
		SharedPreferences pref = getSharedPreferences(sharedPrefKey, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("searchstate", "culturals");
		editor.commit();
		
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
}
