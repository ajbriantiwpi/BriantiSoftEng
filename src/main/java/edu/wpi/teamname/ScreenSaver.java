package edu.wpi.teamname;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreenSaver extends Application {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final int CIRCLE_RADIUS = 50;
  private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
  private Timeline timeline;

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

    Pane root = new Pane();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
    startScreenSaver(root);
  }

  public void startScreenSaver(Pane root) {
    for (int i = 0; i < 10; i++) {
      Circle circle = new Circle(CIRCLE_RADIUS, COLORS[i % COLORS.length]);
      circle.relocate(
          Math.random() * (WIDTH - 2 * CIRCLE_RADIUS),
          Math.random() * (HEIGHT - 2 * CIRCLE_RADIUS));
      root.getChildren().add(circle);

      timeline =
          new Timeline(
              new KeyFrame(
                  Duration.seconds(1),
                  e -> {
                    double dx = Math.random() * 10 - 5;
                    double dy = Math.random() * 10 - 5;
                    circle.setLayoutX(circle.getLayoutX() + dx);
                    circle.setLayoutY(circle.getLayoutY() + dy);
                  }));
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
    }
  }

  public void stopScreenSaver(Pane root) {
    if (timeline != null) {
      timeline.stop();
    }
    root.getChildren().removeIf(node -> node instanceof Circle);
  }
}
