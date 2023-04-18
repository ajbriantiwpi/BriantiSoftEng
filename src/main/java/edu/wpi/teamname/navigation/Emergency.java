package edu.wpi.teamname.navigation;

public class Emergency {

  public MapNode emergencyPosition;

  public Emergency() {}

  /**
   * sets emergency level status to None
   *
   * @param e
   */
  public static void resolveEmergency(Emergency e) {}

  /**
   * Sets given node to which ever level status it should be based on input
   *
   * @param n
   */
  public void updateEmergencyNode(MapNode n) {}

  /**
   * Sets given nodes emergency status
   *
   * @param lvl
   * @return
   */
  public EmergencyStatus status(int lvl) {
    return null;
  }
}
