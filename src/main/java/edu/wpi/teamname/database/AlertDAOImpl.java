package edu.wpi.teamname.database;

import edu.wpi.teamname.alerts.Alert;
import edu.wpi.teamname.database.interfaces.AlertDAO;
import edu.wpi.teamname.employees.EmployeeType;
import java.sql.*;
import java.util.ArrayList;

public class AlertDAOImpl implements AlertDAO {
  @Override
  public void sync(Alert alert) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try {
      String query =
          "UPDATE \"Alert\" SET \"alertID\" = ?, \"startDate\" = ?, \"endDate\" = ?, \"creator\" = ?, \"description\" = ?, \"announcement\" = ?, \"employeeType\" = ?, \"urgency\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setLong(1, alert.getId());
      statement.setTimestamp(2, alert.getStartDisplayDate());
      statement.setTimestamp(3, alert.getEndDisplayDate());
      statement.setString(4, alert.getCreator());
      statement.setString(5, alert.getDescription());

      statement.setString(6, alert.getAnnouncement());
      statement.setString(7, alert.getType().getString());
      statement.setString(8, alert.getUrgency().getString());

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  @Override
  public ArrayList<Alert> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Alert> list = new ArrayList<Alert>();

    try (connection) {
      String query = "SELECT * FROM \"Alert\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int alertID = rs.getInt("alertID");
        Timestamp startDate = rs.getTimestamp("startDate");
        Timestamp endDate = rs.getTimestamp("endDate");
        String creator = rs.getString("creator");
        String description = rs.getString("description");
        String announcement = rs.getString("announcement");
        EmployeeType employeeType = EmployeeType.valueOf(rs.getString("employeeType"));
        Alert.Urgency urgency = Alert.Urgency.valueOf(rs.getString("urgency"));

        list.add(
            new Alert(
                alertID,
                startDate,
                endDate,
                creator,
                description,
                announcement,
                employeeType,
                urgency));
      }
    }
    connection.close();
    return list;
  }

  @Override
  public void add(Alert alert) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try {
      String query =
          "INSERT INTO \"Alert\" (\"alertID\", \"startDate\", \"endDate\", \"creator\", \"description\", \"announcement\", \"employeeType\", \"urgency\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setLong(1, alert.getId());
      statement.setTimestamp(2, alert.getStartDisplayDate());
      statement.setTimestamp(3, alert.getEndDisplayDate());
      statement.setString(4, alert.getCreator());
      statement.setString(5, alert.getDescription());

      statement.setString(6, alert.getAnnouncement());
      statement.setString(7, alert.getType().getString());
      statement.setString(8, alert.getUrgency().getString());

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  public ArrayList<Integer> getAllIDs() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Integer> list = new ArrayList<Integer>();

    try (connection) {
      String query = "SELECT * FROM \"Alert\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int alertID = rs.getInt("alertID");
        list.add((alertID));
      }
    }
    connection.close();
    return list;
  }

  @Override
  public void delete(Alert alert) throws SQLException {}

  /**
   * * Creates a table for storing Alert data if it doesn't already exist
   *
   * @throws SQLException if connection to the database fails
   */
  public static void createTable() throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "create table if not exists \"Alert\"\n"
              + "(\n"
              + "    \"alertID\"      integer,\n"
              + "    \"startDate\"    timestamp,\n"
              + "    \"endDate\"      timestamp,\n"
              + "    creator        varchar(16),\n"
              + "    description    varchar(256),\n"
              + "    announcement   varchar(64),\n"
              + "    \"employeeType\" varchar(20),\n"
              + "    urgency        varchar(10)\n"
              + ");";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
}
