package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlowerDAOTest {
  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"Flowers\"";
    Connection connection = DataManager.DbConnection();
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Truncate Error. " + e);
    }
    connection.close();
  }

  @Test
  void testExportCSV() throws SQLException {
    try {
      Connection conn =
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"Flowers\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<Flower> testData = new ArrayList<>();
    testData.add(new Flower(6, "Test6", 6f, "6Cat", "6Col"));
    testData.add(new Flower(7, "Test7", 7f, "7Cat", "7Col"));
    testData.add(new Flower(8, "Test8", 8f, "8Cat", "8Col"));
    try {
      for (Flower ln : testData) {
        DataManager.addFlower(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // export the location names to a CSV file
    String csvFilePath = "test_flower.csv";
    try {
      DataManager.exportFlowersToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      assertEquals(lines.get(0), "flowerID,Name,Price,Category,Color");
      assertEquals(lines.get(1), "6,Test6,6.0,6Cat,6Col");
      assertEquals(lines.get(2), "7,Test7,7.0,7Cat,7Col");
      assertEquals(lines.get(3), "8,Test8,8.0,8Cat,8Col");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }

  @Test
  void testSync() throws SQLException {
    testAdd();
    // create a location name to sync
    Flower Flower = new Flower(1, "TestName", 10f, "TestCategory", "TestColor");

    // attempt to sync the location name
    try {
      DataManager.syncFlower(Flower);
    } catch (SQLException e) {
      fail("SQL Exception thrown while syncing location name");
    }

    // verify that the location name was synced
    ArrayList<Flower> list = new ArrayList<Flower>();
    try {
      list = DataManager.getAllFlowers();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean foundFlower = false;
    for (Flower ln : list) {
      if (ln.getItemID() == Flower.getItemID()) {
        assertEquals(ln.getName(), Flower.getName());
        assertEquals(ln.getPrice(), Flower.getPrice());
        assertEquals(ln.getCategory(), Flower.getCategory());
        assertEquals(ln.getColor(), Flower.getColor());
        foundFlower = true;
        break;
      }
    }
    if (!foundFlower) {
      fail("Location name not found in database after sync");
    }
  }

  @Test
  void testAdd() throws SQLException {
    // create a location name to add
    Flower Flower = new Flower(1, "TestName", 10f, "TestCategory", "TestColor");
    DataManager.deleteFlower(Flower);
    // attempt to add the location name
    try {
      DataManager.addFlower(Flower);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<Flower> list = null;
    try {
      list = DataManager.getAllFlowers();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (Flower Flower1 : list) {
      System.out.println(Flower1);
      System.out.println(Flower);
      if (Flower1.isEqual(Flower)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }
}
