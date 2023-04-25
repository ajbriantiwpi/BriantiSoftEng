package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.ConfReservation;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ConfReservationDAO extends DAO<ConfReservation> {
  void sync(ConfReservation res) throws SQLException;

  ArrayList<ConfReservation> getAll() throws SQLException;

  void add(ConfReservation res) throws SQLException;

  void delete(ConfReservation res) throws SQLException;
}
