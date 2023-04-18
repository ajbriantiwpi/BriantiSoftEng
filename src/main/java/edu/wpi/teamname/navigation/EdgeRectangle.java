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

  private MapNode startMapNode;
  private MapNode endMapNode;

  public EdgeRectangle(MapNode startMapNode, MapNode endMapNode) {
    this.startMapNode = startMapNode;
    this.endMapNode = endMapNode;

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

      path.getElements().add(new MoveTo(this.startMapNode.getX(), this.startMapNode.getY()));
      path.getElements().add(new LineTo(this.endMapNode.getX(), this.endMapNode.getY()));

      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
      p.getChildren().add(path);
    }

    p.getChildren().add(this.label);
  }
}
