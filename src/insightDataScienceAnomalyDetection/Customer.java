package insightDataScienceAnomalyDetection;

import java.util.HashSet;
import java.util.Set;

public class Customer {
  private String id;
  private Set<String> friends;
  private Set<Event> selfpurchases;
  private Set<Event> socialpurchaes;
  public Customer() {
  }
  public Customer(String idVal) {
    id = idVal;
    friends = new HashSet<>();
    selfpurchases = new HashSet<>();
    socialpurchaes = new HashSet<>();
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
   * @return the friends
   */
  public Set<String> getFriends() {
    return friends;
  }
  /**
   * @param friends the friends to set
   */
  public void setFriends(Set<String> friends) {
    this.friends = friends;
  }
  /**
   * @return the selfpurchases
   */
  public Set<Event> getSelfpurchases() {
    return selfpurchases;
  }
  /**
   * @param selfpurchases the selfpurchases to set
   */
  public void setSelfpurchases(Set<Event> selfpurchases) {
    this.selfpurchases = selfpurchases;
  }
  /**
   * @return the socialpurchaes
   */
  public Set<Event> getSocialpurchaes() {
    return socialpurchaes;
  }
  /**
   * @param socialpurchaes the socialpurchaes to set
   */
  public void setSocialpurchaes(Set<Event> socialpurchaes) {
    this.socialpurchaes = socialpurchaes;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (id == null ? 0 : id.hashCode());
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
    Customer other = (Customer) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Customer [id=" + id + ", friends=" + friends + ", selfpurchases=" + selfpurchases
        + ", socialpurchaes=" + socialpurchaes + "]";
  }

}
