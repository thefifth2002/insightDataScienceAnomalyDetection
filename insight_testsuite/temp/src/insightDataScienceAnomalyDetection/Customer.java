package insightDataScienceAnomalyDetection;

/**
 * Customer class
 * create single customer from input file
 * Customer properties: id, set of friends, set of self purchases.
 */
import java.util.HashSet;
import java.util.Set;

public class Customer {
  private String id;
  private Set<Customer> friends;
  private Set<Event> purchases;
  public Customer() {
  }
  /**
   * Internal builder pattern class
   * initialize Customer class objects' fields in chain pattern
   * @author Hao
   *
   */
  public static class CustomerBuilder {
    private String id = "";
    private Set<Customer> friends = new HashSet<>();
    private Set<Event> purchases = new HashSet<>();
    /**
     * Customer's id builder, take String as input, if String is null
     * throw IllegalArgumentException("id can not be null")
     * @param idVal String
     * @return builder with the id passed in
     */
    public CustomerBuilder id (String idVal) {
      if (idVal == null) {
        throw new IllegalArgumentException("id can not be null");
      }
      id = idVal;
      return this;
    }
    /**
     * Customer's friends list builder, Take a set of customers as input.
     * set can't be null, otherwise throw
     * IllegalArgumentException"friends can not be null"
     * @param customers, a set of customer objects
     * @return builder with the id passed in
     */
    public CustomerBuilder friends (Set<Customer> customers) {
      if (customers == null) {
        throw new IllegalArgumentException("friends can not be null");
      }
      friends = customers;
      return this;
    }
    /**
     * customer's self purchase list, take a set of events as input.
     * set can't be null, otherwise throw
     * IllegalArgumentException("purchase history can not be null")
     * @param history, a set of this customer's purchase history
     * @return builder with the set of purchase passed in
     */
    public CustomerBuilder purchases (Set<Event> history) {
      if (history == null) {
        throw new IllegalArgumentException("purchase history can not be null");
      }
      purchases = history;
      return this;
    }
    /**
     * build one customer.
     * User should use builder to create new instance of Customer class.
     * ie Customer customer = new Customer.CustomerBuilder().id(id).build();
     * every builder is optional
     * @return one Customer class object
     */
    public Customer build() {
      return new Customer(this);
    }
  }
  /**
   * private CustomerBuilder class constructor
   * @param customerBuilder
   */
  private Customer(CustomerBuilder customerBuilder) {
    id = customerBuilder.id;
    friends = customerBuilder.friends;
    purchases = customerBuilder.purchases;
  }
  /**
   * Add a purchase event to current customer's purchase history. if event is null
   * throw IllegalArgumentException("purchase history can not be null");
   * @param e Event object passed in
   */
  public void addPurchase(Event e) {
    if (e == null) {
      throw new IllegalArgumentException("Event can not be null");
    }
    if (!purchases.contains(e)) {
      purchases.add(e);
    }
  }
  /**
   * Add a customer to current customer's friends list, if customer is null
   * throw new IllegalArgumentException("Customer can not be null")
   * @param c Customer object passed in
   */
  public void addFriend(Customer c) {
    if (c == null) {
      throw new IllegalArgumentException("Customer can not be null");
    }
    if (!friends.contains(c)) {
      friends.add(c);
    }
  }
  /**
   * remove customer from current customer's friends list, if customer is null
   * throw new IllegalArgumentException("Customer can not be null")
   * @param c customer to be removed.
   */
  public void removeFriend(Customer c) {
    if (c == null) {
      throw new IllegalArgumentException("Customer can not be null");
    }
    if (friends.contains(c)) {
      friends.remove(c);
    }
  }
  /**
   * customer's id getter
   * @return the id String
   */
  public String getId() {
    return id;
  }
  /**
   * customer's id setter
   * @param id String the id to set
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * customer's friends getter
   * @return the friends set
   */
  public Set<Customer> getFriends() {
    return friends;
  }
  /**
   * customer's friends setter
   * @param friends the friends to set
   */
  public void setFriends(Set<Customer> friends) {
    this.friends = friends;
  }
  /**
   * Customer's purchases' getter
   * @return the purchases set
   */
  public Set<Event> getPurchases() {
    return purchases;
  }
  /**
   * Customer's purchases' setter
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
