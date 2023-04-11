package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.navigation.Edge;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EdgeDAOTest {
  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"Edge\"";
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
    Edge edge = new Edge(40, 41);

    // attempt to sync the location name
    try {
      DataManager.syncEdge(edge);
    } catch (SQLException e) {
      fail("SQL Exception thrown while syncing location name");
    }

    // verify that the location name was synced
    ArrayList<Edge> list = new ArrayList<Edge>();
    try {
      list = DataManager.getAllEdges();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean foundEdge = false;
    for (Edge ln : list) {
      if (ln.getStartNodeID() == edge.getStartNodeID()) {
        assertEquals(ln.getEndNodeID(), edge.getEndNodeID());
        foundEdge = true;
        break;
      }
    }
    if (!foundEdge) {
      fail("Location name not found in database after sync");
    }
  }

  @Test
  void testGetAll() {
    // insert some test data
    List<Edge> testData = new ArrayList<>();
    testData.add(new Edge(10, 11));
    testData.add(new Edge(20, 21));
    testData.add(new Edge(30, 31));
    try {
      for (Edge ln : testData) {
        DataManager.addEdge(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // retrieve all location names
    List<Edge> list = new ArrayList<>();
    try {
      list = DataManager.getAllEdges();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean start1 = list.get(0).getStartNodeID() == 10;
    boolean end1 = list.get(0).getEndNodeID() == 11;
    boolean start2 = list.get(1).getStartNodeID() == 20;
    boolean end2 = list.get(1).getEndNodeID() == 21;
    boolean start3 = list.get(2).getStartNodeID() == 30;
    boolean end3 = list.get(2).getEndNodeID() == 31;
    boolean all = start1 && end1 && start2 && end2 && start3 && end3;
    // verify that all location names were retrieved
    assertTrue(all);
  }

  @Test
  void testAdd() throws SQLException {
    // create a location name to add

    Edge edge = new Edge(40, 41);
    DataManager.deleteEdge(edge);
    // attempt to add the location name
    try {
      DataManager.addEdge(edge);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<Edge> list = null;
    try {
      list = DataManager.getAllEdges();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (Edge Edge1 : list) {
      if (Edge1.equals(edge)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }

  @Test
  void testDelete() throws SQLException {
    // create a location name to delete
    Edge edge = new Edge(50, 51);

    // add the location name to the database
    try {
      DataManager.addEdge(edge);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // delete the location name from the database
    try {
      DataManager.deleteEdge(edge);
    } catch (SQLException e) {
      fail("SQL Exception thrown while deleting location name");
    }

    // verify that the location name was deleted
    ArrayList<Edge> list = null;
    try {
      list = DataManager.getAllEdges();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean isIn = false;
    for (Edge Edge1 : list) {
      if (Edge1.equals(edge)) {
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
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"Edge\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<Edge> testData = new ArrayList<>();
    testData.add(new Edge(60, 61));
    testData.add(new Edge(70, 71));
    testData.add(new Edge(80, 81));
    try {
      for (Edge ln : testData) {
        DataManager.addEdge(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // export the location names to a CSV file
    String csvFilePath = "test_edge.csv";
    try {
      DataManager.exportEdgeToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      assertEquals(lines.get(0), "\"startNode\",\"endNode\"");
      assertEquals(lines.get(1), "60,61");
      assertEquals(lines.get(2), "70,71");
      assertEquals(lines.get(3), "80,81");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }
  // Test uploading a CSV file to a new table
  @Test
  public void testUploadEdgeToPostgreSQL() throws SQLException {
    // Set up test data
    String csvFilePath = "src/test/resources/test_edge.csv";

    // Call the function being tested
    DataManager.uploadEdge(csvFilePath);

    // Verify that the data was uploaded correctly
    Connection connection = DataManager.DbConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM \"Edge\"");
    assertTrue(resultSet.next());
    assertEquals(3, resultSet.getInt(1));
  }
}
