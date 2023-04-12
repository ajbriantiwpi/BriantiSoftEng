package edu.wpi.teamname;

import static org.junit.Assert.assertEquals;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.ItemsOrderedDAOImpl;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.*;

public class ItemsOrderedDAOTest {
  private ItemsOrderedDAOImpl itemsOrderedDAO;

  @BeforeAll
  static void setup() throws SQLException {
    ItemsOrderedDAOImpl io = new ItemsOrderedDAOImpl();
    DataManager.createTableIfNotExists(
        "ItemsOrdered",
        "CREATE TABLE IF NOT EXISTS \"ItemsOrdered\" ("
            + "\"requestID\" INT NOT NULL,"
            + "\"itemID\" INT NOT NULL,"
            + "\"quantity\" INT NOT NULL,"
            + "PRIMARY KEY (\"requestID\", \"itemID\")"
            + ")");
  }

  @BeforeEach
  void init() throws SQLException {
    itemsOrderedDAO.add(new ItemsOrdered(1, 1, 1));
  }

  @AfterEach
  void cleanup() throws SQLException {
    ArrayList<ItemsOrdered> itemsOrdereds = itemsOrderedDAO.getAll();
    for (ItemsOrdered io : itemsOrdereds) {
      itemsOrderedDAO.delete(io);
    }
  }

  @Test
  public void testSync() throws SQLException {
    // create an item ordered and add it to the database
    ItemsOrdered itemsOrdered = new ItemsOrdered(1, 1, 1);
    itemsOrderedDAO.add(itemsOrdered);

    // update the quantity of the item ordered
    itemsOrdered.setQuantity(2);
    itemsOrderedDAO.sync(itemsOrdered);

    // retrieve the updated item ordered from the database
    ItemsOrdered updatedItemsOrdered = itemsOrderedDAO.getItemOrdered(1, 1);

    // assert that the quantity has been updated
    assertEquals(2, updatedItemsOrdered.getQuantity());
  }

  @Test
  public void testGetAll() throws SQLException {
    // add three item ordered to the database
    ItemsOrdered itemsOrdered1 = new ItemsOrdered(1, 1, 1);
    ItemsOrdered itemsOrdered2 = new ItemsOrdered(1, 2, 2);
    ItemsOrdered itemsOrdered3 = new ItemsOrdered(2, 1, 3);
    itemsOrderedDAO.add(itemsOrdered1);
    itemsOrderedDAO.add(itemsOrdered2);
    itemsOrderedDAO.add(itemsOrdered3);

    // retrieve all the item ordered from the database
    ArrayList<ItemsOrdered> itemsOrderedList = itemsOrderedDAO.getAll();

    // assert that the correct number of items have been retrieved
    assertEquals(3, itemsOrderedList.size());

    // assert that each item has the correct values
    assertEquals(itemsOrdered1, itemsOrderedList.get(0));
    assertEquals(itemsOrdered2, itemsOrderedList.get(1));
    assertEquals(itemsOrdered3, itemsOrderedList.get(2));
  }

  @Test
  public void testAdd() throws SQLException {
    // create an item ordered and add it to the database
    ItemsOrdered itemsOrdered = new ItemsOrdered(1, 1, 1);
    itemsOrderedDAO.add(itemsOrdered);

    // retrieve the item ordered from the database
    ItemsOrdered retrievedItemsOrdered = itemsOrderedDAO.getItemOrdered(1, 1);

    // assert that the retrieved item has the correct values
    assertEquals(itemsOrdered, retrievedItemsOrdered);
  }

  @Test
  public void testDelete() throws SQLException {
    // create an item ordered and add it to the database
    ItemsOrdered itemsOrdered = new ItemsOrdered(1, 1, 1);
    itemsOrderedDAO.add(itemsOrdered);

    // delete the item ordered from the database
    itemsOrderedDAO.delete(itemsOrdered);

    // assert that the item has been deleted from the database
    ItemsOrdered deletedItemsOrdered = ItemsOrderedDAOImpl.getItemOrdered(1, 1);
    assertEquals(null, deletedItemsOrdered);
  }
}
