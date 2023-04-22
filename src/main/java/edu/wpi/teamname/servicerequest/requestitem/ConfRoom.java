package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

public class ConfRoom {
    @Getter @Setter private  String room;
    @Getter @Setter private  String startTime;
    @Getter @Setter private  String endTime;
    @Getter @Setter private Timestamp dateBooked;
    @Getter @Setter private  String name;

    @Getter @Setter private  String origRoom;
    @Getter @Setter private  String origStartTime;
    @Getter @Setter private  String origEndTime;
    @Getter @Setter private Timestamp origDateBooked;

    public ConfRoom(String r, String s, String e, Timestamp db, String n){
        this.room = r;
        this.startTime = s;
        this.endTime = e;
        this.dateBooked = db;
        this.name = n;

        this.origRoom = r;
        this.origStartTime = s;
        this.origEndTime = e;
        this.origDateBooked = db;
    }

    public boolean equals(ConfRoom cr){
        return this.room.equals(cr.getRoom()) && this.startTime.equals(cr.getStartTime()) && this.endTime.equals(cr.getEndTime()) && this.dateBooked == cr.dateBooked;
    }


    public String toString(){
        return "Room: " + room + ", Start Time: " + startTime + ", End Time: " + endTime + " Date Booked For: " + dateBooked + ", Name Who Booked: " + name;
    }
}
