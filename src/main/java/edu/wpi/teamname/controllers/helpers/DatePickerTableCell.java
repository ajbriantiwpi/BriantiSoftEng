package edu.wpi.teamname.controllers.helpers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

public class DatePickerTableCell<S> extends TableCell<S, Timestamp> {

  private DatePicker datePicker;

  public DatePickerTableCell() {
    datePicker = new DatePicker();
    datePicker.setConverter(
        new StringConverter<LocalDate>() {
          @Override
          public String toString(LocalDate localDate) {
            if (localDate == null) {
              return "";
            }
            return localDate.toString();
          }

          @Override
          public LocalDate fromString(String dateString) {
            if (dateString == null || dateString.isEmpty()) {
              return null;
            }
            return LocalDate.parse(dateString);
          }
        });

    datePicker
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (isEditing()) {
                commitEdit(
                    Timestamp.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
              }
            });

    datePicker.setOnMouseClicked(
        event -> {
          startEdit();
        });

    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
  }

  @Override
  protected void updateItem(Timestamp item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setGraphic(null);
    } else {
      if (item != null) {
        datePicker.setValue(item.toLocalDateTime().toLocalDate());
      }
      setGraphic(datePicker);
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();
    if (!isEmpty()) {
      datePicker.setValue(getItem().toLocalDateTime().toLocalDate());
    }
  }

  @Override
  public void commitEdit(Timestamp newValue) {
    super.commitEdit(newValue);
    datePicker.setValue(newValue.toLocalDateTime().toLocalDate());
  }
}
