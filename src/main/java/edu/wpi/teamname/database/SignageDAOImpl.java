package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.SignageDAO;
import edu.wpi.teamname.navigation.Signage;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignageDAOImpl implements SignageDAO {
  /**
   * This method updates an existing Signage object in the "Signage" table in the database with the
   * new Signage object.
   *
   * @param signage the new Signage object to be updated in the "Signage" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void sync(Signage signage) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
              "UPDATE \"Signage\" SET \"shortName\" = ?, \"date\" = ?, \"arrowDirection\" = ?, \"signID\" = ?"
                      + " WHERE \"longName\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, signage.getShortName());
      statement.setTimestamp(2, signage.getDate());
      statement.setString(3, signage.getArrowDirection());
      statement.setInt(4, signage.getSignId());
      statement.setString(5, signage.getLongName());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * The method retrieves all the Signage objects from the "Signage" table in the database.
   *
   * @return an ArrayList of the Signage objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public ArrayList<Signage> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Signage> list = new ArrayList<Signage>();
    try {
      String query = "SELECT * FROM \"Signage\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        String longn = rs.getString("longName");
        String shortn = rs.getString("shortName");
        Timestamp date = rs.getTimestamp("date");
        String arrowDirection = rs.getString("arrowDirection");
        int signId = rs.getInt("signID");
        list.add(new Signage(longn, shortn, date, arrowDirection, signId));
      }
    } catch (SQLException e) {
      System.out.println("Get all Signage error.");
    }
    return list;
  }

  /**
   * This method adds a new Signage object to the "Signage" table in the database.
   *
   * @param signage the Signage object to be added to the "Signage" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void add(Signage signage) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
            "INSERT INTO \"Signage\" (\"longName\", \"shortName\", \"date\", \"arrowDirection\", \"signID\") "
                    + "VALUES (?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, signage.getLongName());
      statement.setString(2, signage.getShortName());
      statement.setTimestamp(3, signage.getDate());
      statement.setString(4, signage.getArrowDirection());
      statement.setInt(5, signage.getSignId());
      statement.executeUpdate();
      System.out.println("Signage information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println(query);
      System.err.println("Error adding Signage information to database: " + e.getMessage());
    }
  }
  /**
   * Deletes the given Signage object from the "Signage" table in the database
   *
   * @param signage the Signage object to be deleted from the "Signage" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void delete(Signage signage) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String del = "DELETE ";
    String sel = "SELECT * ";
    String query = "FROM \"Signage\" WHERE \"signID\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(del + query)) {
      statement.setInt(1, signage.getSignId());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Signage table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(sel + query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Signage information deleted successfully.");
      else System.out.println("Signage information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
  public static void importSignageFromCSV(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String createTableQuery =
              "CREATE TABLE IF NOT EXISTS \"Signage\" ("
                      + "\"longName\" varchar(255),"
                      + "\"shortName\" varchar(255),"
                      + "\"date\" timestamp,"
                      + "\"arrowDirection\" varchar(255)"
                      + "\"signID\" SERIAL PRIMARY KEY,"
                      + ");";
      PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
      createTableStatement.execute();

      String query =
              "INSERT INTO \"Signage\" (\"longName\", \"shortName\", \"date\", \"arrowDirection\", \"signID\") "
                      + "VALUES (?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"Signage\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setString(1, row[0]); // longName is a string column
        statement.setString(2, row[1]); // shortName is a string column
        statement.setTimestamp(3, Timestamp.valueOf(row[2])); // date is a timestamp column
        statement.setString(4, row[3]); // arrowDirection is a string column
        statement.setInt(5, Integer.parseInt(row[4]));
        statement.executeUpdate();
      }
      System.out.println("CSV data imported to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error importing CSV data to PostgreSQL database: " + e.getMessage());
    }
  }

  /**
   * Exports data from a PostgreSQL database table "Signage" to a CSV file
   *
   * @param csvFilePath a String representing the csv data (must use "//" not "/")
   * @throws SQLException if an error occurs while exporting the data from the database
   * @throws IOException if an error occurs while writing the data to the file
   */
  public static void exportSignageToCSV(String csvFilePath) throws SQLException, IOException {
    List<String[]> csvData = new ArrayList<>();
    Connection connection = DataManager.DbConnection();

    try (connection) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Signage\"");

      // add column headers
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      String[] headers = new String[columnCount];
      for (int i = 1; i <= columnCount; i++) {
        headers[i - 1] = metaData.getColumnName(i);
      }
      csvData.add(headers);

      // add data rows
      while (resultSet.next()) {
        String[] row = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
          Object value = resultSet.getObject(i);
          row[i - 1] = value == null ? "" : value.toString();
        }
        csvData.add(row);
      }

      // write data to CSV file
      FileWriter fileWriter = new FileWriter(csvFilePath);
      for (String[] row : csvData) {
        for (int i = 0; i < row.length; i++) {
          fileWriter.append("\"");
          fileWriter.append(row[i].replace("\"", "\"\""));
          fileWriter.append("\"");
          if (i < row.length - 1) {
            fileWriter.append(",");
          }
        }
        fileWriter.append("\n");
      }
      fileWriter.flush();
      fileWriter.close();

      System.out.println("Data exported from PostgreSQL database to CSV file");
    } catch (SQLException | IOException e) {
      System.err.println("Error exporting data from PostgreSQL database: " + e.getMessage());
      throw e;
    }
  }
}
