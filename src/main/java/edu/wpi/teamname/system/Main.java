package edu.wpi.teamname.system;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    App.launch(App.class, args);
  }

  // shortcut: psvm
}
