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

import com.vit.gravitas2014.Culturals.Read;
import com.vit.gravitas2014.Culturals.SectionsPagerAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


public class EventsList extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mEventTitles={"Premium","Robomania","Workshops","BitsAndBytes","Builtrix","AppliedEngineering","Circuitrix","Bioxyn","Management","Informals","Quiz","Online"};
    static Typeface font;
	
     HttpClient client;
	JSONObject json;
	final static String URL = "http://vitgravitas.com/app/events.php";
	Events[] day1,day2,day3,day4;
	static boolean fail = false;
	static DatabaseHandler db;
	
	SharedPreferences preferences;
	String sharedPrefKey = "database";
	String sharedPrefKeyRefresh="refresh";
	SharedPreferences preferencesRef;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
		db=new DatabaseHandler(this);

        
        mTitle = mDrawerTitle = getTitle();
       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
       mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mEventTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
    	
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
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        mDrawerLayout.openDrawer(Gravity.LEFT);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        
            return super.onOptionsItemSelected(item);
        
    }

    /* Called whenever we call invalidateOptionsMenu() */
    

    

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mEventTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

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

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment implements OnItemClickListener {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        private String[] mEventTitles={"Premium","Robomania","Workshops","BitsAndBytes","Builtrix","AppliedEngineering","Circuitrix","Bioxyn","Management","Informals","Quiz","Online"};
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
		public String[] team;
		public String[] money;
		public String[] date;
        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int j = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = mEventTitles[j];
            getActivity().setTitle(planet);
            
            List<Events> event_1=db.getEvents(planet);
            ListView listView1=(ListView)rootView.findViewById(R.id.listViewday1);

try{

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
			team = new String[event_1.size()];
			date = new String[event_1.size()];
			money = new String[event_1.size()];
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
				team[i]=""+cn.getTeam();
				money[i]=""+cn.getMoney();
				date[i]=""+cn.getDay();
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
		String teamsOf = team[position];
		String regFee = money[position];
		String event_date = date[position];
		ImageView imageView;
		
		Activity a = getActivity();
		Dialog d = new Dialog(a);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.cultural_dialog);
		((TextView)d.findViewById(R.id.tvEventName)).setText(heading);
		((TextView)d.findViewById(R.id.tvEventName)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvEventDates)).setText("Date :"+event_date);
		((TextView)d.findViewById(R.id.tvEventDates)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvEventTime)).setText("Time: "+time1);
		((TextView)d.findViewById(R.id.tvEventTime)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvEventVenue)).setText("Venue: "+venue+"\n");
		((TextView)d.findViewById(R.id.tvEventVenue)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvEventDesc)).setText("\nDescription: \n"+desc);
		((TextView)d.findViewById(R.id.tvEventDesc)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvHeadingCoordinators)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvRegistrationFee)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvRegistrationFee)).setText("Registration Fee:"+regFee);
		((TextView)d.findViewById(R.id.tvTeam)).setTypeface(font);
		((TextView)d.findViewById(R.id.tvTeam)).setText("Teams of:"+teamsOf);
		
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
					/*Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
					startActivity(openMaps);*/
				}
				catch(Exception e)
				{
					e.printStackTrace();
					//System.out.println(googleMapsLink);
				}
			}
		}); 

		d.show();

	}
    }
    
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
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent myinIntent=new Intent(this,HomeActivity.class);
		startActivity(myinIntent);
		this.finish();
	}
}
