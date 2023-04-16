package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.*;
import java.sql.SQLException;
import java.util.ArrayList;

public interface NodeDAO extends DAO<MapNode> {
  void sync(MapNode mapNode) throws SQLException;

  ArrayList<MapNode> getAll() throws SQLException;

  void add(MapNode mapNode) throws SQLException;

  void delete(MapNode mapNode) throws SQLException;
}
