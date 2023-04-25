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
    setOnMouseClicked(
        event -> {
          parent.handleButtonClick(this.id);
        });
  }

  void setStatus(RoomStatus status) {
    this.status = status;
    if (status == RoomStatus.AVAILABLE) {
      setStyle("-fx-background-color: #FFDEA1;-fx-background-radius: 0;-fx-border-color: #6F797A;");
    } else if (status == RoomStatus.BOOKED) {
      setStyle("-fx-background-color: #235FA6;-fx-background-radius: 0;-fx-border-color: #6F797A;");
    } else if (status == RoomStatus.SELECTED) {
      setStyle("-fx-background-color: #2FA7B0;-fx-background-radius: 0;-fx-border-color: #6F797A;");
    } else {
      setStyle("-fx-background-color: #BA1A1A;-fx-background-radius: 0;-fx-border-color: #6F797A;");
    }
  }
}
