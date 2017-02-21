package com.vit.gravitas2014;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Events 
{
	int id;
	String title;
	String day;
	String time;
	String location;
	String googleMapsURL;
	String rules;
	String cashPrizefirst;
	String cashPrizesecond;
	String cashPrizethird;
	String coordinator1;
	String coordinator1Contact;
	String coordinator2;
	String coordinator2Contact;
	String description;
	String team;
	String money;
	String people;
	String category;
	private String[] prize;
	private JSONObject data;
	
	public Events()
	{
		this.prize= new String[5];
	}
	public Events(int slno, JSONObject jsonData,String category)
	{	this.data= jsonData;
		//this.day=dayParam;
		this.category=category;
		try 
		{
			//id = jsonData.getString("id");
			id= slno+1;
			title = jsonData.getString("name");;
			if(title.equals("null"))
			{
				title = "Not Available";
			}
			day = jsonData.getString("date");;
			if(day.equals("null"))
			{
				day = "Not Available";
			}
			
			time = jsonData.getString("time");
			if(time.equals("null"))
			{
				time = "Not Available";
			}
			
			location = jsonData.getString("venue");
			if(location.equals("null"))
			{
				location = "Not Available";
			}
			team = jsonData.getString("team");;
			if(team.equals("null"))
			{
				team = "Not Available";
			}
			
			money = jsonData.getString("money");
			if(money.equals("null"))
			{
				money = "Not Available";
			}
			
			people = jsonData.getString("people");
			if(people.equals("null"))
			{
				people = "Not Available";
			}
			/*googleMapsURL = jsonData.getString("event_loclink");
			if(googleMapsURL.equals("null"))
			{
				googleMapsURL = "https://www.google.co.in/maps/preview/place/VIT+University,+Vellore,+Tamil+Nadu/@12.9713945,79.157423,16z/data=!3m1!4b1!4m2!3m1!1s0x3bad47a17f3461c1:0x1ace7a2a7f8ccfbf";
			}*/
			
			rules = jsonData.getString("rules");
			if(rules.equals("null"))
			{
				rules = "Not Available";
			}
			
			/*String temp = jsonData.getString("event_room");
			if(!temp.equals("null"))
			{
				location += " ("+temp+")";
			}*/
			
			description =  jsonData.getString("desc");
			if(description.equals("null"))
			{
				description = "Not Available";
			}
			
			coordinator1 = jsonData.getString("cod1");
			coordinator1Contact = jsonData.getString("codnum1");
			if(coordinator1.equals("null"))
			{
				coordinator1 = "Not Available";
			}
			if(coordinator1Contact.equals("null"))
			{
				coordinator1Contact = "Not Available";
			}
			
			coordinator2 = jsonData.getString("cod2");
			coordinator2Contact = jsonData.getString("codnum2");
			
			if(coordinator2.equals("null"))
			{
				coordinator2 = "Not Available";
			}
			if(coordinator2Contact.equals("null"))
			{
				coordinator2Contact = "Not Available";
			}
			
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	public String getTitle(){
		return this.title;
	}
	public String getTime(){
		return this.time;
	}
	public String getLoc(){
		return this.location;
	}
	public String getCat(){
		return this.location;
	}
	public String getDesc(){
		return this.description;
	}
	public String getRules(){
		return this.rules;
	}
	public String getCor1Name(){
		return this.coordinator1;
	}
	public String getCor2Name(){
		return this.coordinator2;
	}
	public String getCor1phone(){
		return this.coordinator1Contact;
	}
	
	public String getCor2phone()
	{
		return this.coordinator2Contact;
	}
	
	public String getGoogleMapsURL()
	{
		return this.googleMapsURL;
	}
	public String getTeam(){
		return this.team;
	}
	public String getMoney(){
		return this.money;
	}
	public String getPeople(){
		return this.people;
	}
	public String getDay()
	{
		return this.day;
	}
	public String getCategory()
	{
		return this.category;
	}
	
}
