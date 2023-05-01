package edu.wpi.teamname.controllers.helpers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.util.StringConverter;

public class TimestampStringConverter extends StringConverter<Timestamp> {
  private final String pattern;

  public TimestampStringConverter(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String toString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    return dateFormat.format(timestamp);
  }

  @Override
  public Timestamp fromString(String string) {
    if (string == null || string.trim().isEmpty()) {
      return null;
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      Date date = dateFormat.parse(string);
      return new Timestamp(date.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
