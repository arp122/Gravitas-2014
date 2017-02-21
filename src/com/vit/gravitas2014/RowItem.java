package com.vit.gravitas2014;

public class RowItem {
    private String location;
    private String title;
    private String time;
    private String cat;
    private String colour;
    public RowItem(String title, String time,String location, String cat,String colour) {
        this.location = location;
        this.title = title;
        this.time= time;
        this.cat=cat;
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
    public void setCat(String cat) {
        this.cat = cat;
    }
    public String getCat() {
        return cat;
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
        return title + "\n" + location + "\n" +time+"\n"+cat;
    }
}
