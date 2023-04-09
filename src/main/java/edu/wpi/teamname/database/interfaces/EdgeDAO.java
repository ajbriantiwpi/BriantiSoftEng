package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.Edge;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EdgeDAO extends DAO<Edge> {
  void sync(Edge edge) throws SQLException;

  ArrayList<Edge> getAll() throws SQLException;

  void add(Edge edge) throws SQLException;

  void delete(Edge edge) throws SQLException;
}
