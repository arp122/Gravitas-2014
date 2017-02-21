package com.vit.gravitas2014;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	Context context;
	private static String makeTable = "CREATE TABLE IF NOT EXISTS main"+"" +
			" (id NUMBER, day NUMBER, title TEXT, desp TEXT, prize1 TEXT, prize2 TEXT,"+
			" prize3 TEXT, rule TEXT, time TEXT, venue TEXT, coord1_name TEXT,"+
			" coord2_name TEXT, coord3_name TEXT, coord1_email TEXT, "+
			"coord2_email TEXT, coord3_email TEXT, coord1_phone NUMBER, "+
			"coord2_phone NUMBER, coord3_phone NUMBER, category TEXT, price NUMBER, color TEXT, cid NUMBER," + 
			"thumb TEXT, cover TEXT, bell NUMBER,link TEXT,team TEXT, money TEXT, people TEXT)";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "new1234";
	
	public DatabaseHandler(Context context) 
	{

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	public void createTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("Query", makeTable);
		
		db.execSQL(makeTable);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
				
	}
	
	public void addEvent(Events dayEvent)
	{
		/*if(dayEvent.id == 79 || dayEvent.id == 90)
			return;*/
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values_main= new ContentValues();
		Log.d("Here", dayEvent.id + dayEvent.title);
		values_main.put("id", dayEvent.id);
		values_main.put("day", dayEvent.day);
		values_main.put("title", dayEvent.title);
		values_main.put("desp", dayEvent.description);
		values_main.put("prize1", dayEvent.cashPrizefirst);
		values_main.put("prize2", dayEvent.cashPrizesecond);
		values_main.put("prize3", dayEvent.cashPrizethird);
		values_main.put("rule", dayEvent.rules);
		values_main.put("time", dayEvent.time);
		values_main.put("venue", dayEvent.location);
		values_main.put("coord1_name", dayEvent.coordinator1);
		values_main.put("coord2_name", dayEvent.coordinator2);
		values_main.put("category", dayEvent.category);
		//values_main.put("coord3_name", dayEvent.coordName[2]);
		//values_main.put("coord1_email", dayEvent.coordEmail[0]);
		//values_main.put("coord2_email", dayEvent.coordEmail[1]);
		//values_main.put("coord3_email", dayEvent.coordEmail[2]);
		values_main.put("coord1_phone", dayEvent.coordinator1Contact);
		values_main.put("coord2_phone", dayEvent.coordinator2Contact);
		values_main.put("link", dayEvent.googleMapsURL);
		values_main.put("team", dayEvent.team);
		values_main.put("money", dayEvent.money);
		values_main.put("people", dayEvent.people);
		//values_main.put("coord3_phone", dayEvent.coordPhone[2]);
		//values_main.put("category", dayEvent.category);
		//values_main.put("price", dayEvent.price);
		//values_main.put("bell", 0);
		
		
		db.insert("main", null, values_main);
	}
	
	public List<Events> getEvents(String name) throws SQLException
	{
		String query = "SELECT * FROM main WHERE category='"+name+"'";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Events> data =new ArrayList<Events>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Events event = new Events();
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
			//	event.day = day;
				
				//event.category = cursor.getString(cursor.getColumnIndex("category"));
				event.title = cursor.getString(cursor.getColumnIndex("title"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.description = cursor.getString(cursor.getColumnIndex("desp"));
				event.location = cursor.getString(cursor.getColumnIndex("venue"));
				//event.cashPrizefirst = cursor.getString(cursor.getColumnIndex("prize1"));
				//event.cashPrizesecond = cursor.getString(cursor.getColumnIndex("prize2"));
				//event.cashPrizethird = cursor.getString(cursor.getColumnIndex("prize3"));
				event.coordinator1=cursor.getString(cursor.getColumnIndex("coord1_name"));
				event.coordinator2=cursor.getString(cursor.getColumnIndex("coord2_name"));
				event.coordinator1Contact=cursor.getString(cursor.getColumnIndex("coord1_phone"));
				event.coordinator2Contact=cursor.getString(cursor.getColumnIndex("coord2_phone"));
				event.rules=cursor.getString(cursor.getColumnIndex("rule"));
				event.googleMapsURL=cursor.getString(cursor.getColumnIndex("link"));
				event.team=cursor.getString(cursor.getColumnIndex("team"));
				event.money=cursor.getString(cursor.getColumnIndex("money"));
				event.people=cursor.getString(cursor.getColumnIndex("people"));
				event.day=cursor.getString(cursor.getColumnIndex("day"));
				
				//event.color = cursor.getString(cursor.getColumnIndex("color"));
				//event.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
				//event.cover = cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<DayEventJson> getAllEvents()
	{
		String query = "SELECT * FROM main";
		SQLiteDatabase db = this.getWritableDatabase();
		List<DayEventJson> data =new ArrayList<DayEventJson>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				DayEventJson event = new DayEventJson();
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				event.category = cursor.getString(cursor.getColumnIndex("category"));
				event.title = cursor.getString(cursor.getColumnIndex("title"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.desc = cursor.getString(cursor.getColumnIndex("desp"));
				event.eventLocation = cursor.getString(cursor.getColumnIndex("venue"));
				event.prize[0] = cursor.getString(cursor.getColumnIndex("prize1"));
				event.prize[1] = cursor.getString(cursor.getColumnIndex("prize2"));
				event.prize[2] = cursor.getString(cursor.getColumnIndex("prize3"));
				event.color = cursor.getString(cursor.getColumnIndex("color"));
				event.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover = cursor.getString(cursor.getColumnIndex("cover"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<DayEventJson> getEventsCate(int cid)
	{
		String query = "SELECT * FROM main WHERE cid="+cid;
		SQLiteDatabase db = this.getWritableDatabase();
		List<DayEventJson> data =new ArrayList<DayEventJson>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				DayEventJson event = new DayEventJson();
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				event.category = cursor.getString(cursor.getColumnIndex("category"));
				event.title = cursor.getString(cursor.getColumnIndex("title"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.desc = cursor.getString(cursor.getColumnIndex("desp"));
				event.eventLocation = cursor.getString(cursor.getColumnIndex("venue"));
				event.prize[0] = cursor.getString(cursor.getColumnIndex("prize1"));
				event.prize[1] = cursor.getString(cursor.getColumnIndex("prize2"));
				event.prize[2] = cursor.getString(cursor.getColumnIndex("prize3"));
				event.color = cursor.getString(cursor.getColumnIndex("color"));
				event.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover = cursor.getString(cursor.getColumnIndex("cover"));
				
				data.add(event);
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<DayEventJson> getEvent(int day, int id)
	{
		String query = "SELECT * FROM main WHERE day="+day+" AND id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<DayEventJson> data =new ArrayList<DayEventJson>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				DayEventJson event = new DayEventJson();
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				event.day = day;
				event.title = cursor.getString(cursor.getColumnIndex("title"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.desc = cursor.getString(cursor.getColumnIndex("desp"));
				event.eventLocation = cursor.getString(cursor.getColumnIndex("venue"));
				event.prize[0] = cursor.getString(cursor.getColumnIndex("prize1"));
				event.prize[1] = cursor.getString(cursor.getColumnIndex("prize2"));
				event.prize[2] = cursor.getString(cursor.getColumnIndex("prize3"));
				event.color = cursor.getString(cursor.getColumnIndex("color"));
				event.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover = cursor.getString(cursor.getColumnIndex("cover"));
				
				data.add(event);
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;

	}
	
	public List<DayEventJson> getEvent(int id)
	{
		String query = "SELECT * FROM main WHERE id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<DayEventJson> data =new ArrayList<DayEventJson>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				DayEventJson event = new DayEventJson();
				event.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				event.day = Integer.parseInt(cursor.getString(cursor.getColumnIndex("day")));
				event.title = cursor.getString(cursor.getColumnIndex("title"));
				event.time = cursor.getString(cursor.getColumnIndex("time"));
				event.desc = cursor.getString(cursor.getColumnIndex("desp"));
				event.eventLocation = cursor.getString(cursor.getColumnIndex("venue"));
				event.prize[0] = cursor.getString(cursor.getColumnIndex("prize1"));
				event.prize[1] = cursor.getString(cursor.getColumnIndex("prize2"));
				event.prize[2] = cursor.getString(cursor.getColumnIndex("prize3"));
				event.color = cursor.getString(cursor.getColumnIndex("color"));
				event.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
				event.cover = cursor.getString(cursor.getColumnIndex("cover"));
				
				data.add(event);
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;

	}
	
	public Cursor listTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor table = db.rawQuery("SELECT name _id FROM sqlite_master WHERE type = 'table' ", null);
		if(table.moveToFirst())
		{
			Log.d("Table List", "Not Null " + table.getCount());
			do
			{
				Log.d("Table Name", table.getString(0));
			}
			while(table.moveToNext());
			
			return table;

			
		}
		table.close();
		return null;
	}
	public boolean exists(String table) {
	    try {
	    	SQLiteDatabase db = this.getWritableDatabase();
	         db.execSQL("SELECT * FROM main");
	         System.out.println("TRUE");
	         return true;
	    } catch (SQLException e) {
	    	System.out.println("FALSE");
	    	return false;
	    }
	}
	
	public void deleteTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String query1 = "DROP TABLE IF EXISTS main";
		
		db.execSQL(query1);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS  main");
		// Create tables again
		onCreate(db);
	}
	


	
}
