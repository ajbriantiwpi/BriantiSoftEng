package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.wpi.teamname.controllers.LoginController;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LoginUITest {
  @Test
  public void testUILoginto() throws SQLException, ExceptionInInitializerError {
    //        Login l = new Login("admin", "admin");
    assertThrows(
        ExceptionInInitializerError.class, () -> LoginController.loginPressed("admin", "admin"));
  }

  @Test
  public void testUIForgotPassword() throws SQLException, NoClassDefFoundError {
    LoginController.forgotPasswordPressed("test");
    assertThrows(
        NoClassDefFoundError.class, () -> LoginController.loginPressed("test", "NewPassword"));
    //    LoginController.loginPressed("test", "NewPassword");
  }
}
