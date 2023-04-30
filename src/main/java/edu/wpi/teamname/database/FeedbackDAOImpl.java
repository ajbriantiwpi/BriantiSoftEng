package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.FeedbackDAO;
import edu.wpi.teamname.employees.Feedback;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.*;
import java.util.ArrayList;

public class FeedbackDAOImpl implements FeedbackDAO {
  /**
   * This method updates an existing Move object in the "Move" table in the database with the new
   * Move object.
   *
   * @param feedback the new Feedback object to be updated in the "Feedback" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void sync(Feedback feedback) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Feedback\" SET \"reporter\" = ?, \"dateReported\" = ?, description = ?, assignee=?, status=?"
              + " WHERE \"reporter\" = ? AND \"dateReported\" = ? AND description = ? AND \"assignee\" = ? AND\"status\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, feedback.getReporter());
      statement.setTimestamp(2, feedback.getDateReported());
      statement.setString(3, feedback.getDescription());
      statement.setString(4, feedback.getAssignee());
      statement.setString(5, feedback.getStatus().getStatusString());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * The method retrieves all the Feedback objects from the "Feedback" table in the database.
   *
   * @return an ArrayList of the Feedback objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public ArrayList<Feedback> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Feedback> list = new ArrayList<Feedback>();
    try (connection) {
      String query = "SELECT * FROM \"Feedback\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        String reporter = rs.getString("reporter");
        Timestamp date = rs.getTimestamp("dateReported");
        String description = rs.getString("description");
        String assignee = rs.getString("assignee");
        Status status = Status.valueOf(rs.getString("status"));
        list.add(new Feedback(reporter, description));
      }
    } catch (SQLException e) {
      System.out.println("Get all nodes error.");
    }
    return list;
  }

  /**
   * This method adds a new Feedback object to the "Feedback" table in the database.
   *
   * @param feedback the Feedback object to be added to the "Feedback" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void add(Feedback feedback) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"Feedback\" (reporter, \"dateReported\", description, assignee, status) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, feedback.getReporter());
      statement.setTimestamp(2, feedback.getDateReported());
      statement.setString(3, feedback.getDescription());
      statement.setString(4, feedback.getAssignee());
      statement.setString(5, feedback.getStatus().getStatusString());
      statement.executeUpdate();
      System.out.println("Feedback information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding feedback information to database: " + e.getMessage());
    }
  }

  /**
   * This method deletes the given Feedback object from the database
   *
   * @param feedback the Feedback object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void delete(Feedback feedback) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String del = "Delete ";
    String sel = "Select * ";
    String query =
        "from \"Move\" where reporter = ?, \"dateReported\" = ?, description = ?, assignee=?, status=?";

    try (PreparedStatement statement = connection.prepareStatement(del + query)) {
      statement.setString(1, feedback.getReporter());
      statement.setTimestamp(2, feedback.getDateReported());
      statement.setString(3, feedback.getDescription());
      statement.setString(4, feedback.getAssignee());
      statement.setString(5, feedback.getStatus().getStatusString());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Feedback table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(sel + query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Feedback information deleted successfully.");
      else System.out.println("Feedback information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
}
