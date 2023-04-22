package edu.wpi.teamname.controllers;

import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    //----------TABLE FXML-----------
    @FXML TableView<ConfRoom> confTable;
    @FXML TableColumn roomName;
    @FXML TableColumn am8;
    @FXML TableColumn am830;
    @FXML TableColumn am9;
    @FXML TableColumn am930;
    @FXML TableColumn am10;
    @FXML TableColumn am1030;
    @FXML TableColumn am11;
    @FXML TableColumn am1130;
    @FXML TableColumn pm12;
    @FXML TableColumn pm1230;
    @FXML TableColumn pm1;
    @FXML TableColumn pm130;
    @FXML TableColumn pm2;
    @FXML TableColumn pm230;
    @FXML TableColumn pm3;
    @FXML TableColumn pm330;
    @FXML TableColumn pm4;
    @FXML TableColumn pm430;
    @FXML TableColumn pm5;
    @FXML TableColumn pm530;
    @FXML TableColumn pm6;
    @FXML TableColumn pm630;

    //---------------------




    @FXML
    public void initialize(){

    }
}
