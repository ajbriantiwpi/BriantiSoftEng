package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.LocationNameDAOImpl;
import edu.wpi.teamname.navigation.LocationName;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationNameDAOImplTest {

  private LocationNameDAOImpl dao;

  @BeforeEach
  void setUp() {
    dao = new LocationNameDAOImpl();
  }

  @Test
  void testSync() {
    // create a location name to sync
    LocationName locationName =
        new LocationName("Test Long Name", "Test Short Name", "Test Node Type");

    // attempt to sync the location name
    try {
      dao.sync(locationName);
    } catch (SQLException e) {
      fail("SQL Exception thrown while syncing location name");
    }

    // verify that the location name was synced
    ArrayList<LocationName> list = new ArrayList<LocationName>();
    try {
      list = dao.getAll();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    boolean foundLocationName = false;
    for (LocationName ln : list) {
      if (ln.getLongName().equals(locationName.getLongName())) {
        assertEquals(ln.getShortName(), locationName.getShortName());
        assertEquals(ln.getNodeType(), locationName.getNodeType());
        foundLocationName = true;
        break;
      }
    }

    if (!foundLocationName) {
      fail("Location name not found in database after sync");
    }
  }

  @Test
  void testGetAll() {
    // insert some test data
    List<LocationName> testData = new ArrayList<>();
    testData.add(new LocationName("Test Long Name 1", "Test Short Name 1", "Test Node Type 1"));
    testData.add(new LocationName("Test Long Name 2", "Test Short Name 2", "Test Node Type 2"));
    testData.add(new LocationName("Test Long Name 3", "Test Short Name 3", "Test Node Type 3"));
    try {
      for (LocationName ln : testData) {
        dao.add(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // retrieve all location names
    List<LocationName> list = new ArrayList<>();
    try {
      list = dao.getAll();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    // verify that all location names were retrieved
    assertEquals(testData.size(), list.size());
    for (LocationName ln : testData) {
      assertTrue(list.contains(ln));
    }
  }

  @Test
  void testAdd() {
    // create a location name to add
    LocationName locationName =
        new LocationName("Test Long Name", "Test Short Name", "Test Node Type");

    // attempt to add the location name
    try {
      dao.add(locationName);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<LocationName> list = new ArrayList<LocationName>();
    try {
      list = dao.getAll();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }

    assertTrue(list.contains(locationName));
  }
}
