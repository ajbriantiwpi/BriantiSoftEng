package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.controllers.ConferenceController;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

public class RoomSelector extends BorderPane {
    @Getter private ConfRoom room;
    private Label name;
    @Setter private Timestamp date;
    @Getter HBox hBox;

    ConferenceController controller;

    ObservableList<SelectorButton> buttons;

    @Getter private static int slots = 22;

    @Getter private int start = 0;
    @Getter private int end = 0;
    @Getter @Setter int selected = 0;

    public RoomSelector(ConfRoom room, ConferenceController controller) {
        this.room = room;
        this.controller = controller;
        System.out.println("New Room Selector: " + room.getLocationName());
        //    this.date = date;
        buttons = FXCollections.observableArrayList();
        initialize();
    }

    private void initialize() {
        setMinHeight(50);
        setMinWidth(400);
        //    this.getStylesheets().add("../stylesheets/Colors/lightTheme.css");
        //    this.getStyleClass().add("surface-container");
        this.name = new Label(this.room.getLocationName());
        this.setLeft(name);
        this.name.setPadding(new Insets(8, 12, 8, 12));
        this.name.setStyle("-fx-background-color: #D5E3FF; -fx-text-fill: #001B3B");
        this.setStyle("-fx-background-color: #D5E3FF; -fx-border-color: #6F797A");
        this.name.setMinHeight(75);
        this.name.setMinWidth(100);
        this.getStyleClass().add("primary-container");

        for (int i = 0; i < slots; i++) {
            buttons.add(new SelectorButton(i, this));
        }
        this.hBox = new HBox();
        this.setCenter(hBox);
        this.hBox.getChildren().addAll(buttons);
    }

    void handleButtonClick(int id) {
        if (selected == 0) {
            setSelect(true, id);
            start = id;
            end = id;
            selected = 1;
            controller.setActiveSelector(this);
        } else if (selected == 1) {
            if (id == start) {
                selected = 0;
                setSelect(false, id);
            } else {
                if (id > start) {
                    end = id;
                    setSelect(false, start);
                } else {
                    start = id;
                    setSelect(false, end);
                }
                setAllInRange(true);
                selected = 2;
            }
            controller.setActiveSelector(this);
        } else {
            setAllInRange(false);
            selected = 0;
            handleButtonClick(id);
        }
    }

    void setSelect(boolean select, int id) {
        if (select) {
            if (buttons.get(id).getStatus() == RoomStatus.AVAILABLE
                    || buttons.get(id).getStatus() == RoomStatus.SELECTED) {
                buttons.get(id).setStatus(RoomStatus.SELECTED);
            } else {
                buttons.get(id).setStatus(RoomStatus.ERROR);
            }
        } else {
            if (buttons.get(id).getStatus() == RoomStatus.SELECTED
                    || buttons.get(id).getStatus() == RoomStatus.AVAILABLE) {
                buttons.get(id).setStatus(RoomStatus.AVAILABLE);
            } else {
                buttons.get(id).setStatus(RoomStatus.BOOKED);
            }
        }
    }

    public void setAllInRange(boolean select) {
        for (int i = start; i <= end; i++) {
            setSelect(select, i);
        }
    }

    int timeToID(String time) {
        DateFormat format = new SimpleDateFormat("HH:mm");
        Time tim;
        try {
            tim = new Time(format.parse(time).getTime());
        } catch (ParseException e) {
            return -1;
        }
        int outID = 0;
        outID += 2 * (tim.getHours() - 6);
        if (tim.getMinutes() > 20) {
            outID++;
        }
        return outID;
    }
}
