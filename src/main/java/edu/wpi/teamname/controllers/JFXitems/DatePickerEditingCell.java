package edu.wpi.teamname.controllers.JFXitems;

import edu.wpi.teamname.navigation.Move;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

public class DatePickerEditingCell extends TableCell<Move, Timestamp> {
  private DatePicker datePicker;

  public DatePickerEditingCell() {
    datePicker = new DatePicker();
    datePicker.setConverter(
        new StringConverter<LocalDate>() {
          DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

          @Override
          public String toString(LocalDate localDate) {
            if (localDate == null) {
              return "";
            }
            return dateFormatter.format(localDate);
          }

          @Override
          public LocalDate fromString(String dateString) {
            if (dateString == null || dateString.trim().isEmpty()) {
              return null;
            }
            return LocalDate.parse(dateString, dateFormatter);
          }
        });

    datePicker.setOnAction(
        event -> {
          if (datePicker.getValue() != null) {
            commitEdit(Timestamp.valueOf(datePicker.getValue().atStartOfDay()));
          }
        });

    setGraphic(datePicker);
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
  }

  @Override
  protected void updateItem(Timestamp item, boolean empty) {
    super.updateItem(item, empty);

    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      if (isEditing()) {
        datePicker.setValue(item.toLocalDateTime().toLocalDate());
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      } else {
        setText(item.toLocalDateTime().toLocalDate().toString());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
      }
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();
    datePicker.setValue(getItem().toLocalDateTime().toLocalDate());
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    setContentDisplay(ContentDisplay.TEXT_ONLY);
  }
}
