package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends DAO<Login> {
  void sync(Login login);

  ArrayList<Login> getAll() throws SQLException;

  void add(Login login) throws SQLException;

  void delete(Login login) throws SQLException;
}
