package com.example.android.mytodo;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by shruti on 09-09-2017.
 */

public class schedules implements Serializable {
    private String title;
    private String description;
    private long toDoID;



    private String date;



    private String time;
 public  schedules( String title,String description,long id,String date,String time){
     this.title = title;
     this.description = description;
     this.toDoID=id;
     this.date = date;
     this.time =time;
 }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public long getToDoID(){
        return toDoID;
    }

    public void setToDoID(long toDoID) {
        this.toDoID = toDoID;
    }
}
