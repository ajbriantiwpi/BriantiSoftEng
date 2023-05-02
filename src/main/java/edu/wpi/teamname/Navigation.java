package edu.wpi.teamname;

import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Navigation {
  private static boolean first = true;

  public static void navigate(final Screen screen) {
    if (!first) {
      Sound.playSFX(SFX.BUTTONCLICK);
    } else {
      first = false;
    }

    final String filename = screen.getFilename();

    try {
      //      final var resource = App.class.getResource(filename);
      //      final FXMLLoader loader = new FXMLLoader(resource);
      //
      //      App.getRootPane().setCenter(loader.load());
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      System.out.println(filename);
      Pane p = loader.load();
      Pane PFinal;
      if (!(filename.equals("views/Home.fxml")) && !(filename.equals("views/Login.fxml"))) {
        System.out.println("Here");
        final FXMLLoader loader2 = new FXMLLoader(App.class.getResource("views/Template.fxml"));
        final Pane root = loader2.load();
        Pane inner =
            (Pane)
                ((Pane) ((Pane) (root.getChildren().get(0))).getChildren().get(1))
                    .getChildren()
                    .get(1);
        System.out.println(p.getId());
        HBox.setHgrow(p, Priority.ALWAYS);
        inner.getChildren().add(p);
        PFinal = root;
      } else {
        PFinal = p;
      }
      System.out.println("Loaded");
      App.getRootPane().setCenter(PFinal);
      GlobalVariables.setPreviousScreen(GlobalVariables.getCurrentScreen());
      GlobalVariables.setCurrentScreen(screen);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
