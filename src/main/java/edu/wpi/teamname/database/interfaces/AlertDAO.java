package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.alerts.Alert;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AlertDAO extends DAO<Alert> {
  void sync(Alert alert) throws SQLException;

  ArrayList<Alert> getAll() throws SQLException;

  void add(Alert alert) throws SQLException;

  void delete(Alert alert) throws SQLException;
}
