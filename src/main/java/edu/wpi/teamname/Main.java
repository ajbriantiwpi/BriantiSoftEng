package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {

    /*DataManager.configConnection(
    "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
    "teamd",
    "teamd40");*/
    DataManager.configConnection(
        "jdbc:postgres://database-1.cwgmodw6cdg6.us-east-1.rds.amazonaws.com:5432/database-1?",
        "postgres",
        "teamd4000");
    DataManager.DbConnection();
    // App.launch(App.class, args);

  }

  // shortcut: psvm
}
