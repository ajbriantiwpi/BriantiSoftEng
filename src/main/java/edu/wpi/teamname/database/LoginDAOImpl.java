package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LoginDAO;
import java.sql.*;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
  /** */
  @Override
  public void sync(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "UPDATE \"Login\" SET \"password\" = ?" + " WHERE \"username\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, login.getPassword());
      statement.setString(2, login.getUsername());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<Login> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Login> list = new ArrayList<Login>();
    try (connection) {
      String query = "SELECT * FROM \"Login\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        String usern = rs.getString("username");
        String passw = rs.getString("password");
        list.add(new Login(usern, passw));
      }
    } catch (SQLException e) {
      System.out.println("Get all Logins error.");
    }
    return list;
  }

  /** @param login */
  @Override
  public void add(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "INSERT INTO \"Login\" (username, password) " + "VALUES (?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate(query);
      statement = connection.prepareStatement(query);
      statement.setString(1, login.getUsername());
      statement.setString(2, login.getPassword());
      statement.executeUpdate();
      System.out.println("Login information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Login information to database: " + e.getMessage());
    }
  }

  /** @param login */
  @Override
  public void delete(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"Login\" " + "where username = " + login.getUsername();

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Login table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Login information deleted successfully.");
      else System.out.println("Login information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
}
