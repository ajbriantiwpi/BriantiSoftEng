package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.navigation.Direction;
import java.awt.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

/**
 * Object for displaying text direcitons and signage
 */
public class DirectionArrow extends GridPane {
  @Getter @Setter Direction direction;

  @Getter @Setter String details;

  String image;
  ImageView imageView;
  Label text;

  int h;
  int w;

  public DirectionArrow(Direction direction, String details, int height) {
    this.details = details;
    this.direction = direction;
    this.h = height;
    initialize();
  }

  private void initialize() {
    if (direction == Direction.UP) {
      this.image = "arrow_upward.png";
    } else if (direction == Direction.DOWN) {
      this.image = "arrow_downward.png";
    } else if (direction == Direction.LEFT) {
      this.image = "arrow_top_left.png";
    } else if (direction == Direction.RIGHT) {
      this.image = "arrow_top_right.png";
    } else if (direction == Direction.STRAIGHT) {
      this.image = "straight.png";
    } else if (direction == Direction.START) {
      this.image = "line_start_circle.png";
    } else if (direction == Direction.END) {
      this.image = "line_end_circle.png";
    } else {
      this.image = "pin_drop.png";
    }
    this.imageView = new ImageView("edu/wpi/teamname/images/DirectionIcons/" + image);
    this.imageView.setFitWidth(h);
    this.imageView.setFitHeight(h);
    this.add(this.imageView, 0, 0);
    this.setPadding(new Insets(0, 0, 0, 12));
    this.setHgap(8);
    this.text = new Label(details);
    this.setMaxHeight(h);
    setFillWidth(text, true);
    this.text.getStyleClass().add("headline-med");
    this.text.getStyleClass().add("primary-text-container");
    this.text.setStyle("-fx-font-size: " + String.valueOf(h / 2));
    this.add(this.text, 1, 0);
    this.getStyleClass().add("secondary-container");
    this.getStyleClass().add("outline");
    this.setStyle("-fx-background-radius: 8; -fx-border-radius: 8");
  }
}
