package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.Flower;
import java.util.ArrayList;

public interface FlowerDAO extends DAO<Flower> {
  void sync(Flower flower);

  ArrayList<Flower> getAll();

  void add(Flower flower);

  void delete(Flower flower);
}
