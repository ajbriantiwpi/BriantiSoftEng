package edu.wpi.teamname;

import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.servicerequest.ConfReservation;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class GlobalVariables {
  // language is set to English by default
  @Getter public static final char aGrave = '\u00E0';
  @Getter public static final char bigAGrave = '\u00C0';
  @Getter public static final char aAcute = '\u00E1';
  @Getter public static final char bigAAcute = '\u00C1';
  // e acute
  @Getter public static final char eAcute = '\u00E9';
  @Getter public static final char bigEACute = '\u00C9';
  @Getter public static final char eGrave = '\u00E8';
  @Getter public static final char bigEGrave = '\u00C8';
  @Getter public static final char oAcute = '\u00F3';
  @Getter public static final char bigOACute = '\u00D3';
  @Getter public static final char oGrave = '\u00F2';
  @Getter public static final char bigOGrave = '\u00D2';
  @Getter public static final char iAcute = '\u00EC';
  @Getter public static final char bigIACute = '\u00CC';
  @Getter public static final char iGrave = '\u00ED';
  @Getter public static final char bigIGrave = '\u00CD';


  @Getter @Setter public static Property<Language> b = new SimpleObjectProperty<>(Language.ENGLISH);
  @Getter @Setter private static String[] args;
  @Getter @Setter private static boolean futureMovesPressed = false;
  @Getter @Setter private static boolean activeRequestsPressed = false;
  @Getter @Setter private static boolean doneRequestsPressed = false;
  @Getter @Setter private static boolean requestFromMap = false;
  @Getter @Setter private static String roomFromMap = "";
  @Getter @Setter private static Timestamp dateFromMap = null;
  @Getter @Setter private static int roomIDFromMap = 0;
  @Getter @Setter private static Color borderColor = Color.web("012D5A"); // Color.web("33567A");
  @Getter @Setter private static Color insideColor = Color.web("35A7FF"); // Color.web("2FA7B0");
  @Getter @Setter private static Color insideBlue = Color.web("35A7FF");
  @Getter @Setter private static Color insideGreen = Color.web("6BD425");
  @Getter @Setter private static Color insideOrange = Color.web("EDB230");
  @Getter @Setter private static Color insideYellow = Color.web("FEEA00");
  @Getter @Setter private static Color insideRed = Color.web("ED6A5A");
  @Getter @Setter private static Color insideMagenta = Color.web("8D5A97");
  @Getter @Setter private static Color insideWhite = Color.web("FFFFFF");

  @Getter @Setter private static float circleR = 10.0f;
  @Getter @Setter private static float lineT = 10.0f;
  @Getter @Setter private static int strokeThickness = 2;
  @Getter @Setter private static Color labelColor = new Color(.835, .89, 1, 1);
  @Getter @Setter private static Color labelTextColor = new Color(0, .106, .231, 1);
  @Getter @Setter private static Timestamp today = new Timestamp(System.currentTimeMillis());
  @Getter @Setter private static HashMap<Integer, ArrayList<LocationName>> hMap;

  @Getter @Setter private static ArrayList<ServiceRequest> serviceRequests;
  @Getter @Setter private static ArrayList<ConfReservation> confReservations;
  @Getter @Setter private static ArrayList<ConfRoom> confRooms;

  @Getter @Setter private static ArrayList<ArrayList<ConfReservation>> allRes;

  //  @Getter @Setter private static Color labelColor = new Color(.835, .89, 1, 1);
  //  @Getter @Setter private static Color labelTextColor = new Color(0, .106, .231, 1);

  public static int roomNumToIndex(int roomNum) {
    ArrayList<Integer> vals =
        new ArrayList<>(Arrays.asList(290, 1335, 1685, 1690, 1695, 1110, 1860));
    return vals.indexOf(roomNum);
  }

  private static final Employee dummyEmployee =
      new Employee(
          "dummyU",
          "dummyP",
          -1,
          "dummmyF",
          "dummyL",
          ClearanceLevel.GUEST,
          EmployeeType.NONE,
          false);
  @Getter @Setter private static Employee currentUser = dummyEmployee;
  @Getter @Setter private static Screen currentScreen = Screen.HOME;
  @Getter @Setter private static Screen previousScreen = Screen.HOME;

  @Getter @Setter private static BooleanProperty darkMode = new SimpleBooleanProperty(false) {};
  @Getter @Setter private static Boolean showServiceIcons = new Boolean(true);
  @Getter @Setter private static Boolean showConfItems = new Boolean(true);

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
    return currentUser.getType().equals(type);
  }

  /**
   * Checks whether the current logged-in user is the given level
   *
   * @param level the level that is being checked if the user is
   * @return whether the user is of that level
   */
  public static boolean userIsClearanceLevel(ClearanceLevel level) {
    return currentUser.getLevel().equals(level);
  }
}
