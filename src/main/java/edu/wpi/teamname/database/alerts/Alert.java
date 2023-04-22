package edu.wpi.teamname.database.alerts;

import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Alert {
  @Getter @Setter private int id; // UUID for the announcement

  @Getter @Setter @NonNull private Timestamp startDisplayDate; // Date that the announcement is for

  @Getter @Setter @NonNull private Timestamp endDisplayDate; // Date that the announcement is for

  @Setter @Getter private String creator; // Author for the message

  @Setter @Getter private ArrayList<Employee> employeeList;
  @Getter @Setter private EmployeeType type; // Department for the message

  @Getter @Setter @NonNull private String description;

  @Getter @Setter @NonNull private String announcement;

  @Getter @Setter @NonNull private Urgency urgency;

  /** Empty constructor, required by hibernate */
  public Alert() {}

  /**
   * Creates the announcement with the given parameters
   *
   * @param startDisplayDate the date to create
   * @param creator the author of the announcement
   * @param announcement the announcement body to create
   */
  public Alert(
      int alertID,
      @NonNull Timestamp startDisplayDate,
      @NonNull Timestamp endDisplayDate,
      String creator,
      @NonNull String description,
      @NonNull String announcement,
      @NonNull EmployeeType type,
      @NonNull Urgency urgency) {
    this.id = alertID;
    this.startDisplayDate = startDisplayDate;
    this.endDisplayDate = endDisplayDate;
    this.creator = creator;
    this.description = description;
    this.announcement = announcement;
    this.type = type;
    this.urgency = urgency;
    employeeList = new ArrayList<>();
  }

  public enum Urgency {
    NONE("None"),
    MILD("Mild"),
    INTERMEDIATE("Medium"),
    SEVERE("Severe");

    @NonNull public final String urgency;

    Urgency(@NonNull String urgencyVal) {
      urgency = urgencyVal;
    }

    @Override
    public String toString() {
      return this.urgency;
    }

    public String getString() {
      return "";
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true; // If the two are the same in memory, easy
    if (o == null || getClass() != o.getClass())
      return false; // If they aren't the same class or one is null, bad

    Alert that = (Alert) o; // Compare announcements

    return getId() == that.getId(); // Check IDs, do it based on that
  }

  public boolean addStaff(Employee adder) {
    return this.employeeList.add(adder);
  }

  @Override
  public String toString() {
    return Long.toString(this.id);
  }
}
