package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    GlobalVariables.setCurrentUser(DataManager.getEmployee("ian"));
    App.launch(App.class, args);
  }
  // shortcut: psvm
}
