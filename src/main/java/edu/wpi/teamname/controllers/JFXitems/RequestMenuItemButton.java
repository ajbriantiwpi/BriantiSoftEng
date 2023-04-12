package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.servicerequest.ServiceRequest;
import java.sql.SQLException;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;

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
          for (int a = 0; a < parent.getQuantity(); a++) {
            try {
              if (add) {
                request.addItem(id);
              } else {
                request.removeItem(id);
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          
        }});
  }
}
