package edu.wpi.teamname;

import javafx.scene.layout.Pane;

public class ThemeSwitch {
  public static void switchTheme(Pane pane) {
    if (GlobalVariables.getDarkMode().get()) {
      pane.getStylesheets().remove(0);
    } else {
      pane.getStylesheets().remove(1);
    }
  }
}
