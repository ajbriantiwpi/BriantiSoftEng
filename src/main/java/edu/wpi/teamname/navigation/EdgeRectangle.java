package edu.wpi.teamname.navigation;

import edu.wpi.teamname.GlobalVariables;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

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

    p.getChildren().add(this.label);
  }
}
