package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.servicerequest.requestitem.RequestItem;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
  @Getter private ArrayList<RequestItem> items;

  public ServiceRequest(
      int requestID,
      String staffName,
      String patientName,
      String roomNumber,
      Timestamp deliverBy,
      Timestamp requestedAt,
      Status status) {
    this.requestID = requestID;
    this.staffName = staffName;
    this.patientName = patientName;
    this.roomNumber = roomNumber;
    this.deliverBy = deliverBy;
    this.requestedAt = requestedAt;
    this.status = status;
    items = new ArrayList<RequestItem>();
  }

  public void addItem(int requestID) {
    return;
  }

  public void removeItem(int requestID) {
    return;
  }
}
