package edu.wpi.teamname.navigation;

import edu.wpi.teamname.GlobalVariables;
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
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import lombok.Getter;
import lombok.Setter;
import net.kurobako.gesturefx.GesturePane;

public class Map {
  public Graph graph;
  public ArrayList<Emergency> emergencies;
  private Point2D centerPoint;
  private Point2D centerTL;
  @Getter @Setter private ArrayList<Shape> prevPath = new ArrayList<Shape>();

  @Getter @Setter private ArrayList<ArrayList<Shape>> shapes = new ArrayList<ArrayList<Shape>>();

  public Map() throws SQLException {
    this.graph = new Graph();
  }

  /**
   * Creates a path with circles representing the nodes from the given list of nodes.
   *
   * @param nodes List of nodes to create the path from
   * @return An ArrayList of Shape objects representing the path
   */
  private ArrayList<ArrayList<Shape>> makeShapePath(ArrayList<Node> nodes) {
    ArrayList<Node> listFloor1 = new ArrayList<>();
    ArrayList<Node> listFloor2 = new ArrayList<>();
    ArrayList<Node> listUpper1 = new ArrayList<>();
    ArrayList<Node> listUpper2 = new ArrayList<>();
    ArrayList<Node> listUpper3 = new ArrayList<>();

    ArrayList<Shape> lF2 = new ArrayList<Shape>();
    ArrayList<Shape> lF1 = new ArrayList<Shape>();
    ArrayList<Shape> uF1 = new ArrayList<Shape>();
    ArrayList<Shape> uF2 = new ArrayList<Shape>();
    ArrayList<Shape> uF3 = new ArrayList<Shape>();

    ArrayList<ArrayList<Shape>> pathAllFloor = new ArrayList<ArrayList<Shape>>();

    for (int i = 0; i < 5; i++) {
      pathAllFloor.add(new ArrayList<Shape>());
    }

    // pathAllFloor.add(0,listFloor2);

    for (Node n : nodes) {
      if (n.getFloor().equals("L1")) {
        listFloor1.add(n);
      } else if (n.getFloor().equals("L2")) {
        listFloor2.add(n);
      } else if (n.getFloor().equals("1") || n.getFloor().equals("G1")) {
        listUpper1.add(n);
      } else if (n.getFloor().equals("2") || n.getFloor().equals("G2")) {
        listUpper2.add(n);
      } else if (n.getFloor().equals("1") || n.getFloor().equals("G1")) {
        listUpper3.add(n);
      } else {
        System.out.println("This should not be here");
      }
    }

    if (listFloor2.size() != 0) {
      pathAllFloor.add(0, makeShapePathFloor(listFloor2, "L2"));
    }

    if (listFloor1.size() != 0) {
      System.out.println("size of list1: " + listFloor1.size());
      // System.out.println("size of list in list: " + pathAllFloor.get(1).size());
      pathAllFloor.add(1, makeShapePathFloor(listFloor1, "L1"));
    }

    if (listUpper1.size() != 0) {
      pathAllFloor.add(2, makeShapePathFloor(listUpper1, "1"));
    }

    if (listUpper2.size() != 0) {
      pathAllFloor.add(3, makeShapePathFloor(listUpper2, "2"));
    }

    if (listUpper3.size() != 0) {
      pathAllFloor.add(4, makeShapePathFloor(listUpper3, "3"));
    }

    return pathAllFloor;

    //    ArrayList<Shape> shapes = new ArrayList<Shape>();
    //
    //    Circle c;
    //    Path path;
    //
    //    for (int j = 0; j < 2; j++) {
    //      path = new Path();
    //      if (j == 0) {
    //        path.setStroke(borderColor);
    //      } else {
    //        path.setStroke(insideColor);
    //      }
    //      path.setStrokeWidth(lineT - (lineTout * 2 * j));
    //      path.getElements().add(new MoveTo(nodes.get(0).getX(), nodes.get(0).getY()));
    //
    //
    //      for (int i = 1; i < nodes.size(); i++) {
    //        path.getElements().add(new LineTo(nodes.get(i).getX(), nodes.get(i).getY()));
    //      }
    //      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
    //      shapes.add(path);
    //    }
    //
    //    for (int i = 0; i < nodes.size(); i++) {
    //
    //      if (i == 0 || i == nodes.size() - 1) {
    //        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR + lineTout);
    //        c.setFill(borderColor);
    //        shapes.add(c);
    //
    //        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR);
    //        c.setFill(insideColor);
    //        shapes.add(c);
    //      }
    //    }
    //    prevPath.addAll(shapes);
    //    return shapes;
  }

