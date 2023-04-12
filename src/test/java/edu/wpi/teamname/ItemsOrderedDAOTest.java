package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.ItemsOrderedDAOImpl;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;

public class ItemsOrderedDAOTest {
  private ItemsOrderedDAOImpl itemsOrderedDAO;

  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"ItemsOrdered\"";
    Connection connection = DataManager.DbConnection();
    DataManager.createTableIfNotExists(
        "ItemsOrdered",
        "CREATE TABLE IF NOT EXISTS \"ItemsOrdered\" (\"requestID\" INTEGER, \"itemID\" INTEGER, \"quantity\" INTEGER);");
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
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"ItemsOrdered\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<ItemsOrdered> testData = new ArrayList<>();
    testData.add(new ItemsOrdered(6, 55, 6));
    testData.add(new ItemsOrdered(2, 4, 7));
    testData.add(new ItemsOrdered(1, 2, 9));
    try {
      for (ItemsOrdered ln : testData) {
        DataManager.addItemsOrdered(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }


    String csvFilePath = "test_ItemsOrdered.csv";
    try {
      DataManager.exportItemsOrderedToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      Assertions.assertEquals(lines.get(0), "requestID,itemID,quantity");
      Assertions.assertEquals(lines.get(1), "6,55,6");
      Assertions.assertEquals(lines.get(2), "2,4,7");
      Assertions.assertEquals(lines.get(3), "1,2,9");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }

  @Test
  void testSync() throws SQLException {
    testAdd();

    ItemsOrdered items = new ItemsOrdered(1, 3, 6);


    try {
      DataManager.syncItemsOrdered(items);
    } catch (SQLException e) {
      fail("SQL Exception thrown while syncing location name");
    }


    ArrayList<ItemsOrdered> list = new ArrayList<ItemsOrdered>();
    try {
      list = DataManager.getAllItemsOrdered();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean foundItem = false;
    for (ItemsOrdered ln : list) {
      if (ln.getOriginalItemID() == items.getOriginalItemID()) {
        assertEquals(ln.getRequestID(), items.getRequestID());
        assertEquals(ln.getItemID(), items.getItemID());
        assertEquals(ln.getQuantity(), items.getQuantity());
        foundItem = true;
        break;
      }
    }
    if (!foundItem) {
      fail("Item not found in database after sync");
    }
  }

  @Test
  void testAdd() throws SQLException {

    ItemsOrdered items = new ItemsOrdered(1, 2, 3);
    DataManager.deleteItemsOrdered(items);

    try {
      DataManager.addItemsOrdered(items);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding item");
    }

    ArrayList<ItemsOrdered> list = null;
    try {
      list = DataManager.getAllItemsOrdered();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (ItemsOrdered item1 : list) {
      System.out.println(item1);
      System.out.println(items);
      if (item1.equals(items)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }
}
