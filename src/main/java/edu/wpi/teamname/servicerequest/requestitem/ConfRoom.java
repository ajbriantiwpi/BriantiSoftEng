package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public class ConfRoom {
  @Getter @Setter private int roomID;
  @Getter @Setter private String locationName;
  @Getter @Setter private int seats;

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

  public boolean equals(ConfRoom cr) {
    return this.roomID == cr.getRoomID();
  }

  public String toString() {
    return "Room ID: " + roomID + ", Location Name: " + locationName + ", Seats: " + seats;
  }
}
