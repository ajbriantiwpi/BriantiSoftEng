package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.navigation.*;
import org.junit.jupiter.api.Test;

public class EqualsTest {
  @Test
  public void edgeEquals() {
    Edge edge1 = new Edge(1, 2);
    Edge edge2 = new Edge(1, 2);
    Edge edge3 = new Edge(1, 3);
    Edge edge4 = new Edge(3, 2);
    assertTrue(edge1.equals(edge2));
    assertFalse(edge1.equals(edge3));
    assertFalse(edge4.equals(edge3));
  }

  @Test
  public void locationNameEquals() {
    LocationName locationName1 = new LocationName("long", "short", "type");
    LocationName locationName2 = new LocationName("long", "short", "type");
    LocationName locationName3 = new LocationName("short", "short", "type");
    LocationName locationName4 = new LocationName("HERER", "here", "NO");
    assertTrue(locationName1.equals(locationName2));
    assertFalse(locationName3.equals(locationName1));
    assertFalse(locationName3.equals(locationName4));
  }
}
