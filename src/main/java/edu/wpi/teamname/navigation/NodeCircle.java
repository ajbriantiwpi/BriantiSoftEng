package edu.wpi.teamname.navigation;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeCircle {
  public Circle inner;
  public Circle outer;
  public Text text = new Text();

  Color borderColor = new Color(0.1, 0.4, 0.9, 1);
  Color insideColor = new Color(0.05, 0.7, 1, 1);
  float circleR = 10.0f;
  float lineT = 10.0f;
  int lineTout = 2;

  public NodeCircle(Node n) {
    this.outer = new Circle(n.getX(), n.getY(), circleR + lineTout);
    outer.setFill(borderColor);
    // outer.setId("" + n.getId());//?????
    this.inner = new Circle(n.getX(), n.getY(), circleR);
    inner.setFill(insideColor);

    this.text.setText(Integer.toString(n.getId()));
    this.text.setX(n.getX());
    this.text.setY(n.getY());

    inner.setOpacity(0);
    outer.setOpacity(0);
    inner.setOnMouseEntered(makeVisible);
    inner.setOnMouseExited(hide);
  }

  EventHandler<MouseEvent> hide =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Circle outer = ((Circle) event.getSource());
          Circle inner = ((Circle) event.getSource());
          inner.setOpacity(0);
          outer.setOpacity(0);
          text.setOpacity(1);
        }
      };

  EventHandler<MouseEvent> makeVisible =
      new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          Circle outer = ((Circle) event.getSource());
          Circle inner = ((Circle) event.getSource());
          inner.setOpacity(1);
          outer.setOpacity(1);
          text.setOpacity(0);
        }
      };
}
