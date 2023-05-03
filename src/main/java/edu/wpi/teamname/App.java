package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Sound;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  // Text idleText = new Text("Idle for: 0 seconds");
  Timeline idleTimeline;
  boolean flag = false;
  AtomicInteger count = new AtomicInteger();

  // ScreenSaver ss = new ScreenSaver();
  private boolean loading;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException, SQLException, URISyntaxException {
    /* primaryStage is generally only used if one of your components require the stage to display */

    Sound.playMusic();

    App.primaryStage = primaryStage;
    primaryStage.setFullScreen(true);

    DataManager.connectToWPI();

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/Root.fxml"));
    final BorderPane root = loader.load();

    App.rootPane = root;

    final Scene scene2 = new Scene(root);
    primaryStage.setScene(scene2);
    primaryStage.show();

    Navigation.navigate(Screen.HOME);

    // -------------SCREEN SAVER----------------

    root.getScene()
        .addEventFilter(
            KeyEvent.KEY_PRESSED,
            event -> {
              // System.out.println(event);
              resetIdleTimeline(root);
            });
    root.getScene()
        .addEventFilter(
            MouseEvent.MOUSE_MOVED,
            event -> {
              // System.out.println("Mouse Moved");
              resetIdleTimeline(root);
            });

    // Create a timeline to track idle time
    idleTimeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  count.getAndIncrement();
                  // Update the idle time display
                  // System.out.println("Time: " + count);

                  // Do something when the idle time exceeds a certain duration
                  if (count.get() == GlobalVariables.getScreenSaveWaitTime() && !flag) {
                    flag = true;
                    GlobalVariables.getSs().startScreenSaver(root, primaryStage);
                  }
                }));
    idleTimeline.setCycleCount(Timeline.INDEFINITE);
    idleTimeline.play();
  }

  private void resetIdleTimeline(BorderPane root) {
    // System.out.println("This ran");
    count.getAndSet(0);
    flag = false;
    GlobalVariables.getSs().stopScreenSaver(root);
    idleTimeline.stop();
    idleTimeline.playFromStart();
  }

  @Override
  public void stop() throws SQLException {
    Connection connection = DataManager.DbConnection();
    connection.close();
    log.info("Shutting Down");
  }
}
