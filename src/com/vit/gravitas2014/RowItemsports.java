package com.vit.gravitas2014;

public class RowItemsports {

	 private String location;
	    private String title;
	    private String time;
	    private String colour;
	    public RowItemsports(String title, String time,String location,String colour) {
	        this.location = location;
	        this.title = title;
	        this.time= time;
	        this.colour=colour;
	    }
	    public String getColour() {
	        return colour;
	    }
	    public void setcolour(String colour) {
	        this.colour = colour;
	    }
	    public String getLocation() {
	        return location;
	    }
	    public void setLocation(String location) {
	        this.location = location;
	    }
	    public String getTime() {
	        return time;
	    }
	    public void setTime(String time) {
	        this.time = time;
	    }
	    public String getTitle() {
	        return title;
	    }
	    public void setTitle(String title) {
	        this.title = title;
	    }
	    @Override
	    public String toString() {
	        return title + "\n" + location + "\n" +time;
	    }
}
