package insightDataScienceAnomalyDetection;

import java.util.HashSet;
import java.util.Set;

public class Customer {
  private String id;
  private Set<Customer> friends;
  private Set<Event> purchases;
  public Customer() {
  }
  public Customer(String idVal) {
    id = idVal;
    friends = new HashSet<>();
    purchases = new HashSet<>();
  }
  public void addPurchase(Event e) {
    if (!purchases.contains(e)) {
      purchases.add(e);
    }
  }
  public void addFriend(Customer c) {
    if (!friends.contains(c)) {
      friends.add(c);
    }
  }
  public void removeFriend(Customer c) {
    if (friends.contains(c)) {
      friends.remove(c);
    }
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
  public Set<Customer> getFriends() {
    return friends;
  }
  /**
   * @param friends the friends to set
   */
  public void setFriends(Set<Customer> friends) {
    this.friends = friends;
  }
  /**
   * @return the purchases
   */
  public Set<Event> getPurchases() {
    return purchases;
  }
  /**
   * @param purchases the purchases to set
   */
  public void setPurchases(Set<Event> purchases) {
    this.purchases = purchases;
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
    return "Customer [id=" + id + ", friends=" + friends + ", purchases=" + purchases + "]";
  }

}
