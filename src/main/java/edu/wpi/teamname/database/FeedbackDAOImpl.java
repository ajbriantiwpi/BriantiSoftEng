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
              + " WHERE id = ? AND \"reporter\" = ? AND \"dateReported\" = ? AND description = ? AND \"assignee\" = ? AND\"status\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, feedback.getReporter());
      statement.setTimestamp(2, feedback.getDateReported());
      statement.setString(3, feedback.getDescription());
      statement.setString(4, feedback.getAssignee());
      statement.setString(5, feedback.getStatus().getStatusString());
      statement.setInt(6, feedback.getId()); // add the id parameter

      // set the remaining parameters
      statement.setString(7, feedback.getReporter());
      statement.setTimestamp(8, feedback.getDateReported());
      statement.setString(9, feedback.getDescription());
      statement.setString(10, feedback.getAssignee());
      statement.setString(11, feedback.getStatus().getStatusString());

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
        int id = rs.getInt("id");
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
        "INSERT INTO \"Feedback\" (id, reporter, \"dateReported\", description, assignee, status) "
            + "VALUES (DEFAULT, ?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement =
          connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, feedback.getReporter());
      statement.setTimestamp(2, feedback.getDateReported());
      statement.setString(3, feedback.getDescription());
      statement.setString(4, feedback.getAssignee());
      statement.setString(5, feedback.getStatus().getStatusString());
      statement.executeUpdate();

      ResultSet rs = statement.getGeneratedKeys();
      if (rs.next()) {
        int id = rs.getInt(1);
        feedback.setId(id);
      }

      System.out.println("Feedback information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding feedback information to database: " + e.getMessage());
    } finally {
      connection.close();
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
    String del = "DELETE ";
    String sel = "SELECT * ";
    String query =
        "FROM \"Feedback\" WHERE id = ? AND reporter = ? AND \"dateReported\" = ? AND description = ? AND assignee = ? AND status = ?";

    try (PreparedStatement statement = connection.prepareStatement(del + query)) {
      statement.setInt(1, feedback.getId()); // add the id parameter
      statement.setString(2, feedback.getReporter());
      statement.setTimestamp(3, feedback.getDateReported());
      statement.setString(4, feedback.getDescription());
      statement.setString(5, feedback.getAssignee());
      statement.setString(6, feedback.getStatus().getStatusString());
      statement.executeUpdate();
      System.out.println("Feedback information has been successfully deleted from the database.");
    } catch (SQLException e) {
      System.err.println("Error deleting feedback information from database: " + e.getMessage());
    } finally {
      connection.close();
    }
  }
}
