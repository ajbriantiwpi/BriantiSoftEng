package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

public class RoomManager {
  @Getter public ArrayList<ConfRoom> rooms;
  public ArrayList<ConfReservation> reservations;

  @Getter @Setter String start;
  @Getter @Setter String end;

  @Getter @Setter DoubleProperty high;

  @Getter @Setter DoubleProperty low;

  @Getter @Setter String building;

  public RoomManager() {
    high = new SimpleDoubleProperty(100.0);
    low = new SimpleDoubleProperty(0);
    rooms = new ArrayList<>();
    reservations = new ArrayList<>();
    try {
      rooms.addAll(DataManager.getAllConfRoom());
      for (ConfRoom room : rooms) {
        room.setReservations(DataManager.getResForRoom(room));
      }
    } catch (SQLException e) {
      System.out.println(e);
    }

    this.start = "";
    this.end = "";
    this.building = "";
  }

  /**
   * Returns a list of rooms that pass through all filters
   *
   * @param date date to check on
   * @return list of the rooms that pass through the filters
   */
  public ArrayList<ConfRoom> getViableRooms(Timestamp date) {
    ArrayList<ConfRoom> viableRooms = new ArrayList<>();
    for (ConfRoom room : rooms) {
      if (isViableRoom(room, date)) {
        viableRooms.add(room);
      }
    }
    return viableRooms;
  }

  /**
   * Puts a room through a list of filter checks to see if its viable
   *
   * @param room
   * @param date date to check on
   * @return true if room passes all checks
   */
  public boolean isViableRoom(ConfRoom room, Timestamp date) {
    boolean timeOK = timeCheck(room, date);
    boolean buildingOK = buildingCheck(room);
    boolean sizeOK = sizeCheck(room);
    boolean ok = timeOK && buildingOK && sizeOK;
    System.out.println(timeOK);
    return ok;
  }

  /**
   * Checks if a room is in the building
   *
   * @param room room to check
   * @return true if room is in the selected building
   */
  private boolean buildingCheck(ConfRoom room) {
    if (building.equals("None")) {
      return true;
    }
    if (!building.equals("")) {
      if (room.getLocationName().split(",")[2].strip().equals(building.strip())) {
        return true;
      } else {
        System.out.println("Building Fail");
        return false;
      }
    } else {
      return true;
    }
  }

  /**
   * Checks if a room is availible for a certain time span on a specific date
   *
   * @param room room to check
   * @param date date to check for availiblity
   * @return true if room is availble for selected date and time
   */
  private boolean timeCheck(ConfRoom room, Timestamp date) {
    try {
      //      System.out.println(start);
      //      System.out.println(end);
      if (start.contains(":") && end.contains(":")) {
        try {
          if (room.checkAvailable(start, end, date)) {
            return true;
          } else {
            System.out.println("Time Fail");
            return false;
          }
        } catch (ParseException e) {
          System.out.println("Parse Exception");
          return true;
        }
      } else {
        return true;
      }
    } catch (NullPointerException e) {
      return true;
    }
  }

  /**
   * checks if room is within specified size constraints
   *
   * @param room room to check
   * @return true if room's seats is between low and high seats
   */
  private boolean sizeCheck(ConfRoom room) {
    return (room.getSeats() >= low.get() && room.getSeats() <= high.get());
  }
}
