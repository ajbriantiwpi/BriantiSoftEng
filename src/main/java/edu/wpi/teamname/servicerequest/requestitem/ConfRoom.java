package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.servicerequest.ConfReservation;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class ConfRoom {
  @Getter @Setter private int roomID;
  @Getter @Setter private String locationName;
  @Getter @Setter private int seats;
  @Getter @Setter private ArrayList<ConfReservation> reservations;

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

  public ConfRoom() {}

  public void setRes(ArrayList<ConfRoom> c) {}

  public boolean equals(ConfRoom cr) {
    return this.roomID == cr.getRoomID();
  }

  public String toString() {
    return "Room ID: " + roomID + ", Location Name: " + locationName + ", Seats: " + seats;
  }

  public boolean checkAvailable(String start, String end, Timestamp date) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    for (ConfReservation reservation : reservations) {
      if (reservation.getDateBook().getDate() == date.getDate()
          && reservation.getDateBook().getMonth() == date.getMonth()) {
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        Date resStart = format.parse(reservation.getStartTime());
        Date resEnd = format.parse(reservation.getEndTime());
        if (startDate.after(resStart) && startDate.before(resEnd)) { // start inside res
          return false;
        } else if (endDate.after(resStart) && endDate.before(resEnd)) { // end inside res
          return false;
        } else if (resStart.after(startDate)
            && resStart.before(endDate)) { // res start inside selected
          return false;
        } else if (resEnd.after(startDate) && resEnd.before(endDate)) { // res end inside selected
          return false;
        }
      }
    }
    return true;
  }
}
