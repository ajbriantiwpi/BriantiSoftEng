package edu.wpi.teamname;

import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import lombok.Getter;
import lombok.Setter;

public class GlobalVariables {

  private static final Employee dummyEmployee =
      new Employee("dummyU", "dummyP", -1, "dummmyF", "dummyL", true);
  @Getter @Setter private static Employee currentUser = dummyEmployee;

  /** Sets the current user to be null indicating no user is logged in */
  public static void logOut() {
    currentUser = dummyEmployee;
  }

  /**
   * Checks whether the current logged-in user is the given type
   *
   * @param type the type that is being checked if the user is
   * @return whether the user is of that type
   */
  public static boolean userIsType(EmployeeType type) {
    return currentUser.getType().contains(type);
  }
}
