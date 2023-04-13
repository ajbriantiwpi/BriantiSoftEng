package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.system.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
  //  public Text text = new Text();

  Label label = new Label();

  public Point2D nodeCords;
  public int nodeID;

  public VBox v;
  public VBox v2;

  public Pane p;

  Color borderColor = Color.web("33567A"); // new Color(0.1, 0.4, 0.9, 1);
  Color insideColor = Color.web("2FA7B0"); // new Color(0.05, 0.7, 1, 1);
  float circleR = 10.0f;

  Color labelColor = new Color(.835, .89, 1, 1);
  Color labelText = new Color(0, .106, .231, 1);
  int lineTout = 2;

  /**
   * A NodeCircle is a circle that represents a Node in the GUI. It is composed of two circles, an inner one that
   *    is filled with a color that represents the Node type, and an outer one that serves as a border. It also contains
   *    a label that displays the Node's name. When the user hovers over a NodeCircle, the outer circle and the label
   *    become visible. When the user clicks on a NodeCircle, a VBox containing information about the Node is displayed.
   *    @param n The Node that this NodeCircle represents.
   *    @throws IOException If there was an error reading from the FXML file for the Node editing menu.
   */
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

    inner.setOpacity(0);
    outer.setOpacity(0);

    //    v.setOpacity(0);
    //    v.setDisable(true);

    //    text.setOpacity(0);
    p.setOnMouseEntered(makeVisible);
    p.setOnMouseExited(hide);
    p.setOnMouseClicked(boxVisible);
  }

  /**
   *    A mouse event handler that hides the VBox containing information about a NodeCircle
   *    when the user's mouse exits the NodeCircle.
   */
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

  /**
   *    An event handler to make a node visible on mouse click.
   *    <p>This handler sets the opacity of the clicked node's parent pane and all its children to 1,
   *    except for any VBoxes which are excluded. This is used to show a previously hidden node and
   *    its contents.
   *    @param event the mouse event triggering the handler
   */
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
          //          p.setBackground(Background.fill(Color.RED));
          for (javafx.scene.Node n : p.getChildren()) {
            if (!(n.getClass() == VBox.class)) {
              n.setOpacity(1);
            }
          }
        }
      };

  /**
   *    An event handler to remove a node on mouse click.
   *    <p>This handler removes the node associated with the clicked button from the parent VBox and
   *    deletes it from the database using the node ID. If an exception occurs during the deletion
   *    process, it is caught and printed to the console.
   *    @param event the mouse event triggering the handler
   */
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
            //            DataManager.deleteLocationName
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

          //          TextField locText = (TextField) ((Pane)
          // (v.getChildren().get(0))).getChildren().get(1);
          TextField xText = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField yText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField floorText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);
          TextField buildingText =
              (TextField) ((Pane) (v.getChildren().get(3))).getChildren().get(1);

          Node currNode = null;
          try {
            currNode = DataManager.getSingleNodeInfo(nodeID).get(0);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }

          int xPos = currNode.getX();
          if (!(xText.getText().equals(""))) {
            xPos = (int) Double.parseDouble(xText.getText());
          }
          int yPos = currNode.getY();
          if (!(yText.getText().equals(""))) {
            yPos = (int) Double.parseDouble(yText.getText());
          }
          String floor = currNode.getFloor();
          if (!(floorText.getText().equals(""))) {
            floor = floorText.getText();
          }
          String building = currNode.getBuilding();
          if (!(buildingText.getText().equals(""))) {
            building = buildingText.getText();
          }

          ArrayList<Node> allNodes;
          try {
            allNodes = DataManager.getAllNodes();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          // This is working on the assumption that We still want all id's to be a difference of 5
          // and that the last one in the table is the biggest ID
          int highestID = allNodes.get(allNodes.size() - 1).getId();

          Node n = new Node(nodeID, xPos, yPos, floor, building);

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

  /**
   * EventHandler for saving changes made to a node in the system.
   * This EventHandler is triggered when the "Save Changes" button is clicked on the edit node screen.
   * It retrieves the updated information from the relevant TextFields and constructs a new Node object
   * with the updated information. It then calls the DataManager to update the information in the system
   * database, and prints a message to the console to confirm that the synchronization has been completed.
   * @param event The MouseEvent that triggered the EventHandler.
   * @throws RuntimeException if an SQL exception occurs during the data synchronization process.
   */
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
          try {
            System.out.println("Start");
            ArrayList<String> nodeInfo =
                DataManager.getUpdatedNodeInfo(nodeID, Timestamp.from(Instant.now()));
            System.out.println(nodeInfo);
            nodeInfo.add("Empty");
            // Long Name, Building, Floor
            Location.setText(nodeInfo.get(0));
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
          //            } catch (SQLException e) {
          //              throw new RuntimeException(e);
          //            }
          // Set X
          TextField XText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          XText.setText("" + nodeCords.getX());
          // Set Y
          TextField YText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);
          YText.setText("" + nodeCords.getY());

          Node currNode = null;
          try {
            currNode = DataManager.getSingleNodeInfo(nodeID).get(0);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }

          TextField floorText = (TextField) ((Pane) (v.getChildren().get(3))).getChildren().get(1);
          floorText.setText(currNode.getFloor());
          TextField buildingText =
              (TextField) ((Pane) (v.getChildren().get(4))).getChildren().get(1);
          buildingText.setText(currNode.getBuilding());

          // Set Remove Node. On Click
          MFXButton removeNodeButton =
              (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(0);
          removeNodeButton.setOnMouseClicked(removeNode);
          // Set Submit
          MFXButton submitButton =
              (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(1);
          submitButton.setOnMouseClicked(saveNodeChanges);

          v.getChildren().remove(6);
          v.getChildren().remove(5);
          v.getChildren().remove(0);

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
