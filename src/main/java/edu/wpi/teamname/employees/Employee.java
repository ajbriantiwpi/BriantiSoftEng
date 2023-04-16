package edu.wpi.teamname.employees;

import java.sql.SQLException;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class Employee {
  @Getter @Setter private String username;
  @Getter private String password;
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
      String username,
      String password,
      int employeeID,
      String firstName,
      String lastName,
      Boolean encrypt) {
    type = new ArrayList<EmployeeType>();
    this.username = username;
    this.originalUsername = username;
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    // encrypt the password using Caesar cipher
    if (encrypt) {
      this.password = encrypt(password, 3);
    } else {
      this.password = password;
    }
  }

  public void addType(EmployeeType employeeType) {
    if (!type.contains(employeeType)) {
      type.add(employeeType);
    }
  }

  public void removeType(EmployeeType employeeType) {
    type.remove(employeeType);
  }

  public void setLogin(String newUser, String newPass) throws SQLException {
    if (!checkLegalLogin(newPass)) {
      System.out.println(
          "Password does not meet the requirements: 8 Characters, 1 uppercase, 1 number, 1 special.");
    } else { // meets password req
      // encrypt the password using Caesar cipher
      String encryptedPass = encrypt(newPass, 3);
      this.username = newUser;
      this.password = encryptedPass;
    }
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

  public static String encrypt(String plaintext, int shift) {
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
