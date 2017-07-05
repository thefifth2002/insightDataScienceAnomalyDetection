package insightDataScienceAnomalyDetection;

/**
 * Event class
 * Create single purchase event object.
 * Event properties: type, timeStamp, id, amount and position
 * @author Hao
 */
public class Event implements Comparable<Event> {
  private String type;
  private String timeStamp;
  private String id;
  private String amount;
  private int position;
  /**
   * Internal builder pattern class
   * initialize Event objects' fields  in chain pattern
   * @author Hao
   *
   */
  public static class EventBuilder {
    private String type = "";
    private String timeStamp = "";
    private String id = "";
    private String amount = "";
    private int position = -1;
    /**
     * Event type builder, take String as input. if String is null
     * throw IllegalArgumentException("type can not be null")
     * @param typeVal String
     * @return builder with type passed in
     */
    public EventBuilder type (String typeVal) {
      if (typeVal == null) {
        throw new IllegalArgumentException("type can not be null");
      }
      type = typeVal;
      return this;
    }
    /**
     * Event timeStamp builder, take String as input. if String is null
     * IllegalArgumentException("timestamp can not be null")
     * @param timeVal String
     * @return builder with timeStamp passed in
     */
    public EventBuilder timeStamp (String timeVal) {
      if (timeVal == null) {
        throw new IllegalArgumentException("timestamp can not be null");
      }
      timeStamp = timeVal;
      return this;
    }
    /**
     * Event id builder, take String as input. if String is null
     * throw IllegalArgumentException("id can not be null")
     * @param idVal String
     * @return builder with id passed in
     */
    public EventBuilder id (String idVal) {
      if (idVal == null) {
        throw new IllegalArgumentException("id can not be null");
      }
      id = idVal;
      return this;
    }
    /**
     * Event amount builder, take String as input. if String is null
     * throw IllegalArgumentException("amount can not be null")
     * @param  amountVal String
     * @return builder with amount passed in
     */
    public EventBuilder amount (String amountVal) {
      if (amountVal == null) {
        throw new IllegalArgumentException("amount can not be null");
      }
      amount = amountVal;
      return this;
    }
    /**
     * Event position builder, take int as input. if position < 0
     * throw new IllegalArgumentException("line count can not be negative")
     * @param posVal int
     * @return builder with line count passed in
     */
    public EventBuilder position (int posVal) {
      if (posVal < 0) {
        throw new IllegalArgumentException("line count can not be negative");
      }
      position = posVal;
      return this;
    }
    /**
     * build one event.
     * User should use builder to create new instance of Event class.
     * ie Event event = new Event.EventBuilder().type(type).timeStamp(timeStamp)
     * .id(id).amount(amount).position(cnt).build(); every builder is optional
     * @return one Event class object.
     */
    public Event build() {
      return new Event(this);
    }
  }
  /**
   * private EventBuilder class constructor
   * @param eventBuilder
   */
  private Event(EventBuilder eventBuilder) {
    type = eventBuilder.type;
    timeStamp = eventBuilder.timeStamp;
    id = eventBuilder.id;
    amount = eventBuilder.amount;
    position = eventBuilder.position;
  }
  /** type getter
   * @return String the type
   */
  public String getType() {
    return type;
  }
  /**
   * Set event't type
   * @param String type,  the type to set, can't be null
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * timeStamp getter
   * @return String the timeStamp
   */
  public String getTimeStamp() {
    return timeStamp;
  }
  /**
   * set event's timeStamp, take String as parameter, can't be null
   * @param String timeStamp the timeStamp to set
   */
  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
  /**
   * id getter
   * @return String the id
   */
  public String getId() {
    return id;
  }
  /**
   * set event's id, take String as parameter, can't be null
   * @param String id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * amount getter
   * @return String the amount
   */
  public String getAmount() {
    return amount;
  }
  /**
   * amount setter
   * @param String amount the amount to set
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }
  /**
   * position getter
   * @return int the position
   */
  public int getPosition() {
    return position;
  }
  /**
   * set event's position in source file
   * @param int position the position to set
   */
  public void setPosition(int position) {
    this.position = position;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (amount == null ? 0 : amount.hashCode());
    result = prime * result + (id == null ? 0 : id.hashCode());
    result = prime * result + position;
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
    if (amount == null) {
      if (other.amount != null) {
        return false;
      }
    } else if (!amount.equals(other.amount)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (position != other.position) {
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
    return "Event [type=" + type + ", timeStamp=" + timeStamp + ", id=" + id + ", amount=" + amount
        + ", position=" + position + "]";
  }
  @Override
  public int compareTo(Event o) {
    return 0;
  }

}
