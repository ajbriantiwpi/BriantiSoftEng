package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.Furniture;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FurnitureDAO extends DAO<Furniture> {
  void sync(Furniture furniture) throws SQLException;

  ArrayList<Furniture> getAll() throws SQLException;

  void add(Furniture furniture) throws SQLException;

  void delete(Furniture furniture) throws SQLException;
}
