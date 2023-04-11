package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.OfficeSupply;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OfficeSupplyDAO extends DAO<OfficeSupply> {
  void sync(OfficeSupply officeSupply) throws SQLException;

  ArrayList<OfficeSupply> getAll() throws SQLException;

  void add(OfficeSupply officeSupply) throws SQLException;

  void delete(OfficeSupply officeSupply) throws SQLException;
}
