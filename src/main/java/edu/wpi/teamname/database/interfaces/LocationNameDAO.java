package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.LocationName;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationNameDAO extends DAO<LocationName> {
  void sync();

  ArrayList<LocationName> getAll() throws SQLException;

  void add(LocationName locationName) throws SQLException;

  void delete(LocationName locationName) throws SQLException;
}
