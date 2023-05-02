package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.Sound;
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
    setTexts(GlobalVariables.getB().getValue());
  }

  public void setTexts(Language lang) {
    switch (lang) {
      case ENGLISH:
        if (add) {
          this.setText("Add to Cart");
        } else {
          this.setText("Remove From Cart");
        }
        break;
      case ITALIAN:
        if (add) {
          this.setText("Aggiunga al carro");
        } else {
          this.setText("rimuovere dal carro");
        }
        break;
      case SPANISH:
        if (add) {
          this.setText("En el Carro");
        } else {
          this.setText("Retirar del carrito");
        }
        break;
      case FRENCH:
        if (add) {
          this.setText("Ajouter au Chariot");
        } else {
          this.setText("Retirer du Chariot");
        }
        break;
    }
  }

  private void initialize() {
    getStyleClass().setAll("button");

    setAccessibleRole(AccessibleRole.BUTTON);
    setMnemonicParsing(true);
    setOnMouseClicked(
        event -> {
          Sound.playOnButtonClick();
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
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setTexts(newValue);
        });
  }
}
