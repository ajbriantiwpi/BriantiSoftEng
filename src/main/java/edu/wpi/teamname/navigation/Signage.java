package edu.wpi.teamname.navigation;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class Signage {
  @Getter private String longName;
  @Getter private String shortName;
  @Getter @Setter private Timestamp date;
  @Getter @Setter private String arrowDirection;
  @Getter @Setter private int signId;

  public Signage(
      String longName, String shortName, Timestamp date, String arrowDirection, int signId) {
    this.longName = longName;
    this.shortName = shortName;
    this.date = date;
    this.arrowDirection = arrowDirection;
    this.signId = signId;
  }

  /**
   * Returns a string representation of this Signage object.
   *
   * @return a string representation of this Signage object
   */
  @Override
  public String toString() {
    return "["
        + longName
        + ", "
        + shortName
        + ", "
        + date
        + ", "
        + arrowDirection
        + ", "
        + signId
        + "]";
  }

  /**
   * Compares this Signage object to another Signage object for equality.
   *
   * @param other the Signage object to compare to
   * @return true if the two objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Signage)) {
      return false;
    }
    Signage otherSignage = (Signage) other;
    return this.longName.equals(otherSignage.getLongName())
        && this.shortName.equals(otherSignage.getShortName())
        && this.date.equals(otherSignage.getDate())
        && this.arrowDirection.equals(otherSignage.getArrowDirection())
        && this.signId == otherSignage.getSignId();
  }
}
