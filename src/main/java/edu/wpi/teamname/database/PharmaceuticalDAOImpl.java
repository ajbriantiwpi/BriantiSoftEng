package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.PharmaceuticalDAO;
import edu.wpi.teamname.servicerequest.requestitem.Pharmaceutical;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PharmaceuticalDAOImpl implements PharmaceuticalDAO {
  /**
   * This method updates an existing Pharmaceutical object in the "Pharmaceutical" table in the
   * database with the new Pharmaceutical object.
   *
   * @param pharmaceutical the new Pharmaceutical object to be updated in the "Pharmaceutical" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void sync(Pharmaceutical pharmaceutical) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Pharmaceutical\" SET \"pharmaceuticalID\" = ?, \"name\" = ?, \"price\" = ?, \"category\" = ?, \"dosage\" = ?"
              + " WHERE \"pharmaceuticalID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, pharmaceutical.getItemID());
      statement.setString(2, pharmaceutical.getName());
      statement.setFloat(3, pharmaceutical.getPrice());
      statement.setString(4, pharmaceutical.getCategory());
      statement.setInt(5, pharmaceutical.getDosage());
      statement.setInt(6, pharmaceutical.getOriginalID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /**
   * The method retrieves all the Pharmaceutical objects from the "Pharmaceutical" table in the
   * database.
   *
   * @return an ArrayList of the Pharmaceutical objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public ArrayList<Pharmaceutical> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Pharmaceutical> list = new ArrayList<Pharmaceutical>();

    try (connection) {
      String query = "SELECT * FROM \"Pharmaceutical\" ORDER BY name";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("pharmaceuticalID");
        String name = rs.getString("name");
        float price = rs.getFloat("price");
        String category = rs.getString("category");
        int dosage = rs.getInt("dosage");
        list.add(new Pharmaceutical(id, name, price, category, dosage));
      }
    }
    connection.close();
    return list;
  }

  /**
   * This method adds a new Pharmaceutical object to the "Pharmaceutical" table in the database.
   *
   * @param pharmaceutical the Pharmaceutical object to be added to the "Pharmaceutical" table
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void add(Pharmaceutical pharmaceutical) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"Pharmaceutical\" (\"pharmaceuticalID\", \"name\", \"price\", \"category\", \"dosage\") "
            + "VALUES (?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, pharmaceutical.getItemID());
      statement.setString(2, pharmaceutical.getName());
      statement.setFloat(3, pharmaceutical.getPrice());
      statement.setString(4, pharmaceutical.getCategory());
      statement.setInt(5, pharmaceutical.getDosage());
      statement.executeUpdate();
      System.out.println("Pharmaceutical information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Pharmaceutical information to database: " + e.getMessage());
    }
  }

  /**
   * This method deletes the given Pharmaceutical object from the database
   *
   * @param pharmaceutical the Pharmaceutical object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  @Override
  public void delete(Pharmaceutical pharmaceutical) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"Pharmaceutical\" where \"pharmaceuticalID\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, pharmaceutical.getItemID());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Pharmaceutical table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Pharmaceutical information deleted successfully.");
      else System.out.println("Pharmaceutical information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }

  /**
   * This method retrieves a Pharmaceutical object with the specified ID from the "Pharmaceutical"
   * table in the database.
   *
   * @param id the ID of the Pharmaceutical object to retrieve from the "Pharmaceutical" table
   * @return the Pharmaceutical object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static Pharmaceutical getPharmaceutical(int id) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Pharmaceutical\" WHERE \"pharmaceuticalID\" = ?";
    Pharmaceutical pharmaceutical = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        int mealid = rs.getInt("pharmaceuticalID");
        String name = rs.getString("name");
        float price = rs.getFloat("price");
        String category = rs.getString("category");
        int dosage = rs.getInt("dosage");
        pharmaceutical = (new Pharmaceutical(mealid, name, price, category, dosage));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return pharmaceutical;
  }

  /**
   * Exports data from a PostgreSQL database table "Pharmaceutical" to a CSV file
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while exporting the data from the database
   */
  public static void exportPharmeceuticalToCSV(String csvFilePath)
      throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Pharmaceutical\"";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      writer.write("pharmaceuticalID,name,price,category,dosage\n");
      while (resultSet.next()) {
        int id = resultSet.getInt("pharmaceuticalID");
        String name = resultSet.getString("name");
        float price = resultSet.getFloat("price");
        String category = resultSet.getString("category");
        int dosage = resultSet.getInt("dosage");
        String row = id + "," + name + "," + price + "," + category + "," + dosage + "\n";
        writer.write(row);
      }
      System.out.println("CSV data downloaded from PostgreSQL database");
    } catch (IOException e) {
      System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
    }
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Pharmaceutical"
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadPharmaceuticalToPostgreSQL(String csvFilePath)
      throws SQLException, ParseException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();

    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);
    if (csvData.size() != 0) {
      try (connection) {
        String query =
            "INSERT INTO \"Pharmaceutical\" (\"pharmaceuticalID\", \"name\", \"price\", \"category\", \"dosage\") VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement =
            connection.prepareStatement("TRUNCATE TABLE \"Pharmaceutical\";");
        statement.executeUpdate();
        statement = connection.prepareStatement(query);

        for (int i = 1; i < csvData.size(); i++) {
          String[] row = csvData.get(i);
          statement.setInt(1, Integer.parseInt(row[0])); // pharmaceuticalID is an int column
          statement.setString(2, row[1]); // name is a string column
          statement.setFloat(3, Float.parseFloat(row[2])); // price is a float column
          statement.setString(4, row[3]); // category is a string column
          statement.setInt(5, Integer.parseInt(row[4])); // dosage is a boolean column

          statement.executeUpdate();
        }
        System.out.println("CSV data uploaded to PostgreSQL database");
      } catch (SQLException e) {
        System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
      }
    }
  }

  /**
   * * Creates a table for storing Pharmaceutical data if it doesn't already exist
   *
   * @throws SQLException if connection to the database fails
   */
  public static void createTable() throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "create table if not exists \"Pharmaceutical\"\n"
              + "(\n"
              + "    \"pharmaceuticalID\" integer not null\n"
              + "        constraint \"Pharmaceutical_pk\"\n"
              + "            primary key,\n"
              + "    name               varchar(50),\n"
              + "    price              double precision,\n"
              + "    category           varchar(40),\n"
              + "    dosage             integer\n"
              + ");";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
}
