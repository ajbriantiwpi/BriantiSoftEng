package edu.wpi.teamname.navigation;

import edu.wpi.teamname.App;
import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class EdgeRectangle {

  public Pane p;
  public Path path;
  public Label label = new Label();
  private VBox changeBox;

  private Node startNode;
  private Node endNode;

  public EdgeRectangle(Node startNode, Node endNode) {
    this.startNode = startNode;
    this.endNode = endNode;

    p = new Pane();

    for (int j = 0; j < 2; j++) {
      path = new Path();
      if (j == 0) {
        path.setStroke(GlobalVariables.getBorderColor());
      } else {
        path.setStroke(GlobalVariables.getInsideColor());
      }
      path.setStrokeWidth(
          GlobalVariables.getLineT() - (GlobalVariables.getStrokeThickness() * 2 * j));

      path.getElements().add(new MoveTo(this.startNode.getX(), this.startNode.getY()));
      path.getElements().add(new LineTo(this.endNode.getX(), this.endNode.getY()));

      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
      p.getChildren().add(path);
    }

    p.setOnMouseClicked();

    p.getChildren().add(this.label);
  }

  EventHandler<MouseEvent> boxVisible =
    new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        Pane p = ((Pane) event.getSource());
        p.setOpacity(1);

        final var resource = App.class.getResource("views/ChangeNode.fxml");
        final FXMLLoader loader = new FXMLLoader(resource);
        try {
          changeBox = loader.load();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        // Set Location Name
        TextField Location =
                (TextField) ((Pane) (changeBox.getChildren().get(0))).getChildren().get(1);

        HashMap<Integer, ArrayList<LocationName>> map;
        try {
          map =
                  DataManager.getAllLocationNamesMappedByNode(
                          new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }

        //          for (Integer key : map.keySet()) {
        //            System.out.println("Key: " + key + " Val: " + map.get(key));
        //          }
        //          System.out.println(map);

        String location;
        if (map.get(nodeID).size() > 0) {
          location = map.get(nodeID).get(0).getLongName();
        } else {
          location = "" + nodeID;
        }
        Location.setText(location);

        // Set X
        TextField XText =
                (TextField) ((Pane) (changeBox.getChildren().get(1))).getChildren().get(1);
        XText.setText("" + nodeCords.getX());
        // Set Y
        TextField YText =
                (TextField) ((Pane) (changeBox.getChildren().get(2))).getChildren().get(1);
        YText.setText("" + nodeCords.getY());

        Node currNode = null;
        try {
          currNode = DataManager.getNode(nodeID);
          //            System.out.println("Done: " + currNode.toString());
        } catch (SQLException e) {
          System.out.println("ERROR: " + e.toString());
          throw new RuntimeException(e);
        }

        TextField floorText =
                (TextField) ((Pane) (changeBox.getChildren().get(3))).getChildren().get(1);

        TextField buildingText =
                (TextField) ((Pane) (changeBox.getChildren().get(4))).getChildren().get(1);

        if (currNode != null) {
          floorText.setText(currNode.getFloor());
          buildingText.setText(currNode.getBuilding());
        }

        // Set Remove Node. On Click
        MFXButton removeNodeButton =
                (MFXButton) ((Pane) (changeBox.getChildren().get(7))).getChildren().get(0);
        removeNodeButton.setOnMouseClicked(removeNode);
        // Set Submit
        MFXButton submitButton =
                (MFXButton) ((Pane) (changeBox.getChildren().get(7))).getChildren().get(1);
        submitButton.setOnMouseClicked(saveNodeChanges);

        changeBox.getChildren().remove(6);
        changeBox.getChildren().remove(5);
        //          changeBox.getChildren().remove(0);

        System.out.println("AddBox");

        PopOver pop = new PopOver(changeBox);
        pop.show(inner);

        //          p.getChildren().addAll(changeBox);

        //
      }
    };
}
