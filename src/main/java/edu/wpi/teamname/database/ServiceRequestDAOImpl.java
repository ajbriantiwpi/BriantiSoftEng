package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ServiceRequestDAO;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements ServiceRequestDAO {
  /** */
  @Override
  public void sync(ServiceRequest serviceRequest) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"ServiceRequest\" SET \"roomNum\" = ?, \"staffName\" = ?, \"patientName\" = ?, \"requestedAt\" = ?, \"deliverBy\" = ?, \"status\" = ?, \"requestMadeBy\" = ?, \"requestID\" = ?"
              + " WHERE \"requestID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, serviceRequest.getRoomNumber());
      statement.setString(2, serviceRequest.getPatientName());
      statement.setTimestamp(3, serviceRequest.getRequestedAt());
      statement.setTimestamp(4, serviceRequest.getDeliverBy());
      statement.setString(5, serviceRequest.getStatus().getStatusString());
      statement.setString(6, serviceRequest.getRequestMadeBy());
      statement.setInt(7, serviceRequest.getRequestID());
      statement.setInt(8, serviceRequest.getOriginalID());
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
        String requestMadeBy = rs.getString("requestMadeBy");
        list.add(
            new ServiceRequest(
                requestID,
                staffName,
                patientName,
                roomNum,
                deliverBy,
                requestedAt,
                status,
                requestMadeBy));
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
          "INSERT INTO \"ServiceRequest\" (\"requestID\", \"roomNum\", \"staffName\", \"patientName\", \"requestedAt\", \"deliverBy\", \"status\", \"requestMadeBy\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, serviceRequest.getRequestID());
      statement.setString(2, serviceRequest.getRoomNumber());
      statement.setString(3, serviceRequest.getStaffName());
      statement.setString(4, serviceRequest.getPatientName());
      statement.setTimestamp(5, serviceRequest.getRequestedAt());
      statement.setTimestamp(6, serviceRequest.getDeliverBy());
      statement.setString(7, serviceRequest.getStatus().getStatusString());
      statement.setString(8, serviceRequest.getRequestMadeBy());

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
          "SELECT \"quantity\" FROM \"ItemsOrdered\" WHERE \"itemID\" = ? AND \"requestID\" = ?";
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
      String requestMadeBy = rs.getString("requestMadeBy");
      serviceRequest =
          (new ServiceRequest(
              rID, staffName, patientName, roomNum, deliverBy, requestedAt, status, requestMadeBy));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return serviceRequest;
  }

  /**
   * * given an id and a staffname, updates that request's staff name into the new staff name
   *
   * @param requestID the id of the request to update
   * @param staffName the new staff name
   */
  public static void uploadStaffName(int requestID, String staffName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try {
      String query = "UPDATE \"ServiceRequest\" SET \"staffName\" = ? WHERE \"requestID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, staffName);
      statement.setInt(2, requestID);
      statement.executeUpdate();
      connection.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "ServiceRequest"
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadServiceRequestToPostgreSQL(String csvFilePath) throws SQLException, ParseException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String query = "INSERT INTO \"ServiceRequest\" (\"requestID\", \"roomNum\", \"staffName\", \"patientName\", \"requestedAt\", \"deliverBy\", \"status\", \"requestMadeBy\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"ServiceRequest\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setInt(1, Integer.parseInt(row[0])); // requestID is an int column
        statement.setString(2, row[1]); // roomNum is a string column
        statement.setString(3, row[2]); // staffName is a string column
        statement.setString(4, row[3]); // patientName is a string column

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        java.util.Date parsedDate = dateFormat.parse(row[4]);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        statement.setTimestamp(5, timestamp); // requestedAt is a timestamp column

        parsedDate = dateFormat.parse(row[5]);
        timestamp = new java.sql.Timestamp(parsedDate.getTime());
        statement.setTimestamp(6, timestamp); // deliverBy is a timestamp column
        statement.setString(7, row[6]);// status is a string column
        statement.setString(8, row[7]); // requestMadeBy is a string column

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e ) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }

  /**
   * Exports data from a PostgreSQL database table "ServiceRequest" to a CSV file
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while exporting the data from the database
   */
  public static void exportServiceRequestToCSV(String csvFilePath) throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"ServiceRequest\"";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      writer.write("requestID,roomNum,staffName,patientName,requestedAt,deliverBy,status,requestMadeBy");
      while (resultSet.next()) {
        int requestID = resultSet.getInt("requestID");
        String roomNum = resultSet.getString("roomNum");
        String staffName = resultSet.getString("staffName");
        String patientName = resultSet.getString("patientName");
        String requestedAt = resultSet.getTimestamp("requestedAt").toString();
        String deliverBy = resultSet.getTimestamp("deliverBy").toString();
        String status = resultSet.getString("status");
        String requestMadeBy = resultSet.getString("requestMadeBy");

        String row = requestID + "," + roomNum + "," + staffName + "," + patientName + "," + requestedAt + "," + deliverBy + "," + status + "," + requestMadeBy + "\n";
        writer.write(row);
      }
      System.out.println("CSV data downloaded from PostgreSQL database");
    } catch (IOException e) {
      System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
    }
  }
}
