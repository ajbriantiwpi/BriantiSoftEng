package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.servicerequest.ConfReservation;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ConfRoom {
  @Getter @Setter private int roomID;
  @Getter @Setter private String locationName;
  @Getter @Setter private int seats;
  @Getter @Setter private ObservableList<ConfReservation> reservations;

  @Getter @Setter private int origRoomID;
  @Getter @Setter private String origLocationName;
  @Getter @Setter private int origSeats;

  public ConfRoom(int roomID, String locName, int seats) {
    this.roomID = roomID;
    this.locationName = locName;
    this.seats = seats;

    this.origRoomID = roomID;
    this.origLocationName = locationName;
    this.origSeats = seats;
  }
  public ConfRoom(){

  }

  public void setRes(ArrayList<ConfRoom> c){

  }

  public boolean equals(ConfRoom cr) {
    return this.roomID == cr.getRoomID();
  }

  public String toString() {
    return "Room ID: " + roomID + ", Location Name: " + locationName + ", Seats: " + seats;
  }
}
