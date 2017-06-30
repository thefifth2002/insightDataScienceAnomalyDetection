package insightDataScienceAnomalyDetection;

import java.util.Date;

public class Event {
  private String type;
  private Date timeStamp;
  private String id;
  private String id1;
  private String id2;
  private double amount;
  public Event() {
  }
  public Event(String typeVal, Date timeStampVal, String idVal, double amountVal) {
    type = typeVal;
    timeStamp = timeStampVal;
    id = idVal;
    amount = amountVal;
  }
  public Event(String typeVal, Date timeStampVal, String id1Val, String id2Val) {
    type = typeVal;
    timeStamp = timeStampVal;
    id1 = id1Val;
    id2 = id2Val;
  }
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * @return the timeStamp
   */
  public Date getTimeStamp() {
    return timeStamp;
  }
  /**
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * @return the id1
   */
  public String getId1() {
    return id1;
  }
  /**
   * @param id1 the id1 to set
   */
  public void setId1(String id1) {
    this.id1 = id1;
  }
  /**
   * @return the id2
   */
  public String getId2() {
    return id2;
  }
  /**
   * @param id2 the id2 to set
   */
  public void setId2(String id2) {
    this.id2 = id2;
  }
  /**
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }
  /**
   * @param amount the amount to set
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(amount);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + (id == null ? 0 : id.hashCode());
    result = prime * result + (id1 == null ? 0 : id1.hashCode());
    result = prime * result + (id2 == null ? 0 : id2.hashCode());
    result = prime * result + (timeStamp == null ? 0 : timeStamp.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Event other = (Event) obj;
    if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (id1 == null) {
      if (other.id1 != null) {
        return false;
      }
    } else if (!id1.equals(other.id1)) {
      return false;
    }
    if (id2 == null) {
      if (other.id2 != null) {
        return false;
      }
    } else if (!id2.equals(other.id2)) {
      return false;
    }
    if (timeStamp == null) {
      if (other.timeStamp != null) {
        return false;
      }
    } else if (!timeStamp.equals(other.timeStamp)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Event [type=" + type + ", timeStamp=" + timeStamp + ", id=" + id + ", id1=" + id1
        + ", id2=" + id2 + ", amount=" + amount + "]";
  }
}
