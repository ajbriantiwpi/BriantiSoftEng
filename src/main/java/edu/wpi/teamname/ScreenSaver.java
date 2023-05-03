package edu.wpi.teamname;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;

public class ScreenSaver extends Application {

  private static double WIDTH;
  private static double HEIGHT;
  private static final int CIRCLE_RADIUS = 50;
  private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
  private Timeline timeline;
  private static boolean screenSaverOn = false;
  private static Rectangle rec;

  //  private Pane root;
  //  private Scene scene;

  public ScreenSaver() {}

  @Override
  public void start(Stage primaryStage) throws Exception {}

  public void startScreenSaver(BorderPane root, Stage primaryStage) {
    if (!screenSaverOn) {
      WIDTH = root.getWidth();
      HEIGHT = root.getHeight();
      System.out.println(WIDTH);
      System.out.println(HEIGHT);

      rec = new Rectangle(0, 0, WIDTH, HEIGHT);
      root.getChildren().add(rec);
      FillTransition ft = new FillTransition(Duration.seconds(30), rec, Color.BLACK, Color.RED);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.GREEN, Color.YELLOW);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.YELLOW, Color.BLUE);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.BLUE, Color.MAGENTA);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.MAGENTA, Color.GOLD);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.GOLD, Color.TEAL);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.TEAL, Color.MAROON);
      ft.play();
      ft = new FillTransition(Duration.seconds(30), rec, Color.MAROON, Color.BLACK);
      ft.play();

      timeline = new Timeline(new KeyFrame(Duration.seconds(30), event -> {}));

      primaryStage.show();
      screenSaverOn = true;
    }
  }

  public void stopScreenSaver(Pane root) {
    // System.out.println("stop");
    if (timeline != null) {
      timeline.stop();
      if (screenSaverOn) {
        // System.out.println("Remove rec");
        root.getChildren().removeIf(node -> node instanceof Rectangle);
        screenSaverOn = false;
      }
    }
  }
}
