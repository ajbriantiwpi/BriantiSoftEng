package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
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
    Employee l = new Employee("admin", "admin");
    assertEquals(l.LogInto(), true);
  }

  @Test
  public void testLogintofail() throws SQLException {
    Employee l = new Employee("", "");
    assertEquals(l.LogInto(), false);
  }

  @Test
  public void setLogin() throws SQLException {
    Employee l = new Employee("", "");
    l.setLogin("Hunter1!", "Poulin");
    // if inputed things r in table it works:
  }

  @Test
  public void testResetPass() throws SQLException {
    Employee l = new Employee("Hunter1!", "Poulin");
    assertEquals(l.resetPass("Wong"), "Wong");
    // in table if password is not "Poulin anymore"
  }

  @Test
  public void testEncrypt() {
    Employee l = new Employee("user", "toencypt");
    assertEquals("wrhqfbsw", l.getPassword());
  }

  @Test
  public void testLegalLogin() {
    Employee l = new Employee("user", "pass");
    assertTrue(!l.checkLegalLogin("small"));
  }

  @Test
  public void testLegalLogin2() {
    Employee l = new Employee("user", "pass");
    assertTrue(!l.checkLegalLogin(";;;;badtest"));
  }

  @Test
  public void testLegalLogin3() {
    Employee l = new Employee("user", "pass");
    assertTrue(l.checkLegalLogin("GoodPassword2!"));
  }
}
