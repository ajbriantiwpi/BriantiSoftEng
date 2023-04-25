package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ConfRomDAO extends DAO<ConfRoom> {
  void sync(ConfRoom confRoom) throws SQLException;

  ArrayList<ConfRoom> getAll() throws SQLException;

  void add(ConfRoom confRoom) throws SQLException;

  void delete(ConfRoom confRoom) throws SQLException;
}
