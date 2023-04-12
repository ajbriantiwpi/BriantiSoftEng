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
   * Adds an item to the list of items for this service request based on the provided request ID. If
   * the request ID is less than 1100, a new Flower object will be created and added to the list. If
   * the request ID is less than 1200, a new Meal object will be created and added to the list. If
   * the request ID is less than 1400, a new Furniture object will be created and added to the list.
   * If the request ID is less than 1500, a new OfficeSupply object will be created and added to the
   * list.
   *
   * @param requestID the ID of the item to be added to the list
   * @throws SQLException if there is an error accessing the database
   */
  public void addItem(int requestID) throws SQLException {
    if (requestID < 1100) {
      items.add(new Flower(requestID));
    } else if (requestID < 1200) {
      items.add(new Meal(requestID));
    } else if (requestID < 1400) {
      items.add(new Furniture(requestID));
    } else if (requestID < 1500) {
      items.add(new OfficeSupply(requestID));
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

  public String toString() {
    return "["
        + requestID
        + ", "
        + staffName
        + ", "
        + patientName
        + ", "
        + roomNumber
        + ", "
        + deliverBy
        + ", "
        + requestedAt
        + ", "
        + status.toString()
        + ", "
        + requestMadeBy
        + ", "
        + requestType
        + "]";
  }
}
