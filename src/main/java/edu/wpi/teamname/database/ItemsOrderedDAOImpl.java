package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ItemsOrderedDAO;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import java.util.ArrayList;

public class ItemsOrderedDAOImpl implements ItemsOrderedDAO {
  /** */
  @Override
  public void sync(ItemsOrdered itemsOrdered) {}

  /** @return */
  @Override
  public ArrayList<ItemsOrdered> getAll() {
    return null;
  }

  /** @param itemsOrdered */
  @Override
  public void add(ItemsOrdered itemsOrdered) {}

  /** @param itemsOrdered */
  @Override
  public void delete(ItemsOrdered itemsOrdered) {}
}
