package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.MoveDAO;
import edu.wpi.teamname.navigation.Move;
import java.util.ArrayList;
import lombok.Getter;

public class MoveDAOImpl implements MoveDAO {
  @Getter private ArrayList<Move> moves;

  /** */
  @Override
  public void sync(Move move) {}

  /** @return */
  @Override
  public ArrayList<Move> getAll() {
    return null;
  }

  /** @param type */
  @Override
  public void add(Move type) {}

  /** @param type */
  @Override
  public void delete(Move type) {}
}
