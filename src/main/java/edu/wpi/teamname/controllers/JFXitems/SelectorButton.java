package edu.wpi.teamname.controllers.JFXitems;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.AccessibleRole;
import lombok.Getter;

public class SelectorButton extends MFXButton {
  RoomSelector parent;
  int id;
  @Getter RoomStatus status;

  SelectorButton(int id, RoomSelector parent) {
    super(parent.idToTime(id));
    this.parent = parent;
    this.id = id;
    setStatus(RoomStatus.AVAILABLE);
    initialize();
  }

  private void initialize() {
    getStyleClass().setAll("button");
    setAccessibleRole(AccessibleRole.BUTTON);
    setMnemonicParsing(true);

    setStyle("-fx-background-radius: 0;-fx-border-color: #6F797A;");
    // getStyleClass().add("primary-on");
    this.setStatus(RoomStatus.AVAILABLE);
    this.setMinWidth(50);
    this.setMinHeight(75);
    setStyle("-fx-background-radius: 0;-fx-border-color: #6F797A;");
    setOnMouseClicked(
        event -> {
          parent.handleButtonClick(this.id);
        });
  }

  void setStatus(RoomStatus status) {
    this.status = status;
    if (status == RoomStatus.AVAILABLE) {
      getStyleClass().clear();
      getStyleClass().add("tertiary-container");
    } else if (status == RoomStatus.BOOKED) {
      getStyleClass().clear();
      getStyleClass().add("primary");
    } else if (status == RoomStatus.SELECTED) {
      getStyleClass().clear();
      getStyleClass().add("teal");
    } else {
      getStyleClass().clear();
      getStyleClass().add("error");
    }
  }
}
