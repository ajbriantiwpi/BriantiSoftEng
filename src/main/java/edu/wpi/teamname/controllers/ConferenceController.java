package edu.wpi.teamname.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

public class ConferenceController {

    @FXML ComboBox startBox;
    @FXML ComboBox endBox;
    @FXML ComboBox buildingBox;
    @FXML ComboBox roomBox;
    @FXML DatePicker dateBox;
    @FXML RangeSlider sizeSlider;
    @FXML MFXButton submitButton;
    @FXML VBox viewBox;




    @FXML
    public void initialize(){

    }
}
