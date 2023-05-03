package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.servicerequest.requestitem.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class ServiceRequest {
  @Getter @Setter private int requestID;
  @Setter @Getter private String staffName;
  @Setter @Getter private String patientName;

  @Setter @Getter private String roomNumber;

  @Setter @Getter private Timestamp deliverBy;
  @Setter @Getter private Timestamp requestedAt;

  @Setter @Getter private Status status;
  @Setter @Getter private String requestMadeBy;
  @Getter private final int originalID;
  @Getter private RequestType requestType;
  @Getter private ArrayList<RequestItem> items;

  public ServiceRequest(
      int requestID,
      String staffName,
      String patientName,
      String roomNumber,
      Timestamp deliverBy,
      Timestamp requestedAt,
      Status status,
      String requestMadeBy,
      RequestType requestType) {
    this.requestID = requestID;
    this.staffName = staffName;
    this.patientName = patientName;
    this.roomNumber = roomNumber;
    this.deliverBy = deliverBy;
    this.requestedAt = requestedAt;
    this.status = status;
    this.requestMadeBy = requestMadeBy;
    this.originalID = requestID;
    this.requestType = requestType;
    items = new ArrayList<RequestItem>();
  }

  /**
   * Adds an item to the list of items for this service request based on this request type
   *
   * @param requestID the ID of the item to be added to the list
   * @throws SQLException if there is an error accessing the database
   */
  public void addItem(int requestID) throws SQLException {
    if (requestType.equals(RequestType.FLOWER)) {
      items.add(new Flower(requestID));
    } else if (requestType.equals(RequestType.MEAL)) {
      items.add(new Meal(requestID));
    } else if (requestType.equals(RequestType.FURNITURE)) {
      items.add(new Furniture(requestID));
    } else if (requestType.equals(RequestType.OFFICESUPPLY)) {
      items.add(new OfficeSupply(requestID));
    } else if (requestType.equals(RequestType.MEDICALSUPPLY)) {
      int aLvl = 3;
      items.add(new MedicalSupply(requestID, aLvl));
    } else if (requestType.equals(RequestType.PHARMACEUTICAL)) {
      items.add(new Pharmaceutical(requestID));
    }
  }

  /**
   * Removes an item from the list of items for this service request.
   *
   * @param requestID the ID of the item to be removed
   */
  public void removeItem(int requestID) {
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).getItemID() == requestID) {
        items.remove(i);
        return;
      }
    }
  }

  /**
   * * Returns specific details of this service request
   *
   * @return
   */
  public String getDetails() {
    //    return "["
    //        + requestID
    //        + ", "
    //        + staffName
    //        + ", "
    //        + patientName
    //        + ", "
    //        + roomNumber
    //        + ", "
    //        + deliverBy
    //        + ", "
    //        + requestedAt
    //        + ", "
    //        + status.toString()
    //        + ", "
    //        + requestMadeBy
    //        + ", "
    //        + requestType
    //        + "]";
    return "Request ID: "
        + String.valueOf(requestID)
        + "\tPatient Name: "
        + patientName
        + "\tRoom Name: "
        + roomNumber
        + "\tDeliver By: "
        + deliverBy.toString();
  }

  /**
   * Takes an id of a RequestItem and gives the number of instances of that RequestItem this order
   * has
   *
   * @param id id of RequestItem to be counted
   * @return number of RequestItem with the id in this order
   */
  public int countItem(int id) {
    int i = 0;
    for (RequestItem item : items) {
      if (item.getItemID() == id) {
        i++;
      }
    }
    return i;
  }
}
