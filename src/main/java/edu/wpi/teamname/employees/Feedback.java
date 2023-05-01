package edu.wpi.teamname.employees;

import edu.wpi.teamname.servicerequest.Status;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class Feedback {
  @Getter @Setter private String reporter;
  @Getter @Setter private Timestamp dateReported;
  @Getter @Setter private String description;
  @Getter @Setter private String assignee;
  @Getter @Setter private Status status;
  @Getter @Setter private int id;
  private static int counter = 0;

  public Feedback(String reporter, String description) {
    this.reporter = reporter;
    this.description = description;
    this.dateReported = new Timestamp(System.currentTimeMillis());
    this.assignee = "No Assignee";
    this.status = Status.BLANK;
    this.id = ++counter;
  }

  public Feedback(Feedback feedback) {
    this.reporter = feedback.reporter;
    this.description = feedback.description;
    this.dateReported =
        feedback.dateReported != null ? new Timestamp(feedback.dateReported.getTime()) : null;
    this.assignee = feedback.assignee;
    this.status = feedback.status;
    this.id = ++counter;
  }

  public Feedback() {
    this("null", "null");
  }

  public Feedback clone() {
    return new Feedback(this);
  }
}
