package edu.wpi.teamname.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Login {
  @Getter @Setter private String username;
  @Getter @Setter private String password;

  private static boolean admin;

  /**
   * Constructor for login that sets the username and password for every instance of someone logging
   * in
   *
   * @param username
   * @param password
   */
  public Login(String username, String password) {
    if (username == "Admin" && password == "Admin") {
      admin = true;
    }
    this.username = username;
    this.password = password;
  }

  public boolean LogInto() throws SQLException {
    Connection connection = DataManager.DbConnection();
    boolean done = false;
    String query =
            "Select count(*) from \"Login\" l Where l.username = '"
                    + username
                    + "' AND l.password = '"
                    + password
                    + "'";

    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(query);
      rs.next();
      int count = rs.getInt(1);
      if (count == 1) {
        if (admin) {
          System.out.println("Welcome Admin " + username + "!");
          // ADMIN IS TRUE
        } else {
          System.out.println("Welcome " + username + "!");
          //ADMIN IS FALSE
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

  public void setLogin() throws SQLException {
    Connection connection = DataManager.DbConnection();
    Scanner scan = new Scanner(System.in);
    System.out.println("Set Username: ");
    String newUser = scan.nextLine();
    if(!checkLegalLogin(username)){
      System.out.println("Username does not meet the requirements: 8 Characters, 1 uppercase, 1 number, 1 special.");
      setLogin();
    }
    else{
      System.out.println("Set Password: ");
      String newPass = scan.nextLine();

      String query =
              "INSERT INTO \"Login\" (username, password) Values('" + newUser + "', '" + newPass + "')";
      try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(query);
      } catch (SQLException e) {
        System.out.println("Set Login Error. " + e);
      }
    }
  }

  public String resetPass() throws SQLException {
    Connection connection = DataManager.DbConnection();
    Scanner scan = new Scanner(System.in);

    System.out.println("Username: " + username);
    System.out.println("New Password: ");
    String newPass = scan.nextLine();
//    StringBuilder sb = new StringBuilder();
//    Random rand = new Random();
//    String oldPass = "NewOldPassword";
//        for (int i = 0; i < 10; i++) {
//          sb.append(oldPass.charAt(rand.nextInt(oldPass.length())));
//        }
    this.password = newPass;
    String query =
            "Update \"Login\" Set password = '" + password + "' where username = '" + username + "'";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(query);
      System.out.println("New password is now: " + password);
    } catch (SQLException e3) {
      System.out.println("Set New Password Error. " + e3);
    }
    return newPass;
   }

  public String toString() {
    return "[" + username + ", " + password + "]";
  }

  //---------------Login requirements-----------

  public boolean checkLegalLogin(String u){
    if(u.contains("\"") || u.contains(";")){
      return false;
    }
    else if(u.length()>=8 && capital(u) && number(u) && special(u)){
      return true;
    }
    else{
      return false;
    }
  }
  private boolean capital(String u){
    for(int i=0;i<u.length()-1;i++){
      char c = u.charAt(i);
      if(Character.isUpperCase(c)){
        return true;
      }
    }
    return false;
  }
  private boolean number(String u){
    for(int i=0;i<u.length()-1;i++){
      char c = u.charAt(i);
      if(Character.isDigit(c)){
        return true;
      }
    }
    return false;
  }
  private boolean special(String u){
    for(int i=0;i<u.length()-1;i++){
      char c = u.charAt(i);
      if(!Character.isDigit(c) && !Character.isLetter(c)){
        return true;
      }
    }
    return false;
  }
}
