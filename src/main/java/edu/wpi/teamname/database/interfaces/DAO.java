package edu.wpi.teamname.database.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<T> {
  void sync(T type) throws SQLException;

  ArrayList<T> getAll() throws SQLException;

  void add(T type) throws SQLException;

  void delete(T type) throws SQLException;
}
