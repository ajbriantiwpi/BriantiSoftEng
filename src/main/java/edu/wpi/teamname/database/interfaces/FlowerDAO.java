package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.Flower;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FlowerDAO extends DAO<Flower> {
  void sync(Flower flower) throws SQLException;

  ArrayList<Flower> getAll() throws SQLException;

  void add(Flower flower) throws SQLException;

  void delete(Flower flower) throws SQLException;
}
