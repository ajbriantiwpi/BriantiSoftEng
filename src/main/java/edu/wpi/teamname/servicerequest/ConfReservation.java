package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class ConfReservation {

  @Getter @Setter private int resID;
  @Getter @Setter private String startTime;
  @Getter @Setter private String endTime;
  @Getter @Setter private Timestamp dateBook;
  @Getter @Setter private Timestamp dateMade; // calc here
  @Getter @Setter private String name;
  @Getter @Setter private String username;
  @Getter @Setter private String staffAssigned;
  @Getter @Setter private int roomID;

  @Getter @Setter private int origResID;
  @Getter @Setter private String origStartTime;
  @Getter @Setter private String origEndTime;
  @Getter @Setter private Timestamp origDateBook;
  @Getter @Setter private Timestamp origDateMade;
  @Getter @Setter private String origName;
  @Getter @Setter private String origUsername;
  @Getter @Setter private String origStaffAssigned;
  @Getter @Setter private int origRoomID;

  public ConfReservation(
      int resID,
      String startT,
      String endT,
      Timestamp dateBook,
      Timestamp dateMade,
      String name,
      String username,
      String staff,
      int roomID) {
    this.resID = resID;
    this.startTime = startT;
    this.endTime = endT;
    this.dateBook = dateBook;
    this.dateMade = dateMade;
    this.name = name;
    this.username = username;
    this.staffAssigned = staff;
    this.roomID = roomID;

    this.origResID = resID;
    this.origStartTime = startT;
    this.origEndTime = endT;
    this.origDateBook = dateBook;
    this.origDateMade = dateMade;
    this.origName = name;
    this.origUsername = username;
    this.origStaffAssigned = staff;
    this.origRoomID = roomID;
  }

  public ConfReservation(int resID, String strt, String end){
    this.resID = resID;
    this.startTime = strt;
    this.endTime = end;
  }

  public boolean equals(ConfRoom cr) {
    return this.resID == cr.getRoomID();
  }

  public String toString() {
    return "Reservation ID: "
        + resID
        + ", Start Time: "
        + startTime
        + ", End Time: "
        + endTime
        + ", Date Booked: "
        + dateBook
        + ", Date Made: "
        + dateMade
        + ", Name: "
        + name
        + ", Username: "
        + username
        + ", Assigned Staff: "
        + staffAssigned
        + ", Room ID: "
        + roomID;
  }
}
