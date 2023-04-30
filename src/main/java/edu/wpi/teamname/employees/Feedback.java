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

  public Feedback(String reporter, String description) {
    this.reporter = reporter;
    this.description = description;
  }

  public Feedback() {}
}
