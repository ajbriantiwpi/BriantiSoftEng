package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.controllers.ServiceRequestController;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/** Item for displaying items to be put in cart as well as in the cart for service requests */
public class ReqMenuItems extends GridPane {
  private String name;
  private int id;
  private boolean add;
  private String folder;

  private int q;
  ServiceRequest request;
  RequestItem item;
  private boolean editable = true;

  ServiceRequestController controller;

  @FXML VBox vBox;
  @FXML Label label;
  @FXML Label labelP;
  @FXML Button button;
  @FXML ImageView imageView;
  @FXML TextField quantity;

  public ReqMenuItems(
      RequestItem item,
      String folder,
      ServiceRequest request,
      boolean add,
      ServiceRequestController controller) {
    this.name = item.getName();
    // System.out.println(item.getItemID());
    this.id = item.getItemID();
    this.item = item;
    this.request = request;
    this.add = add;
    this.controller = controller;
    this.folder = folder;
    this.editable = true;

    initialize();
  }

  public void setTexts(Language lang, DecimalFormat df, Label labelP) {
    switch (GlobalVariables.getB().getValue()) {
      case ENGLISH:
        quantity.setText("Quantity: " + String.valueOf(this.q));
        quantity.setPromptText("Quantity");
        break;
      case ITALIAN:
        quantity.setText("Quantità: " + String.valueOf(this.q));
        quantity.setPromptText("Quantità");
        break;
      case FRENCH:
        quantity.setText("Quantité: " + String.valueOf(this.q));
        quantity.setPromptText("Quantité");
        break;
      case SPANISH:
        quantity.setText("Cantidad: " + String.valueOf(this.q));
        quantity.setPromptText("Cantidad");
        break;
    }
    if (labelP != null) {
      switch (GlobalVariables.getB().getValue()) {
        case ENGLISH:
          labelP.setText("Price: $" + df.format(item.getPrice()));
          break;
        case ITALIAN:
          labelP.setText("Prezzo: $" + df.format(item.getPrice()));
          break;
        case FRENCH:
          labelP.setText("Prix: $" + df.format(item.getPrice()));
          break;
        case SPANISH:
          labelP.setText("Precio: $" + df.format(item.getPrice()));
          break;
      }
    }
  }

  public ReqMenuItems(
      RequestItem item,
      String folder,
      ServiceRequest request,
      boolean add,
      ServiceRequestController controller,
      int q) {
    this.name = item.getName();
    this.id = item.getItemID();
    this.item = item;
    this.request = request;
    this.add = add;
    this.controller = controller;
    this.folder = folder;
    this.editable = true;
    this.q = q;

    initialize();

    switch (GlobalVariables.getB().getValue()) {
      case ENGLISH:
        quantity.setText("Quantity: " + String.valueOf(this.q));
        break;
      case ITALIAN:
        quantity.setText("Quantità: " + String.valueOf(this.q));
        break;
      case FRENCH:
        quantity.setText("Quantité: " + String.valueOf(this.q));
        break;
      case SPANISH:
        quantity.setText("Cantidad: " + String.valueOf(this.q));
        break;
    }
    //    quantity.setText("Quantity: " + String.valueOf(this.q));
    quantity.setDisable(true);
  }

  public ReqMenuItems(RequestItem item, String folder, int q) {
    this.item = item;
    this.folder = folder;
    this.id = item.getItemID();
    this.name = item.getName();
    this.editable = false;
    this.add = false;

    initialize();
    switch (GlobalVariables.getB().getValue()) {
      case ENGLISH:
        quantity.setText("Quantity: " + String.valueOf(this.q));
        break;
      case ITALIAN:
        quantity.setText("Quantità: " + String.valueOf(this.q));
        break;
      case FRENCH:
        quantity.setText("Quantité: " + String.valueOf(this.q));
        break;
      case SPANISH:
        quantity.setText("Cantidad: " + String.valueOf(this.q));
        break;
    }
    //    quantity.setText("Quantity: " + String.valueOf(q));
    quantity.setDisable(true);
  }

