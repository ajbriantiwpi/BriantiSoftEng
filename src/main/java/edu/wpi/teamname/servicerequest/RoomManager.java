package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class RoomManager {
  @Getter public ArrayList<ConfRoom> rooms;
  public ArrayList<ConfReservation> reservations;

  @Getter @Setter String start;
  @Getter @Setter String end;

  @Getter @Setter String building;

  public RoomManager() {
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

  public ArrayList<ConfRoom> getViableRooms(Timestamp date) {
    ArrayList<ConfRoom> viableRooms = new ArrayList<>();
    for (ConfRoom room : rooms) {
      if (isViableRoom(room, date)) {
        viableRooms.add(room);
      }
    }
    return viableRooms;
  }

  public boolean isViableRoom(ConfRoom room, Timestamp date) {
    boolean timeOK = timeCheck(room, date);

    boolean buildingOK = buildingCheck(room, date);
    boolean ok = timeOK && buildingOK;
    System.out.println(timeOK);
    return ok;
  }

  private boolean buildingCheck(ConfRoom room, Timestamp date) {
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
}
