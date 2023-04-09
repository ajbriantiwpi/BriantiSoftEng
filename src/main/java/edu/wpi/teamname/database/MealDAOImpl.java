package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.MealDAO;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class MealDAOImpl implements MealDAO {
  @Getter
  private ArrayList<Meal> meals;
  /** */
  @Override
  public void sync(Meal meal) {}

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
            "INSERT INTO \"Meal\" (\"mealID\", \"Name\", \"Price\", \"Meal\", \"Cuisine\") " + "VALUES (?, ?, ?, ?, ?)";

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
    String query = "Delete from \"Meal\" " + "where \"mealID\" = " + meal.getItemID();

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Meal table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0)
        System.out.println("Meal information deleted successfully.");
      else System.out.println("Meal information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
}
