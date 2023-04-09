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

  /** @param serviceRequest */
  @Override
  public void add(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "INSERT INTO \"ServiceRequest\" (\"requestID\", \"roomNum\", \"staffName\", \"patientName\", \"requestedAt\", \"deliverBy\", \"status\") VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.setString(2, serviceRequest.getRoomNumber());
      statement.setString(3, serviceRequest.getStaffName());
      statement.setString(4, serviceRequest.getPatientName());
      statement.setTimestamp(5, serviceRequest.getRequestedAt());
      statement.setTimestamp(6, serviceRequest.getDeliverBy());
      statement.setString(7, serviceRequest.getStatus().getStatusString());

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  /** @param serviceRequest */
  @Override
  public void delete(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "DELETE FROM \"ServiceRequest\" WHERE \"requestID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  public void deleteWithItems(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "DELETE FROM \"ServiceRequest\" WHERE \"requestID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.executeUpdate();
      query = "DELETE FROM \"ItemsOrdered\" WHERE \"requestID\" = ?";
      statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
