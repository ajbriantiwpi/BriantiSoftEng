package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LoginDAO;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
  /** */
  @Override
  public void sync() {}

  /** @return */
  @Override
  public ArrayList<Login> getAll() {
    return null;
  }

  /** @param login */
  @Override
  public void add(Login login) {}

  /** @param login */
  @Override
  public void delete(Login login) {}
}
