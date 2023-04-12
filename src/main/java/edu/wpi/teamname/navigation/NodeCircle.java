package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.system.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeCircle {
  public Circle inner;
  public Circle outer;

  Label label = new Label();

  public Point2D nodeCords;
  public int nodeID;

  public VBox v;

  public Pane p;

  Color borderColor = new Color(0.1, 0.4, 0.9, 1);
  Color insideColor = new Color(0.05, 0.7, 1, 1);
  Color labelColor = new Color(.835, .89, 1, 1);
  Color labelText = new Color(0, .106, .231, 1);
  float circleR = 7.0f;
  float lineT = 10.0f;
  int lineTout = 2;

  public NodeCircle(Node n) throws IOException {
    float shiftX = 0; // circleR;
    float shiftY = 0; // circleR;

    nodeCords = new Point2D(n.getX(), n.getY());
    nodeID = n.getId();

    this.outer = new Circle(shiftX, shiftY, circleR + lineTout);
    outer.setFill(borderColor);
    outer.setOpacity(0);
    this.inner = new Circle(shiftX, shiftY, circleR);
    inner.setFill(insideColor);
    inner.setOpacity(0);

    ArrayList<String> nameType = new ArrayList<>();
    try {
      nameType = n.getShortName();
    } catch (SQLException ex) {
      System.out.println(ex.toString());
      System.out.println("Could not find info");
    }
    String shortName = "";
    String nodeType = "";
    if (nameType.size() == 2) {
      shortName = nameType.get(0);
      nodeType = nameType.get(1);
    } else {
    }

    label.setText(shortName);
    // label.setText("HELLO");
    CornerRadii corn = new CornerRadii(7);
    label.setBackground(new Background(new BackgroundFill(labelColor, corn, Insets.EMPTY)));
    label.setTextFill(labelText);
    label.setTranslateX(n.getX() - 35);
    label.setTranslateY(n.getY() - 30);
    //    final var resource = App.class.getResource("../views/ChangeNode.fxml");
    //    final FXMLLoader loader = new FXMLLoader(resource);
    //    v = loader.load();

    p = new Pane();

    p.getChildren().addAll(this.outer, this.inner);

    float boxW = circleR;
    float boxH = circleR;

    p.setTranslateX(n.getX() - shiftX);
    p.setTranslateY(n.getY() - shiftY);
    p.setMaxWidth(boxW);
    p.setMaxHeight(boxH);

    //    v.setTranslateY(-200);

    //    inner.setOpacity(0);
    //    outer.setOpacity(0);

    //    v.setOpacity(0);
    //    v.setDisable(true);

    //    text.setOpacity(0);
    p.setOnMouseEntered(makeVisible);
    p.setOnMouseExited(hide);
    p.setOnMouseClicked(boxVisible);
  }

  EventHandler<MouseEvent> hide =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Pane p = ((Pane) event.getSource());
          for (javafx.scene.Node n : p.getChildren()) {
            if (n.getClass() == VBox.class) {
              p.getChildren().remove(n);
              break;
            }
          }

          for (javafx.scene.Node n : p.getChildren()) {
            if (n.getClass() == VBox.class) {
              n.setOpacity(0);
            }
          }
          //          v = null;
          p.setOpacity(0);
          //          Circle outer = ((Circle) event.getSource());
          //          Circle inner = ((Circle) event.getSource());
          //          inner.setOpacity(0);
          //          outer.setOpacity(0);
          //          text.setOpacity(1);
        }
      };

  EventHandler<MouseEvent> makeVisible =
      new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          //          Circle outer = ((Circle) event.getSource());
          //          Circle inner = ((Circle) event.getSource());
          //          inner.setOpacity(1);
          //          outer.setOpacity(1);
          //          text.setOpacity(0);
          Pane p = ((Pane) event.getSource());
          p.setOpacity(1);
          p.setBackground(Background.fill(Color.RED));
          for (javafx.scene.Node n : p.getChildren()) {
            if (!(n.getClass() == VBox.class)) {
              n.setOpacity(1);
            }
          }
        }
      };

  EventHandler<MouseEvent> removeNode =
      new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          //        removeNodeByID(nodeID);
          System.out.println("REM");
          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((HBox) SubmitButton.getParent()).getParent();

          //          for (javafx.scene.Node n : p.getChildren()) {
          //            if (n.getClass() == VBox.class) {
          //              p.getChildren().remove(n);
          //              break;
          //            }
          //          }

          // Only the Node ID is important for Deletion
          Node n = new Node(nodeID, 0, 0, "", "");

          try {
            DataManager.deleteNode(n);
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> saveNodeChanges =
      new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((HBox) SubmitButton.getParent()).getParent();

          TextField locText = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField xText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField yText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);

          //          String locationNameValue = popupVbox.g

          System.out.println(
              locText.getText() + ", " + xText.getText() + ", " + yText.getText() + "2");

          //          int highestID = 3000;
          String currentFloor = "L1";
          String currentBuilding = "45 Francis";

          Node n =
              new Node(
                  nodeID,
                  (int) Double.parseDouble(xText.getText()),
                  (int) Double.parseDouble(yText.getText()),
                  currentFloor,
                  currentBuilding);

          try {
            DataManager.syncNode(n);
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }

          System.out.println("DONE SYNC");
          // Update Based On text
        }
      };

  EventHandler<MouseEvent> boxVisible =
      new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          Pane p = ((Pane) event.getSource());
          p.setOpacity(1);
          p.setBackground(Background.fill(Color.RED));

          final var resource = App.class.getResource("../views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          // Set Location Name
          TextField Location = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          Location.setText("LOC");
          // Set X
          TextField XText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          XText.setText("" + nodeCords.getX());
          // Set Y
          TextField YText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);
          YText.setText("" + nodeCords.getY());
          // Set Remove Node. On Click
          MFXButton removeNodeButton =
              (MFXButton) ((Pane) (v.getChildren().get(3))).getChildren().get(0);
          removeNodeButton.setOnMouseClicked(removeNode);
          // Set Submit
          MFXButton submitButton =
              (MFXButton) ((Pane) (v.getChildren().get(3))).getChildren().get(1);
          submitButton.setOnMouseClicked(saveNodeChanges);

          p.getChildren().addAll(v);

          System.out.println("ADDV");

          //          for (javafx.scene.Node n : p.getChildren()) {
          //            if ((n.getClass() == VBox.class)) {
          //              n.setOpacity(1);
          //              n.setDisable(false);
          //            }
          //          }
        }
      };
}
