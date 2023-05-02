package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import java.sql.SQLException;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;

/**
 * Custom button for service request items that hard codes for adding and removing items from the
 * cart
 */
public class RequestMenuItemButton extends Button {
  ReqMenuItems parent;
  String name;
  int id;
  ServiceRequest request;

  boolean add;

  RequestMenuItemButton(
      String name, int id, ReqMenuItems parent, ServiceRequest request, boolean add) {
    super(name);
    this.parent = parent;
    this.name = name;
    this.id = id;
    this.request = request;
    this.add = add;
    initialize();
    if (add) {
      this.setText("Add to Cart");
    } else {
      this.setText("Remove From Cart");
    }
  }

  private void initialize() {
    getStyleClass().setAll("button");
    setAccessibleRole(AccessibleRole.BUTTON);
    setMnemonicParsing(true);
    // setStyle("-fx-background-color: #d9d9d9");
    setOnMouseClicked(
        event -> {
          Sound.playSFX(SFX.BUTTONCLICK);
          for (int a = 0; a < parent.getQuantity(); a++) {
            try {
              if (add) {
                request.addItem(id);
                System.out.println(id);
              } else {
                request.removeItem(id);
                parent.delete();
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }
}
