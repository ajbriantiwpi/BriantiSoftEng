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
      rec.setOpacity(0.8);

      root.getChildren().add(rec);
      FillTransition ft =
          new FillTransition(
              Duration.seconds(GlobalVariables.getScreenSaveTransTime()),
              rec,
              Color.BLACK,
              Color.BLACK);
      ft.setOnFinished(
          event -> {
            FillTransition ft2 =
                new FillTransition(
                    Duration.seconds(GlobalVariables.getScreenSaveTransTime()),
                    rec,
                    Color.BLACK,
                    Color.GREEN);
            rec.setOpacity(1);
            ft2.setOnFinished(
                event2 -> {
                  FillTransition ft3 =
                      new FillTransition(
                          Duration.seconds(GlobalVariables.getScreenSaveTransTime()),
                          rec,
                          Color.GREEN,
                          Color.RED);
                  ft3.setOnFinished(
                      event3 -> {
                        FillTransition ft4 =
                            new FillTransition(
                                Duration.seconds(GlobalVariables.getScreenSaveTransTime()),
                                rec,
                                Color.RED,
                                Color.MAGENTA);
                        ft4.setOnFinished(
                            event4 -> {
                              FillTransition ft5 =
                                  new FillTransition(
                                      Duration.seconds(GlobalVariables.getScreenSaveTransTime()),
                                      rec,
                                      Color.MAGENTA,
                                      Color.GOLD);
                              ft5.setOnFinished(
                                  event5 -> {
                                    FillTransition ft6 =
                                        new FillTransition(
                                            Duration.seconds(
                                                GlobalVariables.getScreenSaveTransTime()),
                                            rec,
                                            Color.GOLD,
                                            Color.TEAL);
                                    ft6.setOnFinished(
                                        event6 -> {
                                          FillTransition ft7 =
                                              new FillTransition(
                                                  Duration.seconds(
                                                      GlobalVariables.getScreenSaveTransTime()),
                                                  rec,
                                                  Color.TEAL,
                                                  Color.MAROON);
                                          ft7.setOnFinished(
                                              event7 -> {
                                                FillTransition ft8 =
                                                    new FillTransition(
                                                        Duration.seconds(
                                                            GlobalVariables
                                                                .getScreenSaveTransTime()),
                                                        rec,
                                                        Color.BLACK,
                                                        Color.BLUE);
                                                ft8.setOnFinished(
                                                    event8 -> {
                                                      ft8.play();
                                                    });
                                              });
                                          ft7.play();
                                        });
                                    ft6.play();
                                  });
                              ft5.play();
                            });
                        ft4.play();
                      });
                  ft3.play();
                });
            ft2.play();
          });
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
