package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.MealDAOImpl;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import java.sql.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MealDAOTest {
  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"Meal\"";
    Connection connection = DataManager.DbConnection();
    DataManager.createTableIfNotExists(
        "Meal",
        "CREATE TABLE IF NOT EXISTS \"Meal\" (\"mealID\" INTEGER, \"Name\" TEXT, \"Price\" INTEGER, \"Meal\" TEXT, \"Cuisine\" TEXT);");
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Truncate Error. " + e);
    }
    connection.close();
  }

  @Test
  public void testSync() throws SQLException {

    // Initialize test data
    Meal meal = new Meal(1, "Test Meal", 10.99f, "Test Food", "Test Cuisine");

    // Sync meal to database
    MealDAOImpl dao = new MealDAOImpl();
    dao.sync(meal);

    // Retrieve synced meal from database
    Meal syncedMeal = MealDAOImpl.getMeal(1);

    // Assert that synced meal matches original meal
    assertEquals(meal.getItemID(), syncedMeal.getItemID());
    assertEquals(meal.getName(), syncedMeal.getName());
    assertEquals(meal.getPrice(), syncedMeal.getPrice(), 0.01);
    assertEquals(meal.getMeal(), syncedMeal.getMeal());
    assertEquals(meal.getCuisine(), syncedMeal.getCuisine());
  }

  @Test
  void testGetAll() throws SQLException {
    // Add some meals to the database
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.add(new Meal(1, "Pizza", 10.0f, "Margherita", "Italian"));
    mealDAO.add(new Meal(2, "Burger", 8.0f, "Cheeseburger", "American"));
    mealDAO.add(new Meal(3, "Taco", 6.0f, "Beef", "Mexican"));

    // Retrieve all meals from the database
    ArrayList<Meal> meals = mealDAO.getAll();

    // Check that the correct number of meals were retrieved
    Assertions.assertEquals(3, meals.size());

    // Check that the meals were retrieved in the correct order
    Assertions.assertEquals("Pizza", meals.get(0).getName());
    Assertions.assertEquals("Burger", meals.get(1).getName());
    Assertions.assertEquals("Taco", meals.get(2).getName());
  }

  @Test
  void testAdd() throws SQLException {
    // create a location name to add

    Meal m = new Meal(40, "fish", 9.9f, "salmon", "idk");
    DataManager.deleteMeals(m);
    // attempt to add the location name
    try {
      DataManager.addMeal(m);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<Meal> list = null;
    try {
      list = DataManager.getAllMeals();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (Meal Edge1 : list) {
      if (Edge1.equals(m)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }

  @Test
  void testDelete() throws SQLException, SQLException {
    // Add a meal to the database
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.add(new Meal(1, "Pizza", 10.0f, "Margherita", "Italian"));

    // Delete the meal
    mealDAO.delete(new Meal(1, "Pizza", 10.0f, "Margherita", "Italian"));

    // Attempt to retrieve the deleted meal from the database
    Meal deletedMeal = MealDAOImpl.getMeal(1);

    // Check that the meal has been deleted
    Assertions.assertNull(deletedMeal);
  }
}
