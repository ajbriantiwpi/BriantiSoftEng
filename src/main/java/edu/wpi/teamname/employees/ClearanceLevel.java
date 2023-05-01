package edu.wpi.teamname.employees;

import edu.wpi.teamname.Screen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ClearanceLevel {
  ADMIN("ADMIN"),
  STAFF("STAFF"),
  GUEST("GUEST"),
  ;

  private final String level;

  ClearanceLevel(String level) {
    this.level = level;
  }

  public String getString() {
    return level;
  }

  public List<Screen> accessableScreens() {
    switch (this) {
      case ADMIN:
        List<Screen> allScreens = Arrays.stream(Screen.values()).toList();
        return allScreens;
      case STAFF:
        List<Screen> staffScreens = ClearanceLevel.GUEST.accessableScreens();

        staffScreens.add(Screen.SERVICE_REQUEST);
        staffScreens.add(Screen.SERVICE_REQUEST_VIEW);
        staffScreens.add(Screen.MOVE_TABLE);
        staffScreens.add(Screen.CONFERENCE_ROOM);
        staffScreens.add(Screen.SERVICE_REQUEST_ANALYTICS);

        return staffScreens;
      case GUEST:
        ArrayList<Screen> guestScreens = new ArrayList<>();
        guestScreens.add(Screen.HOME);
        guestScreens.add(Screen.MAP);
        guestScreens.add(Screen.SIGNAGE);
        guestScreens.add(Screen.LOGIN);
        return guestScreens;
    }
    return null;
  }

  /**
   * * Gets the string values of all of the ClearanceLevels and returns a string array of all of
   * them formatted It is formatted with first letter is capitalized and the rest is lowercase
   *
   * @return The string array with all formatted values
   */
  public static String[] formattedValues() {
    String[] output = new String[values().length];
    String string = "";
    for (int i = 0; i < values().length; i++) {
      string = values()[i].getString();
      output[i] = string.substring(0, 1) + string.substring(1).toLowerCase();
    }
    return output;
  }
}
