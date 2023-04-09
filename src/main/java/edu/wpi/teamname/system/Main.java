package edu.wpi.teamname.system;

import edu.wpi.teamname.database.DataManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    App.launch(App.class, args);
  }

  // shortcut: psvm
}
