package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.employees.Feedback;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FeedbackDAO extends DAO<Feedback> {
  void sync(Feedback feedback) throws SQLException;

  ArrayList<Feedback> getAll() throws SQLException;

  void add(Feedback feedback) throws SQLException;

  void delete(Feedback feedback) throws SQLException;
}
