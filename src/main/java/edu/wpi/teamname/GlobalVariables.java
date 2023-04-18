package edu.wpi.teamname;

import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class GlobalVariables {
  @Getter @Setter private static Color borderColor = Color.web("33567A");
  @Getter @Setter private static Color insideColor = Color.web("2FA7B0");
  @Getter @Setter private static float circleR = 10.0f;
  @Getter @Setter private static float lineT = 10.0f;
  @Getter @Setter private static int strokeThickness = 2;
  @Getter @Setter private static Color labelColor = new Color(.835, .89, 1, 1);
  @Getter @Setter private static Color labelTextColor = new Color(0, .106, .231, 1);

  private static final Employee dummyEmployee =
      new Employee("dummyU", "dummyP", -1, "dummmyF", "dummyL", true);
  @Getter @Setter private static Employee currentUser = dummyEmployee;
  @Getter @Setter private static Screen currentScreen = Screen.HOME;
  @Getter @Setter private static Screen previousScreen = Screen.HOME;
  /** Sets the current user to be null indicating no user is logged in */
  public static void logOut() {
    // dummyEmployee.setType(new ArrayList<>());
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
