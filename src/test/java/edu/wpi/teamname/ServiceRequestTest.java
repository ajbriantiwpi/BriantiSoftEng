package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.navigation.*;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import edu.wpi.teamname.servicerequest.requestitem.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceRequestTest {

  @BeforeEach
  void setup() {
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
  }

  @Test
  public void addItem() throws SQLException {
    ServiceRequest serviceRequest =
        new ServiceRequest(
            50,
            "staff1",
            "patient",
            "location",
            new Timestamp(1),
            new Timestamp(2),
            Status.BLANK,
            "madeBy",
            RequestType.FURNITURE);

    serviceRequest.addItem(1000);
    serviceRequest.addItem(1101);
    serviceRequest.addItem(1304);
    serviceRequest.addItem(1410);

    assertEquals(4, serviceRequest.getItems().size());
  }

  @Test
  public void removeItem() throws SQLException {
    ServiceRequest serviceRequest =
        new ServiceRequest(
            50,
            "staff1",
            "patient",
            "location",
            new Timestamp(1),
            new Timestamp(2),
            Status.BLANK,
            "madeBy",
            RequestType.FURNITURE);

    serviceRequest.addItem(1000);
    serviceRequest.addItem(1101);
    serviceRequest.addItem(1304);
    serviceRequest.addItem(1410);
    serviceRequest.removeItem(1410);
    assertEquals(3, serviceRequest.getItems().size());
  }

  @Test
  public void countItem() throws SQLException {
    ServiceRequest serviceRequest =
        new ServiceRequest(
            50,
            "staff1",
            "patient",
            "location",
            new Timestamp(1),
            new Timestamp(2),
            Status.BLANK,
            "madeBy",
            RequestType.FURNITURE);

    serviceRequest.addItem(1000);
    serviceRequest.addItem(1101);
    serviceRequest.addItem(1304);
    serviceRequest.removeItem(1410);
    serviceRequest.addItem(1304);
    serviceRequest.addItem(1410);
    serviceRequest.addItem(1410);
    serviceRequest.addItem(1410);
    serviceRequest.removeItem(1410);
    assertEquals(2, serviceRequest.countItem(1410));
    assertEquals(2, serviceRequest.countItem(1304));
    assertEquals(1, serviceRequest.countItem(1000));
  }
}
