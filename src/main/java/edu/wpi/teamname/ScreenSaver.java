package edu.wpi.teamname;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;

public class ScreenSaver extends Application {

  private static int count = 0;
  private static double WIDTH;
  private static double HEIGHT;
  private static final int CIRCLE_RADIUS = 50;
  private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
  private Timeline timeline;
  private static boolean screenSaverOn = false;

  //  private Pane root;
  //  private Scene scene;

  public ScreenSaver() {}

  @Override
  public void start(Stage primaryStage) throws Exception {
    //    // Load the image from a file
    //    Image image = new Image("C:\\Users\\Aleksandr Samarin\\Desktop\\images.png");
    //
    //    // Create an ImageView object to display the image
    //    ImageView imageView = new ImageView(image);
    //
    //    // Set the size and position of the ImageView to fill the scene
    //    imageView.setFitWidth(primaryStage.getWidth());
    //    imageView.setFitHeight(primaryStage.getHeight());
    //
    //    Group root = new Group(imageView);
    //
    //    // Add any desired visual elements to the root Group
    //
    //    Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
    //    primaryStage.setScene(scene);
    //    primaryStage.show();

    //    primaryStage.setScene(scene);
    //    primaryStage.show();
    startScreenSaver(primaryStage);
  }

  public void startScreenSaver(Stage primaryStage) {
    WIDTH = 35;
    HEIGHT = 35;
    System.out.println(WIDTH);
    System.out.println(HEIGHT);

    ImageView iView = new ImageView("edu/wpi/teamname/images/logo.png");

    iView.setFitHeight(HEIGHT);
    iView.setFitWidth(WIDTH);

    Pane pane = new Pane(iView);
    // StackPane sp = App.getRootPane();
    // sp.getChildren().add(pane);

    Scene scene = new Scene(pane, WIDTH, HEIGHT);//shit balls error

    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  double x = Math.random() * (primaryStage.getWidth() - iView.getFitWidth());
                  double y = Math.random() * (primaryStage.getHeight() - iView.getFitHeight());
                  iView.setLayoutX(x);
                  iView.setLayoutY(y);
                }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    // Scene scene = new Scene(paneRoot, 35, 35, Color.BLACK);

    primaryStage.setScene(scene);
    primaryStage.show();
    screenSaverOn = true;
  }

  public void stopScreenSaver(Pane root) {
    if (timeline != null) {
      timeline.stop();
    }
    if (screenSaverOn) {
      count = 0;
      root.getChildren().remove(0);
    }
  }
}
