package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.Move;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MoveDAO extends DAO<Move> {
  void sync(Move move) throws SQLException;

  ArrayList<Move> getAll() throws SQLException;

  void add(Move type) throws SQLException;

  void delete(Move type) throws SQLException;
}
