package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.Pharmaceutical;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PharmaceuticalDAO extends DAO<Pharmaceutical> {
  void sync(Pharmaceutical pharmaceutical) throws SQLException;

  ArrayList<Pharmaceutical> getAll() throws SQLException;

  void add(Pharmaceutical pharmaceutical) throws SQLException;

  void delete(Pharmaceutical pharmaceutical) throws SQLException;
}
