package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.FlowerDAO;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import java.util.ArrayList;

public class FlowerDAOImpl implements FlowerDAO {
  /** */
  @Override
  public void sync(Flower flower) {}

  /** @return */
  @Override
  public ArrayList<Flower> getAll() {
    return null;
  }

  /** @param flower */
  @Override
  public void add(Flower flower) {}

  /** @param flower */
  @Override
  public void delete(Flower flower) {}
}
