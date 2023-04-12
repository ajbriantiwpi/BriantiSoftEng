package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LoginDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {
  /**
   * This method updates an existing Login object in the "Login" table in the database with the new
   * Login object.
   *
   * @param login the new Login object to be updated in the "Login" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void sync(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Login\" SET \"password\" = ?, \"username\" = ? WHERE \"username\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, login.getPassword());
      statement.setString(2, login.getUsername());
      statement.setString(3, login.getOriginalUsername());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * The method retrieves all the Login objects from the "Login" table in the database.
   *
   * @return an ArrayList of the Login objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method adds a new Login object to the "Login" table in the database.
   *
   * @param login the Login object to be added to the "Login" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void add(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "INSERT INTO \"Login\" (username, password) " + "VALUES (?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, login.getUsername());
      statement.setString(2, login.getPassword());
      statement.executeUpdate();
      System.out.println("Login information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Login information to database: " + e.getMessage());
    }
  }

  /**
   * This method deletes the given Login object from the database
   *
   * @param login the Login object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void delete(Login login) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"Login\" where username = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, login.getUsername());
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

  /**
   * This method retrieves a Login object with the specified username from the "Login" table in the
   * database.
   *
   * @param username the username of the Login object to retrieve from the "Login" table
   * @return the Login object with the specified username, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static Login getLogin(String username) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Login\" WHERE \"username\" = ?";
    Login login = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, username);
      ResultSet rs = statement.executeQuery();

      String user = rs.getString("username");
      String pass = rs.getString("password");
      login = (new Login(user, pass));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return login;
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Login"
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadLoginToPostgreSQL(String csvFilePath)
      throws SQLException, ParseException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String query = "INSERT INTO \"Login\" (\"username\", \"password\") VALUES (?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"Login\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setString(1, row[0]); // username is a string column
        statement.setString(2, row[1]); // password is a string column
        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }

  /**
   * Exports data from a PostgreSQL database table "Login" to a CSV file
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while exporting the data from the database
   */
  public static void exportLoginToCSV(String csvFilePath) throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Login\"";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      writer.write("username,password\n");
      while (resultSet.next()) {
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        String row = username + "," + password + "\n";
        writer.write(row);
      }
      System.out.println("CSV data downloaded from PostgreSQL database");
    } catch (IOException e) {
      System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
    }
  }
}
