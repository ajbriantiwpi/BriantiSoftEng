package edu.wpi.teamname.database;

import lombok.Getter;
import lombok.Setter;

public class Login {
  @Getter @Setter private String username;
  @Getter @Setter private String password;

  /**
   * Constructor for login that sets the username and password for every instance of someone logging
   * in
   *
   * @param username
   * @param password
   */
  public Login(String username, String password) {}
}