  private ArrayList<Shape> makeShapePathFloor(ArrayList<Node> listNode, String floor) {
    ArrayList<Shape> shapes = new ArrayList<Shape>();

    Circle c;
    Path path;

    for (int j = 0; j < 2; j++) {
      path = new Path();
      if (j == 0) {
        path.setStroke(GlobalVariables.getBorderColor());
      } else {
        path.setStroke(GlobalVariables.getInsideColor());
      }
      path.setStrokeWidth(
          GlobalVariables.getLineT() - (GlobalVariables.getStrokeThickness() * 2 * j));
      path.getElements().add(new MoveTo(listNode.get(0).getX(), listNode.get(0).getY()));

      for (int i = 1; i < listNode.size(); i++) {
        path.getElements().add(new LineTo(listNode.get(i).getX(), listNode.get(i).getY()));
      }
      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
      shapes.add(path);
    }

    for (int i = 0; i < listNode.size(); i++) {

      if (i == 0 || i == listNode.size() - 1) {
        c =
            new Circle(
                listNode.get(i).getX(),
                listNode.get(i).getY(),
                GlobalVariables.getCircleR() + GlobalVariables.getStrokeThickness());
        c.setFill(GlobalVariables.getBorderColor());
        shapes.add(c);

        c =
            new Circle(
                listNode.get(i).getX(), listNode.get(i).getY(), GlobalVariables.getCircleR());
        c.setFill(GlobalVariables.getInsideColor());
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
  public void drawPath(
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
        endIndex = 171;
      }
    }

    //    Node startNode = allNodes.get(startIndex);
    //    Node endNode = allNodes.get(endIndex);
    System.out.println(startIndex + " " + endIndex);
    int startId = allNodes.get(startIndex).getId();
    int endId = allNodes.get(endIndex).getId();

    drawPath(parent, startId, endId);
    //    String retStr = "";
    //    retStr += startIndex + "_" + endIndex;
    //    return retStr;
  }

  /**
   * Draws the A* path between two nodes on a given floor and adds it to the specified parent Pane.
   *
   * @param parent the Pane to add the path to
   * @param startNodeId the starting node ID
   * @param endNodeId the ending node ID
   */
  public void drawPath(Pane parent, int startNodeId, int endNodeId) {
    ArrayList<Node> nodePath = this.graph.getPathBetween(startNodeId, endNodeId);

    System.out.println("SIZE: " + nodePath.size());

    shapes = makeShapePath(nodePath);

    // return shapes

    //    System.out.println(nodePath);

    // parent.getChildren().addAll(shapes);
  }

  /**
   * Draws the A* path between two given nodes and adds it to the specified parent Pane.
   *
   * @param parent the Pane to add the path to
   */
  //  public ArrayList<ArrayList<Shape>> drawAStarPath(Pane parent, int sInd, int eInd) {
  //    List<Node> allNodes = this.graph.getNodes();
  //
  //    Node startNode = allNodes.get(sInd);
  //    Node endNode = allNodes.get(eInd);
  //
  //    ArrayList<Node> nodePath = this.graph.AStar(startNode, endNode);
  //
  //    ArrayList<ArrayList<Shape>> shapes = makeShapePath(nodePath);
  //
  //    // System.out.println(shapes.get(1));
  //    return shapes;
  //
  //    // parent.getChildren().addAll(shapes.get(1));
  //
  //  }

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
    "Lower Level 2", "Lower Level 1", "First Floor", "Second Floor", "Third Floor"
  };

  /**
   * Returns an observable list of all floor names.
   *
   * @return an observable list of all floor names
   * @throws SQLException if there is an error retrieving the data from the database
   */
  public ObservableList<String> getAllFloors() {
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
  //  public ArrayList<javafx.scene.Node> makeAllFloorNodes(String floor) throws SQLException,
  // IOException {
  //    ArrayList<javafx.scene.Node> nodes = new ArrayList<javafx.scene.Node>(); // list of shapes
  // to be displayed
  //    List<NodeCircle> circles = new ArrayList<>(); // List of NodeCircle Objects
  //
  //    for (Node n : DataManager.getAllNodes()) {
  //      if (n.getFloor().equals(floor)) {
  //        ArrayList<String> nameType = new ArrayList<>();
  //        try {
  //          nameType = n.getShortName();
  //        } catch (SQLException ex) {
  //          System.out.println(ex.toString());
  //          System.out.println("Could not find info");
  //        }
  //        // String shortName = "";
  //        String nodeType = "";
  //        if (nameType.size() == 2) {
  //          // shortName = nameType.get(0);
  //          nodeType = nameType.get(1);
  //        } else {
  //        }
  //        if (!nodeType.equals("HALL")) circles.add(new NodeCircle(n));
  //      }
  //    }
  //    for (NodeCircle c : circles) {
  //      nodes.add(c.p);
  //      nodes.add(c.label);
  //    }
  //    return nodes;
  //  }

  /**
   * Generates a list of all nodes to display for a given floor, including nodes with type "HALL".
   *
   * @param floor the floor for which to generate nodes
   * @return the list of nodes to display
   * @throws SQLException if there is an error accessing the database
   * @throws IOException if there is an error loading image resources
   */
  public ArrayList<javafx.scene.Node> makeAllFloorNodes(String floor, boolean isMapPage)
      throws SQLException, IOException {
    ArrayList<javafx.scene.Node> nodes =
        new ArrayList<javafx.scene.Node>(); // list of shapes to be displayed
    ArrayList<NodeCircle> circles = new ArrayList<>(); // List of NodeCircle Objects

    for (Node n : DataManager.getAllNodes()) {
      if (n.getFloor().equals(floor)) {
        circles.add(new NodeCircle(n, isMapPage));
      }
    }
    for (NodeCircle c : circles) {
      nodes.add(c.p);
    }

    return nodes;
  }

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
