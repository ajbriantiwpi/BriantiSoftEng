package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
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

public class ReqMenuItems extends GridPane {
  private String name;
  private int id;
  ServiceRequest request;
  @FXML HBox hBox;
  @FXML VBox vBox;
  @FXML Label label;
  @FXML Button button;
  @FXML ImageView imageView;
  @FXML TextField quantity;

  public ReqMenuItems(RequestItem item, String folder, ServiceRequest request) {
    this.name = item.getName();
    this.id = item.getItemID();
    this.request = request;
    try {
      imageView =
          new ImageView(
              "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
      add(imageView, 0, 0);
      setFillWidth(imageView, true);
      setHgrow(imageView, Priority.ALWAYS);
      System.out.println(
          "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
    } catch (IllegalArgumentException i) {
      System.out.println("illegal image urls");
      System.out.println(
          "edu/wpi/teamname/images/" + folder + "/" + name.replace(" ", "_") + ".png");
    }
    hBox = new HBox();
    label = new Label(name.replace("_", " "));
    label.setFont(Font.font("Roboto", 32));
    label.setMinWidth(200);
    label.setMaxWidth(200);
    button = new RequestMenuItemButton(name.replace("_", " "), this.id, this, this.request);
    quantity = new TextField("");
    quantity.setPromptText("Quantity");
    quantity.setFont(Font.font("Roboto", 32));

    quantity.setStyle(
        "-fx-background-color: #D9E3F8 ;-fx-font-fill: #121C2B; -fx-background-radius: 8;-fx-border-radius: 8; -fx-border-color: #6F797A; -fx-border-width: 1");

    add(label, 1, 0);
    add(quantity, 2, 0);

    vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setMinWidth(button.getWidth() + 15);
    vBox.getChildren().add(button);
    add(vBox, 3, 0);

    button.setLayoutY(this.getHeight());
    button.setPadding(new Insets(this.getHeight() / 2 - button.getHeight() / 2, 25, 0, 25));
    initialize();
  }

  private void initialize() {
    // setLayoutX(1000);
    hBox.setPadding(new Insets(0, 30, 0, 30));
    hBox.setSpacing(100);
    setFillWidth(label, true);
    setHgrow(label, Priority.ALWAYS);
    setHalignment(label, HPos.LEFT);
    setHalignment(quantity, HPos.LEFT);
    setFillWidth(vBox, true);
    setHgrow(vBox, Priority.ALWAYS);
    button.setAlignment(Pos.CENTER_RIGHT);
    setHalignment(vBox, HPos.RIGHT);
    hBox.setAlignment(Pos.CENTER_LEFT);
    setStyle("-fx-background-color: #D5E3FF;" + "-fx-background-radius: 8");
    button.setStyle(
        "-fx-background-color: #555F71; "
            + "-fx-text-fill:  #FFFFFF; "
            + "-fx-background-radius: 100;"
            + " -fx-font-family: Roboto;"
            + " -fx-font-size: 28 ");
  }

  public int getQuantity() {
    int val;
    try {
      val = Integer.parseInt(quantity.getText());
    } catch (NumberFormatException e) {
      val = 1;
    }
    return val;
  }
}
