package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    // App.launch(App.class, args);
    System.out.println(DataManager.getAllConferenceRequestIDs());
  }
  // shortcut: psvm
}
