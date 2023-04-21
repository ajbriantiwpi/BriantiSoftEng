package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.PathMessageDAO;
import edu.wpi.teamname.navigation.PathMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PathMessageDAOImpl implements PathMessageDAO {

  /**
   * This method updates an existing PathMessage object in the "PathMessages" table in the database
   * with the new PathMessage object.
   *
   * @param pm the new PathMessage object to be updated in the "PathMessages" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void sync(PathMessage pm) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"PathMessages\" SET \"startNode\" = ?, \"endNode\" = ?, \"algorithm\" = ?"
              + " WHERE \"startNode\" = ? AND \"endNode\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, pm.getStartNodeID());
      statement.setInt(2, pm.getEndNodeID());
      statement.setString(3, pm.getAlgorithm());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * The method retrieves all the PathMessage objects from the "PathMessages" table in the database.
   *
   * @return an ArrayList of the PathMessage objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public ArrayList<PathMessage> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<PathMessage> list = new ArrayList<PathMessage>();

    try (connection) {
      String query = "SELECT * FROM \"PathMessages\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        int startNode = rs.getInt("startNode");
        int endNode = rs.getInt("endNode");
        String alg = rs.getString("algorithm");
        Timestamp date = rs.getTimestamp("date");
        int adminID = rs.getInt("adminID");
        String message = rs.getString("message");
        list.add(new PathMessage(startNode, endNode, alg, date, adminID, message));
      }
    }
    connection.close();
    return list;
  }

  /**
   * This method adds a new PathMessage object to the "PathMessages" table in the database.
   *
   * @param pm the PathMessage object to be added to the "PathMessages" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void add(PathMessage pm) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "INSERT INTO \"PathMessages\" (\"startNode\", \"endNode\", algorithm, \"date\", \"adminID\", message) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, pm.getStartNodeID()); // startNode is a int column
      statement.setInt(2, pm.getEndNodeID()); // endNode is a int column
      statement.setString(3, pm.getAlgorithm()); // algorithm used
      statement.setTimestamp(4, pm.getDate()); // date
      statement.setInt(5, pm.getAdminID()); // adminID
      statement.setString(6, pm.getMessage()); // message

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * This method deletes the given PathMessage object from the database
   *
   * @param pm the PathMessage object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void delete(PathMessage pm) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String del = "Delete ";
    String sel = "Select * ";
    String query =
        "FROM \"PathMessages\" WHERE \"startNode\" = ? AND \"endNode\" = ?, AND \"algorithm\" = ?";
    try (connection) {

      PreparedStatement statement = connection.prepareStatement(del + query);
      statement.setInt(1, pm.getStartNodeID());
      statement.setInt(2, pm.getEndNodeID());
      statement.setString(3, pm.getAlgorithm());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(sel + query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("PathMessage information deleted successfully.");
      else System.out.println("PathMessage information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
    connection.close();
  }

  /**
   * This method retrieves an PathMessage object with the specified ID from the "PathMessages" table
   * in the database.
   *
   * @param id the ID of the PathMessage object to retrieve from the "PathMessages" table
   * @return the PathMessage object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<PathMessage> getPathMessage(int startNode, int endNode, String algr)
      throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<PathMessage> pm = new ArrayList<>();
    String query =
        "SELECT * FROM \"PathMessages\" WHERE \"startNode\" = ? AND \"endNode\" = ? AND algorithm = ?";
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, startNode);
      statement.setInt(2, endNode);
      statement.setString(3, algr);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int sNode = rs.getInt("startNode");
        int eNdoe = rs.getInt("endNode");
        String alg = rs.getString("algorithm");
        Timestamp date = rs.getTimestamp("date");
        int adminID = rs.getInt("adminID");
        String message = rs.getString("message");
        pm.add((new PathMessage(sNode, eNdoe, alg, date, adminID, message)));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return pm;
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "PathMessagea"-also creates one if one does not
   * exist
   *
   * @param csvFilePath a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadPMToPostgreSQL(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    csvData = DataManager.parseCSVAndUploadToPostgreSQL(csvFilePath);
    try (connection) {
      String createTableQuery =
          "CREATE TABLE IF NOT EXISTS \"PathMessages\" (\"startNode\" INTEGER, \"endNode\" INTEGER, algorithm VARCHAR, \"date\" timestamp, \"adminID\" INTEGER, message VARCHAR);";
      PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
      createTableStatement.executeUpdate();

      String query =
          "INSERT INTO \"PathMessages\" (\"startNode\", \"endNode\", algorithm, \"date\", \"adminID\", message) "
              + "VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"PathMessages\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setInt(1, Integer.parseInt(row[0])); // startNode is a int column
        statement.setInt(2, Integer.parseInt(row[1])); // endNode is a int column
        statement.setString(3, row[2]); // alg
        statement.setTime(4, Time.valueOf(row[3])); // date
        statement.setInt(5, Integer.parseInt(row[4])); // admin
        statement.setString(6, (row[5])); // message

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }

  /**
   * This method exports all the PathMessage objects from the "PathMessages" table in the database
   * to a CSV file at the specified file path.
   *
   * @param csvFilePath the file path of the CSV file to export the PathMessage objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportPMToCSV(String csvFilePath) throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();

    try (connection) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM \"PathMessages\"");

      // add column headers
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      StringBuilder headerBuilder = new StringBuilder();
      for (int i = 1; i <= columnCount; i++) {
        headerBuilder.append(metaData.getColumnName(i));
        if (i < columnCount) {
          headerBuilder.append(",");
        }
      }
      String header = headerBuilder.toString();

      // add data rows
      StringBuilder dataBuilder = new StringBuilder();
      while (resultSet.next()) {
        for (int i = 1; i <= columnCount; i++) {
          Object value = resultSet.getObject(i);
          if (value instanceof java.sql.Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataBuilder.append(dateFormat.format(value));
          } else {
            dataBuilder.append(value == null ? "" : value.toString());
          }
          if (i < columnCount) {
            dataBuilder.append(",");
          }
        }
        dataBuilder.append("\n");
      }

      // write data to CSV file
      FileWriter fileWriter = new FileWriter(csvFilePath);
      fileWriter.write(header + "\n" + dataBuilder.toString());
      fileWriter.close();

      System.out.println("Data exported from PostgreSQL database to CSV file");
    } catch (SQLException | IOException e) {
      System.err.println("Error exporting data from PostgreSQL database: " + e.getMessage());
    }
  }
}
