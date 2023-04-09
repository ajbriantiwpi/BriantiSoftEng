package edu.wpi.teamname.navigation;

public enum EmergencyStatus {
  None, // no emergency -> green
  Level1, // small emergency (walking pace emergency, ex: cant reach tv remote) -> blue
  Level2, // medium emergency (jogging pace emergency, ex: fell off bed) -> Yellow
  Level3, // high emergency (sprinting pace emergency, ex: vitals stopped) -> Orange
  Level4 // building wide emergency (get the fuck out the building emergency, ex: fire) -> Flashing
  // Red
}
