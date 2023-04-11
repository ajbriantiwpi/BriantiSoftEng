package edu.wpi.teamname.navigation;

import edu.wpi.teamname.system.App;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeCircle {
  public Circle inner;
  public Circle outer;
  //  public Text text = new Text();

  public VBox v;

  public Pane p;

  Color borderColor = new Color(0.1, 0.4, 0.9, 1);
  Color insideColor = new Color(0.05, 0.7, 1, 1);
  float circleR = 10.0f;
  float lineT = 10.0f;
  int lineTout = 2;

  public NodeCircle(Node n) throws IOException {
    float shiftX = 0; // circleR;
    float shiftY = 0; // circleR;

    //    this.outer = new Circle(n.getX(), n.getY(), circleR + lineTout);
    this.outer = new Circle(shiftX, shiftY, circleR + lineTout);
    outer.setFill(borderColor);
    // outer.setId("" + n.getId());//?????
    //    this.inner = new Circle(n.getX(), n.getY(), circleR);
    this.inner = new Circle(shiftX, shiftY, circleR);
    inner.setFill(insideColor);

    //    this.text.setText(Integer.toString(n.getId()));
    //    this.text.setX(n.getX());
    //    this.text.setY(n.getY());
    //    this.text.setX(shiftX);
    //    this.text.setY(shiftY);

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

  EventHandler<MouseEvent> hide =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Pane p = ((Pane) event.getSource());
          for (javafx.scene.Node n : p.getChildren()) {
            n.setOpacity(0);
          }
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
          //    p.getChildren().addAll(this.outer, this.inner, this.text);
          p.getChildren().addAll(v);

          //          for (javafx.scene.Node n : p.getChildren()) {
          //            if ((n.getClass() == VBox.class)) {
          //              n.setOpacity(1);
          //              n.setDisable(false);
          //            }
          //          }
        }
      };
}
