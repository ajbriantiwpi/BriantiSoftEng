package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.MealDAO;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MealDAOImpl implements MealDAO {

  /** */
  @Override
  public void sync(Meal meal) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Meal\" SET \"Name\" = ?, \"Price\" = ?, \"Meal\" = ?, \"Cuisine\" = ?, \"mealID\" = ?"
              + " WHERE \"mealID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, meal.getName());
      statement.setFloat(2, meal.getPrice());
      statement.setString(3, meal.getMeal());
      statement.setString(4, meal.getCuisine());
      statement.setInt(5, meal.getItemID());
      statement.setInt(6, meal.getOriginalID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<Meal> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Meal> list = new ArrayList<Meal>();
    try (connection) {
      String query = "SELECT * FROM \"Meal\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("mealID");
        String name = rs.getString("Name");
        float price = rs.getFloat("Price");
        String meal = rs.getString("Meal");
        String cuis = rs.getString("Cuisine");
        list.add(new Meal(id, name, price, meal, cuis));
      }
    } catch (SQLException e) {
      System.out.println("Get all Nodes error.");
    }
    return list;
  }

  /** @param meal */
  @Override
  public void add(Meal meal) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"Meal\" (\"mealID\", \"Name\", \"Price\", \"Meal\", \"Cuisine\") "
            + "VALUES (?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate(query);
      statement = connection.prepareStatement(query);
      statement.setInt(1, meal.getItemID());
      statement.setString(2, meal.getName());
      statement.setFloat(3, meal.getPrice());
      statement.setString(4, meal.getMeal());
      statement.setString(5, meal.getCuisine());
      statement.executeUpdate();
      System.out.println("Meal information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Meal information to database: " + e.getMessage());
    }
  }

  /** @param meal */
  @Override
  public void delete(Meal meal) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"Meal\" where \"mealID\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, meal.getItemID());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Meal table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Meal information deleted successfully.");
      else System.out.println("Meal information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }

  public static Meal getMeal(int id) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Meal\" WHERE \"mealID\" = ?";
    Meal meal = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();

      int mealid = rs.getInt("mealID");
      String name = rs.getString("Name");
      float price = rs.getFloat("Price");
      String meall = rs.getString("Meal");
      String cuisine = rs.getString("Cuisine");
      meal = (new Meal(mealid, name, price, meall, cuisine));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return meal;
  }
  /**
   * Uploads CSV data to a PostgreSQL database table "Meal"
   *
   * @param csvFilePath is a String representing a file path
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadMealToPostgreSQL(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String query =
          "INSERT INTO \"Meal\" (\"mealID\", \"Name\", \"Price\",\"Meal\",\"Cuisine\") VALUES (?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"Meal\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setInt(1, Integer.parseInt(row[0]));
        statement.setString(2, row[1]);
        statement.setInt(3, Integer.parseInt(row[2]));
        statement.setString(4, row[3]);
        statement.setString(5, row[4]);

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }


  public static void exportMealToCSV(String csvFilePath) throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Meal\"";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      writer.write("mealID,Name,Price,Meal,Cuisine\n");
      while (resultSet.next()) {
        int mealID = resultSet.getInt("mealID");
        String name = resultSet.getString("Name");
        int price = resultSet.getInt("Price");
        String meal = resultSet.getString("Meal");
        String cuisine = resultSet.getString("Cuisine");

        String row = mealID + "," + name + "," + price + "," + meal + "," + cuisine + "\n";
        writer.write(row);
      }
      System.out.println("CSV data downloaded from PostgreSQL database");
    } catch (IOException e) {
      System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
    }
  }
}
