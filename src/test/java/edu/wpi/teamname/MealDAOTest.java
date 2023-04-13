package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.MealDAOImpl;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MealDAOTest {
  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    Connection connection = DataManager.DbConnection();
    String query = "Truncate Table \"Meal\"";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Truncate Error. " + e);
    }
    connection.close();
  }

  //  @Test
  //  public void testSync() throws SQLException {
  //    testAdd();
  //    // Initialize test data
  //    Meal meal = new Meal(1, "Test Meal", 10.99f, "Test Food", "Test Cuisine");
  //
  //    // Sync meal to database
  //    MealDAOImpl dao = new MealDAOImpl();
  //    dao.sync(meal);
  //
  //    // Retrieve synced meal from database
  //    Meal syncedMeal = MealDAOImpl.getMeal(1);
  //
  //    // Assert that synced meal matches original meal
  //    assertEquals(meal.getItemID(), syncedMeal.getItemID());
  //    assertEquals(meal.getName(), syncedMeal.getName());
  //    assertEquals(meal.getPrice(), syncedMeal.getPrice(), 0.01);
  //    assertEquals(meal.getMeal(), syncedMeal.getMeal());
  //    assertEquals(meal.getCuisine(), syncedMeal.getCuisine());
  //  }

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

    Meal m = new Meal(1, "Test Meal", 10.99f, "Test Food", "Test Cuisine");
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
      if (Edge1.isEqual(m)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }

  @Test
  void testDelete() throws SQLException, SQLException {
    // Add a meal for the database
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.add(new Meal(1, "Pizza", 10.0f, "Margherita", "Italian"));

    // Delete the meal
    mealDAO.delete(new Meal(1, "Pizza", 10.0f, "Margherita", "Italian"));

    // Attempt to retrieve the deleted meal from the database
    Meal deletedMeal = MealDAOImpl.getMeal(1);

    // Check that the meal has been deleted
    Assertions.assertNull(deletedMeal);
  }

  @Test
  void testExportCSV() throws SQLException {
    try {
      Connection conn =
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"Meal\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<Meal> testData = new ArrayList<>();
    testData.add(new Meal(9, "TestFood9", 9.0f, "TestMeal9", "TestC9"));
    testData.add(new Meal(10, "TestFood10", 10.0f, "TestMeal10", "TestC10"));
    testData.add(new Meal(11, "TestFood11", 11.0f, "TestMeal11", "TestC11"));
    try {
      for (Meal ln : testData) {
        DataManager.addMeal(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // export the location names to a CSV file
    String csvFilePath = "test_meal.csv";
    try {
      DataManager.exportMealToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      assertEquals(lines.get(0), "mealID,Name,Price,Meal,Cuisine");
      assertEquals(lines.get(1), "9, \"TestFood9\", 9.0f, \"TestMeal9\", \"TestC9\"");
      assertEquals(lines.get(2), "10, \"TestFood10\", 10.0f, \"TestMeal10\", \"TestC10\"");
      assertEquals(lines.get(3), "11, \"TestFood11\", 11.0f, \"TestMeal11\", \"TestC11\"");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }
}
