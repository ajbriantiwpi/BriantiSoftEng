package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.NodeDAO;
import edu.wpi.teamname.navigation.Node;
import java.util.ArrayList;

public class NodeDAOImpl implements NodeDAO {
  /** */
  @Override
  public void sync(Node node) {}

  /** @return */
  @Override
  public ArrayList<Node> getAll() {
    return null;
  }

  /** @param type */
  @Override
  public void add(Node type) {}

  /** @param type */
  @Override
  public void delete(Node type) {}
}
