package edu.wpi.teamname;

import edu.wpi.teamname.employees.Employee;
import lombok.Getter;
import lombok.Setter;

public class GlobalVariables {
  @Getter @Setter public static Employee currentUser = null;

  /** Sets the current user to be null indicating no user is logged in */
  public static void logOut() {
    currentUser = null;
  }
}
