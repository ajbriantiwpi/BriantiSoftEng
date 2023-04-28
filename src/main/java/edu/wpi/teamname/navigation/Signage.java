package edu.wpi.teamname.navigation;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class Signage {
  @Getter private String longName;
  @Getter private String shortName;
  @Getter @Setter private Timestamp date;
  @Getter @Setter private Timestamp endDate;
  @Getter @Setter private Direction arrowDirection;
  @Getter @Setter private int signId;
  @Getter @Setter private int kioskId;

  public Signage(
      String longName,
      String shortName,
      Timestamp date,
      Timestamp endDate,
      Direction arrowDirection,
      int signId,
      int kioskId) {
    this.longName = longName;
    this.shortName = shortName;
    this.date = date;
    this.endDate = endDate;
    this.arrowDirection = arrowDirection;
    this.signId = signId;
    this.kioskId = kioskId;
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
        + endDate
        + ", "
        + arrowDirection
        + ", "
        + kioskId
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
        && this.endDate.equals(otherSignage.getEndDate())
        && this.kioskId == (otherSignage.getKioskId())
        && this.arrowDirection.equals(otherSignage.getArrowDirection())
        && this.signId == otherSignage.getSignId();
  }

  public void setLongName(String newValue) {
    this.longName = newValue;
  }

  public void setShortName(String newValue) {
    this.shortName = newValue;
  }


}
