package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ServiceRequestDAO;
import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;

import java.sql.*;
import java.util.ArrayList;

public class ServiceRequestDAOImpl implements ServiceRequestDAO {
  /** */
  @Override
  public void sync() {}

  /** @return */
  @Override
  public ArrayList<ServiceRequest> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<ServiceRequest> list = new ArrayList<ServiceRequest>();

    try (connection) {
      String query = "SELECT * FROM \"ServiceRequest\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        String roomNum = rs.getString("roomNum");
        String staffName = rs.getString("staffName");
        String patientName = rs.getString("patientName");
        Timestamp requestedAt = rs.getTimestamp("requestedAt");
        Timestamp deliverBy = rs.getTimestamp("deliverBy");
        Status status = Status.valueOf(rs.getString("status"));
        list.add(new ServiceRequest(requestID, roomNum, staffName, patientName, requestedAt, deliverBy, status));
      }
    }
    return list;

  }

  /** @param type */
  @Override
  public void add(ServiceRequest type) {}

  /** @param type */
  @Override
  public void delete(ServiceRequest type) {}
}
