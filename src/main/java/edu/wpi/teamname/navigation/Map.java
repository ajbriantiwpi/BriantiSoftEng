package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import lombok.Getter;
import lombok.Setter;
import net.kurobako.gesturefx.GesturePane;

public class Map {
  private Color borderColor = Color.web("33567A");
  private Color insideColor = Color.web("2FA7B0");
  private float circleR = 10.0f;
  private float lineT = 10.0f;
  private int lineTout = 2;
  public Graph graph;
  public ArrayList<Emergency> emergencies;
  private Point2D centerPoint;
  private Point2D centerTL;
  @Getter @Setter private ArrayList<Shape> prevPath = new ArrayList<Shape>();

  public Map() throws SQLException {
    this.graph = new Graph();
  }

  /**
   * Creates a path with circles representing the nodes from the given list of nodes.
   *
   * @param nodes List of nodes to create the path from
   * @return An ArrayList of Shape objects representing the path
   */
  private ArrayList<Shape> makeShapePath(ArrayList<Node> nodes) {
    ArrayList<Shape> shapes = new ArrayList<Shape>();

    Circle c;
    Path path;

    for (int j = 0; j < 2; j++) {
      path = new Path();
      if (j == 0) {
        path.setStroke(borderColor);
      } else {
        path.setStroke(insideColor);
      }
      path.setStrokeWidth(lineT - (lineTout * 2 * j));
      path.getElements().add(new MoveTo(nodes.get(0).getX(), nodes.get(0).getY()));

      for (int i = 1; i < nodes.size(); i++) {
        path.getElements().add(new LineTo(nodes.get(i).getX(), nodes.get(i).getY()));
      }
      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
      shapes.add(path);
    }

    for (int i = 0; i < nodes.size(); i++) {

      if (i == 0 || i == nodes.size() - 1) {
        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR + lineTout);
        c.setFill(borderColor);
        shapes.add(c);

        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR);
        c.setFill(insideColor);
        shapes.add(c);
      }
    }
    prevPath.addAll(shapes);
    return shapes;
  }

  /**
   * Draws the A* path between two points on a given floor.
   *
   * @param parent The Pane to draw the path on
   * @param firstClick The starting Point2D object
   * @param secondClick The ending Point2D object
   * @param floor1 The starting floor
   * @param floor2 The ending floor
   */
  public void drawAStarPath(
      Pane parent, Point2D firstClick, Point2D secondClick, String floor1, String floor2) {

    //    String floor = "L1";

    List<Node> allNodes = this.graph.getNodes();

    //    System.out.println(firstClick);
    //    System.out.println(secondClick); // Coordinates in inner, now goes up to 5000

    int startIndex = -1;
    int endIndex = -1;
    double leastDistance;
    double nodeDist;

    Point2D currentClick;
    String currentFloor;
    int checkIndex;

    for (int j = 0; j < 2; j++) {
      if (j == 0) {
        // Start Node
        currentFloor = floor1;
        currentClick = firstClick;
      } else {
        // End Node
        currentFloor = floor2;
        currentClick = secondClick;
      }

      leastDistance = Double.MAX_VALUE;
      checkIndex = -1;

      for (int i = 0; i < allNodes.size(); i++) {
        if (i == startIndex) {
          continue;
        } else {
          Node currentNode = allNodes.get(i);
          if (currentNode.getFloor().equals(currentFloor)) {
            nodeDist = currentClick.distance(currentNode.getX(), currentNode.getY());
            if (nodeDist < leastDistance) {
              leastDistance = nodeDist;
              checkIndex = i;
            }
          }
        }
      }

      if (j == 0) {
        // Start Node
        startIndex = checkIndex;
      } else {
        // End Node
        endIndex = checkIndex;
      }
    }

    Node startNode = allNodes.get(startIndex);
    Node endNode = allNodes.get(endIndex);

    drawAStarPath(parent, startNode, endNode);
  }

  /**
   * Draws the A* path between two nodes on a given floor and adds it to the specified parent Pane.
   *
   * @param parent the Pane to add the path to
   * @param floor1 the starting floor
   * @param floor2 the ending floor
   * @param sNode the starting node ID
   * @param eNode the ending node ID
   */
  public void drawAStarPath(Pane parent, String floor1, String floor2, int sNode, int eNode) {

    //    String floor = "L1";

    List<Node> allNodes = this.graph.getNodes();

    //    System.out.println(firstClick);
    //    System.out.println(secondClick); // Coordinates in inner, now goes up to 5000

    Node startNode = allNodes.get(/*startIndex*/ (sNode - 100) / 5);
    Node endNode = allNodes.get(/*endIndex*/ (eNode - 100) / 5);

    drawAStarPath(parent, startNode, endNode);
  }

  /**
   * Draws the A* path between two given nodes and adds it to the specified parent Pane.
   *
   * @param parent the Pane to add the path to
   * @param startNode the starting node
   * @param endNode the ending node
   */
  public void drawAStarPath(Pane parent, Node startNode, Node endNode) {
    ArrayList<Node> nodePath = this.graph.AStar(startNode, endNode);

    ArrayList<Shape> shapes = makeShapePath(nodePath);

    System.out.println(nodePath);

    parent.getChildren().addAll(shapes);
  }

  /**
   * Retrieves the names of all nodes on the specified floor and returns them in an ObservableList.
   *
   * @param floor the floor to retrieve node names from
   * @return an ObservableList of node names on the specified floor
   * @throws SQLException if there is an error with the SQL database
   */
  public ObservableList<String> getAllNodeNames(String floor) throws SQLException {
    ObservableList<String> nodeNames = FXCollections.observableArrayList();
    for (Node n : DataManager.getAllNodes()) {
      if (n.getFloor().equals(floor)) {
        nodeNames.addAll(("" + n.getId()));
      }
    }
    return nodeNames;
  }

  /** An array of strings that represent the names of different floors. */
  String[] floorArr = {
    "Lower Level 2", "Lower Level 1", "Ground Floor", "First Floor", "Second Floor", "Third Floor"
  };

  /**
   * Returns an observable list of all floor names.
   *
   * @param floor the floor to retrieve information for
   * @return an observable list of all floor names
   * @throws SQLException if there is an error retrieving the data from the database
   */
  public ObservableList<String> getAllFloors(String floor) throws SQLException {
    ObservableList<String> floorNames = FXCollections.observableArrayList();

    for (String f : floorArr) {
      floorNames.addAll(f);
    }

    return floorNames;
  }

  /**
   * An event handler that sets the visibility of a Circle object to true when triggered by a
   * MouseEvent. When this event handler is invoked, it prints "V" to the console, gets the source
   * of the event (assumed to be a Circle object), and sets the visibility of the Circle object to
   * true. Additionally, it prints the ID of the Circle object to the console.
   *
   * @param <MouseEvent> the type of the MouseEvent that triggers this event handler
   */
  EventHandler<MouseEvent> makeVisible =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Circle outer = ((Circle) event.getSource());
          System.out.println("V");
          System.out.println(outer.getId());
          outer.setVisible(true);
        }
      };

  /**
   * An event handler that sets the visibility of a Circle object to false when triggered by a
   * MouseEvent. When this event handler is invoked, it prints "H" to the console, gets the source
   * of the event (assumed to be a Circle object), and sets the visibility of the Circle object to
   * false. Additionally, it prints the ID of the Circle object to the console.
   *
   * @param <MouseEvent> the type of the MouseEvent that triggers this event handler
   */
  EventHandler<MouseEvent> hide =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          System.out.println("H");
          Circle outer = ((Circle) event.getSource());
          System.out.println(outer.getId());
          outer.setVisible(false);
        }
      };

  /**
   * Draws emergency markers on the map. The method retrieves a list of all emergency events from
   * the EmergencyEventManager and creates a corresponding emergency marker for each event on the
   * map.
   */
  public void drawEmergencies() {}

  //  public ObservableList<String> getAllNodeNames(String floor) throws SQLException {
  //    ObservableList<String> nodeNames = FXCollections.observableArrayList();
  //    for (Node n : DataManager.getAllNodes()) {
  //      if (n.getFloor().equals(floor)) {
  //        nodeNames.addAll(("" + n.getId()));
  //      }
  //    }
  //    return nodeNames;
  //  }

  /**
   * Generates a list of nodes to display for a given floor, excluding any nodes with type "HALL".
   *
   * @param floor the floor for which to generate nodes
   * @return the list of nodes to display
   * @throws SQLException if there is an error accessing the database
   * @throws IOException if there is an error loading image resources
   */
  public ArrayList<javafx.scene.Node> makeAllFloorNodes(String floor)
      throws SQLException, IOException {
    ArrayList<javafx.scene.Node> nodes =
        new ArrayList<javafx.scene.Node>(); // list of shapes to be displayed
    List<NodeCircle> circles = new ArrayList<>(); // List of NodeCircle Objects

    for (Node n : DataManager.getAllNodes()) {
      if (n.getFloor().equals(floor)) {
        ArrayList<String> nameType = new ArrayList<>();
        try {
          nameType = n.getShortName();
        } catch (SQLException ex) {
          System.out.println(ex.toString());
          System.out.println("Could not find info");
        }
        // String shortName = "";
        String nodeType = "";
        if (nameType.size() == 2) {
          // shortName = nameType.get(0);
          nodeType = nameType.get(1);
        } else {
        }
        if (!nodeType.equals("HALL")) circles.add(new NodeCircle(n));
      }
    }
    for (NodeCircle c : circles) {
      nodes.add(c.p);
      nodes.add(c.label);
    }
    return nodes;
  }

  /**
   * Generates a list of all nodes to display for a given floor, including nodes with type "HALL".
   *
   * @param floor the floor for which to generate nodes
   * @return the list of nodes to display
   * @throws SQLException if there is an error accessing the database
   * @throws IOException if there is an error loading image resources
   */

  //  public ArrayList<javafx.scene.Node> makeAllFloorNodesTwo(String floor)
  //      throws SQLException, IOException {
  //    ArrayList<javafx.scene.Node> nodes =
  //        new ArrayList<javafx.scene.Node>(); // list of shapes to be displayed
  //    List<NodeCircle> circles = new ArrayList<>(); // List of NodeCircle Objects
  //
  //    for (Node n : DataManager.getAllNodes()) {
  //      if (n.getFloor().equals(floor)) {
  //        circles.add(new NodeCircle(n));
  //      }
  //    }
  //    for (NodeCircle c : circles) {
  //      //            Pane p = new Pane();
  //      //      p.getChildren().addAll(c.outer, c.inner, c.text);
  //      //      nodes.add(c.outer);
  //      //      nodes.add(c.inner);
  //      //      nodes.add(c.text);
  //      nodes.add(c.p);
  //    }
  //
  //    return nodes;
  //  }

  /** Draws location names on the map. */
  public void drawLocationNames() throws SQLException {}

  /** Clears the map of all displayed nodes. */
  public void clearMap() {}

  /**
   * Returns the width of the map.
   *
   * @return the width of the map
   */
  private double getMapWitdh() {
    return 0;
  }

  /**
   * returns the height of the map.
   *
   * @return the height of the map
   */
  private double getMapHeight() {
    return 0;
  }

  /**
   * Centers and zooms the given GesturePane to display the map.
   *
   * @param parent the GesturePane to center and zoom
   */
  public void centerAndZoom(GesturePane parent) {
    double parentW = getMapWitdh();
    double parentH = getMapHeight();
    parentW = 760;
    parentH = 512;

    //    Point2D scaleOneDim = new Point2D(760 * 2, 512 * 2); // hard Coded
    Point2D scaleOneDim = new Point2D(1500, 2000);

    double scaleX = parentW / scaleOneDim.getX();
    double scaleY = parentH / scaleOneDim.getY();

    System.out.println(scaleX + ", " + scaleY);

    double scaleFactor = Double.min(scaleX, scaleY);

    centerPoint = new Point2D(2250, 1000); // Hard Coded
    double scale = parent.getCurrentScale();
    Point2D CMin = new Point2D((parentW / 2) * (1 / scale), (parentH / 2) * (1 / scale));
    centerTL = centerPoint.subtract(CMin);

    parent.zoomTo(scaleFactor, Point2D.ZERO);
    parent.centreOn(centerTL); // Actually Moves the Top left corner
  }
}
