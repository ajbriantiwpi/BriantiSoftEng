package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Map;
import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.kurobako.gesturefx.GesturePane;

public class MapController {

  Map map;
  @FXML GesturePane gp;
  @FXML AnchorPane anchor;

  EventHandler<MouseEvent> e =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          map.drawAStarPath(anchor, new Point2D(event.getX(), event.getY()));
        }
      };

  @FXML
  public void initialize() throws SQLException {

    map = new Map();

    gp.setMinScale(0.11);
    anchor.setOnMouseClicked(e);

    map.centerAndZoom(anchor);
  }
}
