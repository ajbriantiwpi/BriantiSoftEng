package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LocationNameDAO;
import edu.wpi.teamname.navigation.LocationName;
import java.util.ArrayList;

public class LocationNameDAOImpl implements LocationNameDAO {
  /** */
  @Override
  public void sync() {}

  /** @return */
  @Override
  public ArrayList<LocationName> getAll() {
    return null;
  }

  /** @param locationName */
  @Override
  public void add(LocationName locationName) {}

  /** @param locationName */
  @Override
  public void delete(LocationName locationName) {}
}
