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

    boolean long1 = list.get(0).getLongName().equals("Test Long Name 1");
    boolean small1 = list.get(0).getShortName().equals("Test Short Name 1");
    boolean type1 = list.get(0).getNodeType().equals("Test Node Type 1");
    boolean long2 = list.get(1).getLongName().equals("Test Long Name 2");
    boolean small2 = list.get(1).getShortName().equals("Test Short Name 2");
    boolean type2 = list.get(1).getNodeType().equals("Test Node Type 2");
    boolean long3 = list.get(2).getLongName().equals("Test Long Name 3");
    boolean small3 = list.get(2).getShortName().equals("Test Short Name 4");
    boolean type3 = list.get(2).getNodeType().equals("Test Node Type 3");
    boolean all = long1 && small1 && type1 && long2 && small2 && type2 && long3 && small3 && type3;
    // verify that all location names were retrieved
    assertTrue(all);
  }

  @Test
  void testAdd() throws SQLException {
    // create a location name to add

    LocationName locationName =
        new LocationName("Test Long Name", "Test Short Name", "Test Node Type");
    dao.delete(locationName);
    // attempt to add the location name
    try {
      dao.add(locationName);
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding location name");
    }

    // verify that the location name was added
    ArrayList<LocationName> list = null;
    try {
      list = dao.getAll();
    } catch (SQLException e) {
      fail("SQL Exception thrown while getting all location names");
    }
    boolean isIn = false;
    for (LocationName locationName1 : list) {
      if (locationName1.equals(locationName)) {
        isIn = true;
      }
    }
    assertTrue(isIn);
  }
}
