package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.navigation.Direction;
import java.awt.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

/** Object for displaying text direcitons and signage */
public class DirectionArrow extends GridPane {
  @Getter @Setter Direction direction;

  @Getter @Setter String details;
  @Getter @Setter String details2;

  String image;
  ImageView imageView;
  Label text;
  Label text2;

  boolean twoTexts = false;

  int h;
  int w;

  String styleClass;

  public DirectionArrow(Direction direction, String details, int height) {
    this.details = details;
    this.direction = direction;
    this.h = height;
    this.styleClass = "secondary-container";
    this.twoTexts = false;
    initialize();
  }

  public DirectionArrow(Direction direction, String details, String details2, int height) {
    this.details2 = details2;
    this.twoTexts = true;
    this.details = details;
    this.direction = direction;
    this.h = height;
    this.styleClass = "secondary-container";
    initialize();
  }

  public DirectionArrow(Direction direction, String details, int height, String styleClass) {
    this.details = details;
    this.direction = direction;
    this.h = height;
    this.styleClass = styleClass;
    this.twoTexts = false;
    initialize();
  }

  private void initialize() {
    String folder;
    if (GlobalVariables.getDarkMode().get()) {
      folder = "dark";
    } else {
      folder = "light";
    }
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
    this.imageView =
        new ImageView("edu/wpi/teamname/images/DirectionIcons/" + folder + "/" + image);
    this.imageView.setFitWidth(h);
    this.imageView.setFitHeight(h);
    this.add(this.imageView, 0, 0);
    this.setPadding(new Insets(0, 0, 0, 12));
    this.setHgap(8);
    this.text = new Label(details);
    this.setMaxHeight(h);
    setFillWidth(text, true);
    this.text.getStyleClass().add("headline-med");
    this.text.getStyleClass().add("secondary-text-container");

    this.getStyleClass().add(styleClass);
    this.getStyleClass().add("outline");
    this.setStyle("-fx-background-radius: 8; -fx-border-radius: 8");
    if (twoTexts) {
      this.text2 = new Label(details2);
      setFillWidth(text2, true);
      this.text2.getStyleClass().add("headline-med");
      this.text2.getStyleClass().add("secondary-text-container");
      this.text.setStyle("-fx-font-size: " + String.valueOf(h / 3.5));
      this.text2.setStyle("-fx-font-size: " + String.valueOf(h / 3.5));
      VBox vBox = new VBox();
      vBox.setSpacing(4);
      vBox.getChildren().add(text);
      vBox.getChildren().add(text2);
      vBox.setAlignment(Pos.CENTER_LEFT);
      this.add(vBox, 1, 0);
    } else {
      this.text.setStyle("-fx-font-size: " + String.valueOf(h / 2));
      this.add(this.text, 1, 0);
    }
  }
}
