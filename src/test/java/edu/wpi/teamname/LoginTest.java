package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.teamname.database.Login;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LoginTest {
  // assertEquals(converter.toKibenian(), "I");

  @Test
  public void testLoginto() throws SQLException {
    Login l = new Login("admin", "admin");
    assertEquals(l.LogInto(), true);
  }

  @Test
  public void testLogintofail() throws SQLException {
    Login l = new Login("", "");
    assertEquals(l.LogInto(), false);
  }

  @Test
  public void setLogin() throws SQLException {
    Login l = new Login("", "");
    l.setLogin("Hunter1!", "Poulin");
    // if inputed things r in table it works:
  }

  @Test
  public void testResetPass() throws SQLException {
    Login l = new Login("Hunter1!", "Poulin");
    assertEquals(l.resetPass("Wong"), "Wong");
    // in table if password is not "Poulin anymore"
  }
}
