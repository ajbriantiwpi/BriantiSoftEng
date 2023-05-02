package edu.wpi.teamname;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    // ScreenSaver.launch(ScreenSaver.class, args);
    GlobalVariables.setArgs(args);
    App.launch(App.class, args);
  }
  // shortcut: psvm
}
