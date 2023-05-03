package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URISyntaxException;
import javafx.scene.AccessibleRole;
import lombok.Getter;

/** Custom button for RoomSelector that represents 30min in time */
public class SelectorButton extends MFXButton {
  RoomSelector parent;
  int id;
  @Getter RoomStatus status;

  SelectorButton(int id, RoomSelector parent) throws URISyntaxException {
    super(parent.idToTime(id));
    this.parent = parent;
    this.id = id;
    setStatus(RoomStatus.AVAILABLE);
    initialize();
  }

  private void initialize() throws URISyntaxException {
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
          try {
            parent.handleButtonClick(this.id);
          } catch (URISyntaxException e) {
            throw new RuntimeException(e);
          }
        });
  }

  /**
   * Sets the status and color of the button
   *
   * @param status
   */
  void setStatus(RoomStatus status) throws URISyntaxException {
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
      Sound.playSFX(SFX.ERROR);
    }
  }
}
