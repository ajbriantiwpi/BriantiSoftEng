package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ServiceRequestDAO;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import java.sql.*;
import java.util.ArrayList;

public class ServiceRequestDAOImpl implements ServiceRequestDAO {
  /** */
  @Override
  public void sync(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"ServiceRequest\" SET \"roomNum\" = ?, \"staffName\" = ?, \"patientName\" = ?, \"requestedAt\" = ?, \"deliverBy\" = ?, \"status\" = ?"
              + " WHERE \"requestID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, serviceRequest.getRoomNumber());
      statement.setString(2, serviceRequest.getPatientName());
      statement.setTimestamp(3, serviceRequest.getRequestedAt());
      statement.setTimestamp(4, serviceRequest.getDeliverBy());
      statement.setString(5, serviceRequest.getStatus().getStatusString());
      statement.setInt(6, serviceRequest.getRequestID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

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
        list.add(
            new ServiceRequest(
                requestID, staffName, patientName, roomNum, deliverBy, requestedAt, status));
      }
    }
    connection.close();
    return list;
  }

  /** @param serviceRequest */
  @Override
  public void add(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try {
      String query =
          "INSERT INTO \"ServiceRequest\" (\"requestID\", \"roomNum\", \"staffName\", \"patientName\", \"requestedAt\", \"deliverBy\", \"status\") VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.setString(2, serviceRequest.getRoomNumber());
      statement.setString(3, serviceRequest.getStaffName());
      statement.setString(4, serviceRequest.getPatientName());
      statement.setTimestamp(5, serviceRequest.getRequestedAt());
      statement.setTimestamp(6, serviceRequest.getDeliverBy());
      statement.setString(7, serviceRequest.getStatus().getStatusString());

      statement.executeUpdate();
      // ItemsOrdered
      ArrayList<RequestItem> items = serviceRequest.getItems();
      for (int i = 0; i < items.size(); i++) {
        connection = DataManager.DbConnection();
        int newQuantity = getQuantity(serviceRequest.getRequestID(), items.get(i).getItemID()) + 1;
        try {

          if (newQuantity == 1) {
            query =
                "INSERT INTO \"ItemsOrdered\" (\"requestID\", \"itemID\", \"quantity\") "
                    + "VALUES (?, ?, 1)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, serviceRequest.getRequestID());
            statement.setInt(2, items.get(i).getItemID());
          } else {
            query =
                "UPDATE \"ItemsOrdered\" SET quantity = ? WHERE \"itemID\" = ? AND \"requestID\" = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, newQuantity);
            statement.setInt(2, items.get(i).getItemID());
            statement.setInt(3, serviceRequest.getRequestID());
          }
          statement = connection.prepareStatement(query);
          statement.executeUpdate();
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  public int getQuantity(int requestID, int itemID) throws SQLException {
    Connection connection = DataManager.DbConnection();

    int quantity = 0;
    try (connection) {
      String query =
          "SELECT \"quantity\" FROM \"ItemsOrdered\" WHERE \"itemID\" = "
              + itemID
              + " AND \"requestID\" = "
              + requestID;
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        quantity = rs.getInt("quantity");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    if (quantity > 0) {
      return quantity + 1;
    } else {
      return 1;
    }
  }

  /** @param serviceRequest */
  @Override
  public void delete(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "DELETE FROM \"ServiceRequest\" WHERE \"requestID\" = ?";
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("ServiceRequest information deleted successfully.");
      else System.out.println("ServiceRequest information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
    connection.close();
  }

  public void deleteWithItems(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "DELETE FROM \"ServiceRequest\" WHERE \"requestID\" = ?";
    try (connection) {
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
    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("ServiceRequest information deleted successfully.");
      else System.out.println("ServiceRequest information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
    connection.close();
  }

  public static ServiceRequest getServiceRequest(int id) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"ServiceRequest\" WHERE \"requestID\" = ?";
    ServiceRequest serviceRequest = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();

      int rID = rs.getInt("mealID");
      String roomNum = rs.getString("roomNum");
      String staffName = rs.getString("Meal");
      String patientName = rs.getString("Cuisine");
      Timestamp requestedAt = rs.getTimestamp("requestedAt");
      Timestamp deliverBy = rs.getTimestamp("deliverBy");
      Status status = Status.valueOf(rs.getString("status"));
      serviceRequest = (new ServiceRequest(rID, staffName, patientName, roomNum, deliverBy, requestedAt, status));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return serviceRequest;
  }
}
