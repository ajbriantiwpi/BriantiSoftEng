package edu.wpi.teamname;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenSaver extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Group root = new Group();

    // Add any desired visual elements to the root Group

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
