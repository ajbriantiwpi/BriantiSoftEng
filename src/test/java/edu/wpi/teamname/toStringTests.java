package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.navigation.LocationName;
import org.junit.jupiter.api.Test;

public class toStringTests {

  @Test
  public void loginToString() {
    Login login = new Login("test1", "test2");
    assertEquals("[test1, whvw2]", login.toString());
  }

  @Test
  public void edgeToString() {
    Edge edge = new Edge(1, 2);
    assertEquals("StartNodeID: 1 EndNodeID: 2", edge.toString());
  }

  @Test
  public void locationNameToString() {
  LocationName locationName = new LocationName("long", "short", "TYPE");
  assertEquals("[long, short, TYPE]", locationName.toString());
  }
}
