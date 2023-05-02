package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    GlobalVariables.setArgs(args);
    App.launch(App.class, args);
    try {
      Connection connection = DataManager.DbConnection();
      connection.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    System.exit(0);
  }
  // shortcut: psvm
}
