package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.MoveDAO;
import edu.wpi.teamname.navigation.Move;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MoveDAOImpl implements MoveDAO {

  /**
   * Sync an ORM with its row in the database WARNING: do not create a new node just change the
   * parameters on the old one
   */
  @Override
  public void sync(Move move) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Move\" SET \"nodeID\" = ?, \"longName\" = ?, date = ?"
              + " WHERE \"nodeID\" = ? AND \"longName\" = ? AND date = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, move.getNodeID());
      statement.setString(2, move.getLongName());
      statement.setTimestamp(3, move.getDate());
      statement.setInt(4, move.getOriginalNodeID());
      statement.setString(5, move.getOriginalLongName());
      statement.setTimestamp(6, Timestamp.valueOf(move.getOriginalDate()));

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<Move> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Move> list = new ArrayList<Move>();
    try (connection) {
      String query = "SELECT * FROM \"Move\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("nodeID");
        String longn = rs.getString("longName");
        Timestamp date = rs.getTimestamp("date");
        list.add(new Move(id, longn, date));
      }
    } catch (SQLException e) {
      System.out.println("Get all nodes error.");
    }
    return list;
  }

  /** @param move */
  @Override
  public void add(Move move) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"Move\" (\"nodeID\", \"longName\", \"date\") " + "VALUES (?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, move.getNodeID());
      statement.setString(2, move.getLongName());
      statement.setTimestamp(3, move.getDate());
      statement.executeUpdate();
      System.out.println("Node information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Move information to database: " + e.getMessage());
    }
  }

  /** @param move */
  @Override
  public void delete(Move move) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "Delete from \"Move\" where \"nodeID\" = ? AND \"longName\" = ? AND \"date\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, move.getNodeID());
      statement.setString(2, move.getLongName());
      statement.setTimestamp(3, move.getDate());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Move table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Move information deleted successfully.");
      else System.out.println("Move information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
  /**
   * Uploads CSV data to a PostgreSQL database table "Move"
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadMoveToPostgreSQL(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String query = "INSERT INTO \"Move\" (\"nodeID\", \"longName\", \"date\") VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"Move\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setInt(1, Integer.parseInt(row[0])); // nodeId is an int column
        statement.setString(2, row[1]); // longName is a string column
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parsedDate = dateFormat.parse(row[2]);
        java.sql.Date date = new java.sql.Date(parsedDate.getTime());
        statement.setDate(3, date); // date is a date column

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * Exports data from a PostgreSQL database table "Move" to a CSV file
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while exporting the data from the database
   */
  public static void exportMoveToCSV(String csvFilePath) throws SQLException {
    Connection connection = DataManager.DbConnection();

    try (connection) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Move\"");

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
