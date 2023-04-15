package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.employees.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends DAO<Employee> {
  void sync(Employee login) throws SQLException;

  ArrayList<Employee> getAll() throws SQLException;

  void add(Employee login) throws SQLException;

  void delete(Employee login) throws SQLException;
}