  private void initialize() {
    try {
      imageView =
          new ImageView(
              "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
      add(imageView, 0, 0);
      setFillWidth(imageView, true);
      setHgrow(imageView, Priority.ALWAYS);
      imageView.setFitHeight(100);
      imageView.setFitWidth(100);

      System.out.println(
          "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
    } catch (IllegalArgumentException i) {
      System.out.println("illegal image urls");
      System.out.println(
          "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
    }
    // hBox = new HBox();
    DecimalFormat df = new DecimalFormat("###0.00");
    label = new Label(name.replace("_", " "));
    label.setFont(Font.font("Roboto", 32));
    label.setMinWidth(300);
    label.setMaxWidth(300);
    switch (GlobalVariables.getB().getValue()) {
      case ENGLISH:
        labelP = new Label("Price: $" + df.format(item.getPrice()));
        break;
      case ITALIAN:
        labelP = new Label("Prezzo: $" + df.format(item.getPrice()));
        break;
      case FRENCH:
        labelP = new Label("Prix: $" + df.format(item.getPrice()));
        break;
      case SPANISH:
        labelP = new Label("Precio: $" + df.format(item.getPrice()));
        break;
    }

    labelP.setFont(Font.font("Roboto", 32));
    labelP.setMinWidth(200);
    labelP.setMaxWidth(200);
    if (editable) {
      button =
          new RequestMenuItemButton(name.replace("_", " "), this.id, this, this.request, this.add);
      vBox = new VBox();
      vBox.setAlignment(Pos.CENTER);
      vBox.setMinWidth(button.getWidth() + 15);
      vBox.getChildren().add(button);
      add(vBox, 4, 0);
      setFillWidth(vBox, true);
      setHgrow(vBox, Priority.ALWAYS);
      setHalignment(vBox, HPos.RIGHT);
      button.setLayoutY(this.getHeight());
      button.setPadding(new Insets(this.getHeight() / 2 - button.getHeight() / 2, 25, 0, 25));
      button.setAlignment(Pos.CENTER_RIGHT);
      button.setStyle(
          "-fx-background-radius: 8;" + " -fx-font-family: Roboto;" + " -fx-font-size: 32 ");
      button.getStyleClass().add("primary");
    }
    quantity = new TextField("");
    switch (GlobalVariables.getB().getValue()) {
      case ENGLISH:
        quantity.setPromptText("Quantity");
        break;
      case ITALIAN:
        quantity.setPromptText("Quantità");
        break;
      case FRENCH:
        quantity.setPromptText("Quantité");
        break;
      case SPANISH:
        quantity.setPromptText("Cantidad");
        break;
    }

    quantity.setFont(Font.font("Roboto", 32));

    quantity.setStyle(
        "-fx-background-radius: 8;-fx-border-radius: 8; -fx-border-color: #6F797A; -fx-border-width: 1");
    quantity.getStyleClass().add("primary-container");
    add(label, 1, 0);
    add(labelP, 2, 0);
    add(quantity, 3, 0);
    quantity.setText("1");

    setFillWidth(label, true);
    setHgrow(label, Priority.ALWAYS);
    setHalignment(label, HPos.LEFT);
    setHalignment(labelP, HPos.LEFT);
    setHalignment(quantity, HPos.LEFT);

    setStyle("-fx-background-radius: 8");
    getStyleClass().add("primary-container");
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setTexts(newValue, df, labelP);
        });
  }

  /**
   * gets the quantity of the of this item
   *
   * @return the quantity of the item int he text field
   */
  public int getQuantity() {
    int val;
    try {
      if (add) {
        val = Integer.parseInt(quantity.getText());
      } else {
        val = this.q;
      }
    } catch (NumberFormatException e) {
      val = 1;
    }
    return val;
  }

  /**
   * deletes this item from the ui
   *
   * @throws SQLException
   */
  void delete() throws SQLException {
    controller.getCartBox().getChildren().remove(this);
    controller.refreshPrice();
  }
}
