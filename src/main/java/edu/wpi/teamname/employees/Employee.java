package edu.wpi.teamname.employees;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class Employee {
  @Getter @Setter private String username;
  @Getter @Setter private String password;
  @Getter @Setter private int employeeID;
  @Getter @Setter private String firstName;
  @Getter @Setter private String lastName;
  @Getter private final String originalUsername;
  @Getter private ArrayList<EmployeeType> type;

  /**
   * Constructor for login that sets the username and password for every instance of someone logging
   * in
   *
   * @param username
   * @param password
   */
  public Employee(
      String username, String password, int employeeID, String firstName, String lastName) {
    type = new ArrayList<EmployeeType>();
    this.username = username;
    this.originalUsername = username;
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    // encrypt the password using Caesar cipher
    this.password = encrypt(password, 3);
  }

  public void addType(EmployeeType employeeType) {
    if (!type.contains(employeeType)) {
      type.add(employeeType);
    }
  }

  public void removeType(EmployeeType employeeType) {
    type.remove(employeeType);
  }

  public boolean LogInto() throws SQLException {
    Connection connection = DataManager.DbConnection();
    boolean done = false;
    String query =
        "Select count(*) from \"Employee\" l Where l.username = '"
            + username
            + "' AND l.password = '"
            + password
            + "'";

    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(query);
      rs.next();
      int count = rs.getInt(1);
      if (count == 1) {
        if (type.contains(EmployeeType.ADMIN)) {
          System.out.println("Welcome Admin " + username + "!");
          // ADMIN IS TRUE
        } else {
          System.out.println("Welcome " + username + "!");
          // ADMIN IS FALSE
        }

        done = true;
      } else if (count == 0) {
        done = false;
        System.out.println("Username or Password are incorrect.");
        // should clear the line and have to make new login object to update constructor
      }
    } catch (SQLException e2) {
      System.out.println("Login Error. " + e2);
    }
    return done;
  }

  public void setLogin(String newUser, String newPass) throws SQLException {
    Connection connection = DataManager.DbConnection();
    if (!checkLegalLogin(newUser)) {
      System.out.println(
          "Username does not meet the requirements: 8 Characters, 1 uppercase, 1 number, 1 special.");
    } else { // meets username req
      // encrypt the password using Caesar cipher
      String encryptedPass = encrypt(newPass, 3);
      String query =
          "INSERT INTO \"Employee\" (username, password) VALUES('"
              + newUser
              + "', '"
              + encryptedPass
              + "')";
      try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(query);
      } catch (SQLException e) {
        System.out.println("Set Login Error. " + e);
      }
    }
  }

  public String resetPass(String newPass) throws SQLException {
    Connection connection = DataManager.DbConnection();
    // encrypt the new password using Caesar cipher
    String encryptedPass = encrypt(newPass, 3);
    this.password = encryptedPass;
    String query =
        "UPDATE \"Employee\" SET password = '"
            + encryptedPass
            + "' WHERE username = '"
            + username
            + "'";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(query);
      System.out.println("New password is now: " + newPass);
    } catch (SQLException e3) {
      System.out.println("Set New Password Error. " + e3);
    }
    return newPass;
  }

  @Override
  public String toString() {
    return "Employee{"
        + "username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", employeeID="
        + employeeID
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", type="
        + type
        + '}';
  }

  public String encrypt(String plaintext, int shift) {
    StringBuilder ciphertext = new StringBuilder();
    for (int i = 0; i < plaintext.length(); i++) {
      char c = plaintext.charAt(i);
      if (Character.isLetter(c)) {
        char base = Character.isUpperCase(c) ? 'A' : 'a';
        c = (char) (base + (c - base + shift) % 26);
      }
      ciphertext.append(c);
    }
    return ciphertext.toString();
  }

  // ---------------Login requirements-----------

  public boolean checkLegalLogin(String u) {
    if (u.contains("\'") || u.contains(";")) {
      return false;
    } else if (u.length() >= 8 && capital(u) && number(u) && special(u)) {
      return true;
    } else {
      return false;
    }
  }

  private boolean capital(String u) {
    for (int i = 0; i < u.length(); i++) {
      char c = u.charAt(i);
      if (Character.isUpperCase(c)) {
        return true;
      }
    }
    return false;
  }

  private boolean number(String u) {
    for (int i = 0; i < u.length(); i++) {
      char c = u.charAt(i);
      if (Character.isDigit(c)) {
        return true;
      }
    }
    return false;
  }

  private boolean special(String u) {
    for (int i = 0; i < u.length(); i++) {
      char c = u.charAt(i);
      if (!Character.isDigit(c) && !Character.isLetter(c)) {
        return true;
      }
    }
    return false;
  }
}
