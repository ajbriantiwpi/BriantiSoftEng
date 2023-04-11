package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.servicerequest.ServiceRequest;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class RequestMenuItemButton extends Button {
  ReqMenuItems parent;
  String name;
  int id;
  ServiceRequest request;

  RequestMenuItemButton(String name, int id, ReqMenuItems parent, ServiceRequest request) {
    super(name);
    this.parent = parent;
    this.name = name;
    this.id = id;
    this.request = request;
    initialize();
    this.setText("Add to Cart");
  }

  private void initialize() {
    getStyleClass().setAll("button");
    setAccessibleRole(AccessibleRole.BUTTON);
    setMnemonicParsing(true);
    setStyle("-fx-background-color: #d9d9d9");
    setFont(Font.font("Times New Roman", 32));
    setOnMouseClicked(
        event -> {
          for (int a = 0; a < parent.getQuantity(); a++) {
            request.addItem(id);
          }
          /*catch (SQLException e) {
              throw new RuntimeException(e);
          }*/
        });
  }
}
