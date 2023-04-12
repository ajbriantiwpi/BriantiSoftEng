package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.requestitem.Furniture;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FurnitureDAOTest {

  @Getter @Setter int itemID;
  @Getter @Setter String name;
  @Getter @Setter float price;
  @Getter @Setter String category;
  @Getter @Setter String size;
  @Getter @Setter String color;

  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"Furniture\"";
    Connection connection = DataManager.DbConnection();
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Truncate Error. " + e);
    }
    connection.close();
  }

  @Test
  void testSync() throws SQLException {
    testAdd();
    // create a location name to sync
    Furniture Furniture = new Furniture(5, "Test5", 5f, "Test5Cat", "Test5Size", "Test5Color");

    // attempt to sync the location name
    try {
      DataManager.syncFurniture(Furniture);
    } catch (SQLException e) {
      fail("SQL Exception thrown while syncing location name");
    }

    // verify that the location name was synced
    ArrayList<Furniture> list = new ArrayList<Furniture>();
    try {
      list = DataManager.getAllFurniture();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean foundFurniture = false;
    for (Furniture ln : list) {
      if (ln.getItemID() == (Furniture.getItemID())) {
        assertEquals(ln.getName(), Furniture.getName());
        assertEquals(ln.getPrice(), Furniture.getPrice());
        assertEquals(ln.getCategory(), Furniture.getCategory());
        assertEquals(ln.getSize(), Furniture.getSize());
        assertEquals(ln.getColor(), Furniture.getColor());
        foundFurniture = true;
        break;
      }
    }
    assertFalse(foundFurniture);
  }

  @Test
  void testGetAll() {
    // insert some test data
    List<Furniture> testData = new ArrayList<>();
    testData.add(new Furniture(1, "Test1", 1f, "Test1Cat", "Test1Size", "Test1Color"));
    testData.add(new Furniture(2, "Test2", 2f, "Test2Cat", "Test2Size", "Test2Color"));
    try {
      for (Furniture ln : testData) {
        DataManager.addFurniture(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // retrieve all location names
    List<Furniture> list = new ArrayList<>();
    try {
      list = DataManager.getAllFurniture();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean id1 = list.get(0).getItemID() == 1;
    boolean n1 = list.get(0).getName().equals("Test1");
    boolean p1 = list.get(0).getPrice() == 1f;
    boolean ca1 = list.get(0).getCategory().equals("Test1Cat");
    boolean s1 = list.get(0).getSize().equals("Test1Size");
    boolean co1 = list.get(0).getColor().equals("Test1Color");
    boolean id2 = list.get(1).getItemID() == 2;
    boolean n2 = list.get(1).getName().equals("Test2");
    boolean p2 = list.get(1).getPrice() == 2f;
    boolean ca2 = list.get(1).getCategory().equals("Test2Cat");
    boolean s2 = list.get(1).getSize().equals("Test2Size");
    boolean co2 = list.get(1).getColor().equals("Test2Color");
    boolean all = id1 && n1 && p1 && ca1 && s1 && co1 && id2 && n2 && p2 && ca2 && s2 && co2;
    // verify that all location names were retrieved
    assertTrue(all);
  }

  @Test
  void testAdd() throws SQLException {
    // create a location name to add

    Furniture Furniture = new Furniture(3, "Test3", 3f, "Test3Cat", "Test3Size", "Test3Color");
    DataManager.deleteFurniture(Furniture);
    // attempt to add the location name
    try {
      DataManager.addFurniture(Furniture);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<Furniture> list = null;
    try {
      list = DataManager.getAllFurniture();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (Furniture Furniture1 : list) {
      if (Furniture1.isEqual(Furniture)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }

  @Test
  void testDelete() throws SQLException {
    // create a location name to delete
    Furniture Furniture = new Furniture(4, "Test1", 4f, "Test4Cat", "Test4Size", "Test4Color");

    // add the location name to the database
    try {
      DataManager.addFurniture(Furniture);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // delete the location name from the database
    try {
      DataManager.deleteFurniture(Furniture);
    } catch (SQLException e) {
      fail("SQL Exception thrown while deleting location name");
    }

    // verify that the location name was deleted
    ArrayList<Furniture> list = null;
    try {
      list = DataManager.getAllFurniture();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean isIn = false;
    for (Furniture Furniture1 : list) {
      if (Furniture1.equals(Furniture)) {
        isIn = true;
      }
    }
    assertFalse(isIn);
  }

  @Test
  void testExportCSV() throws SQLException {
    try {
      Connection conn =
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"Furniture\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<Furniture> testData = new ArrayList<>();
    testData.add(new Furniture(1, "TestN1", 1f, "1Cat", "TestSize1", "1Col"));
    testData.add(new Furniture(2, "TestN2", 2f, "2Cat", "TestSize2", "2Col"));
    testData.add(new Furniture(3, "TestN3", 3f, "3Cat", "TestSize3", "3Col"));
    try {
      for (Furniture ln : testData) {
        DataManager.addFurniture(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // export the location names to a CSV file
    String csvFilePath = "test_furniture.csv";
    try {
      DataManager.exportFurnitureToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      assertEquals(lines.get(0), "furnitureID,name,price,category,size,color");
      assertEquals(lines.get(1), "1,TestN1,1,1Cat,TestSize1,1Col");
      assertEquals(lines.get(2), "2,TestN2,2,2Cat,TestSize2,2Col");
      assertEquals(lines.get(3), "3,TestN3,3,3Cat,TestSize3,3Col");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }
}
