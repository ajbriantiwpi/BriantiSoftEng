package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.ConfRoomDAOImpl;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
    private static Timestamp bookingDate;

    //make date button
        //when date button selected get the date and when initializing table query only the ones on the specific day and put it in bookingDate variable
    //make name field OR just get the users ID




    @FXML
    public void initialize() throws SQLException {
        roomName.setCellValueFactory(new PropertyValueFactory<ConfRoom, String>("room"));


    }

    //while user has not clicked submit, whichever cells they choose add those cells to a list
    //after user submits with name field filled out have a confirm booking
    //have option to clear bookings
    //when clicking a cell make new confRoom obj thats null called finalConfReq
    //when clicked is true, get cells coordinates, call translate function with the coordinates, change color of cell,
        //translate function should return String of string endTime (time+30)
    //Calling translate into String clickedTime and comparing if the finalConfReq.endTime == clickedTime UNLESS clickedTime = 7 then set finalConfReq.endTime to 7
}
