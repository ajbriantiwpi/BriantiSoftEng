package edu.wpi.teamname.navigation;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Map {

  public Graph graph;
  public ArrayList<Emergency> emergencies;

  private ArrayList<Shape> makeShapePath(ArrayList<Node> nodes) {
    return null;
  }

  /**
   * @param parent
   * @param clickPos
   */
  public void drawAStarPath(Pane parent, Point2D clickPos) {}

  /** */
  public void drawEmergencies() {}

  /** */
  public void drawLocationNames() {}

  /** */
  public void clearMap() {}

  /** @param parent */
  public void centerAndZoom(Pane parent) {}
}
