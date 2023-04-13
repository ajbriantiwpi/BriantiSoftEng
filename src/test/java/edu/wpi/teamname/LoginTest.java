package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.Login;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
  // assertEquals(converter.toKibenian(), "I");

  @BeforeEach
  void setup() {
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
  }

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

  @Test
  public void testEncrypt() {
    Login l = new Login("user", "toencypt");
    assertEquals("wrhqfbsw", l.getPassword());
  }

  @Test
  public void testLegalLogin() {
    Login l = new Login("user", "pass");
    assertTrue(!l.checkLegalLogin("small"));
  }

  @Test
  public void testLegalLogin2() {
    Login l = new Login("user", "pass");
    assertTrue(!l.checkLegalLogin(";;;;badtest"));
  }

  @Test
  public void testLegalLogin3() {
    Login l = new Login("user", "pass");
    assertTrue(l.checkLegalLogin("GoodPassword2!"));
  }
}
