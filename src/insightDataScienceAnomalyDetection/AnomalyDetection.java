package insightDataScienceAnomalyDetection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * AnomalyDetection class, including all the methods to read, parse, update,
 * detect anomaly purchase and write to file
 * priorities: D, T, a list of all customers and a list of all anomalies.
 * @author Hao
 *
 */
public class AnomalyDetection {
  private int D = 1;
  private int T = 2;
  private int cnt = 0;
  private Map<String, Customer> customers;
  private List<String[]> anomalys;
  private BufferedReader br;
  private JSONParser parser;
  /**
   * A private class constructor
   */
  private AnomalyDetection() {
    customers = new HashMap<>();
    parser = new JSONParser();
    anomalys = new ArrayList<>();
  }
  /**
   * A static method to wake the class constructor
   * @return An class object
   */
  public static AnomalyDetection create() {
    return new AnomalyDetection();
  }
  public static void main(String[] args) {
    if (args.length < 3) {
      throw new IllegalArgumentException("extra arguments needed for main");
    } else if (args.length > 3) {
      throw new IllegalArgumentException("ony 3 arguments are needed for main");
    }
    AnomalyDetection detection = create();
    detection.batchReader(args[0]);
    detection.streamReader(args[1]);
    detection.writer(args[2]);
  }
  /**
   * Read and parse batch file to construct the initial social network
   * @param path to the batch file
   */
  public void batchReader(String path) {
    String line;
    try {
      br = new BufferedReader(new FileReader(path));
      while((line = br.readLine()) != null && !line.equals("") ) {
        cnt++;
        String[] props = lineParser(line);
        updater(props);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Read and parse stream file, detect anomalies and update the network
   * @param path to the stream file
   */
  public void streamReader(String path) {
    String line;
    try {
      br = new BufferedReader(new FileReader(path));
      while((line = br.readLine()) != null && !line.equals("") ) {
        cnt++;
        String[] props = lineParser(line);
        updater(props);
        if (props[0].equals("purchase") && customers.containsKey(props[2])) {
          detector(props);
        }
        
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * private method that both reader method used to parse each line of file,
   * extract event and customer info from files
   * @param line of files
   * @return A string array of event or customer properties
   */
  private String[] lineParser(String line) {
    String[] res = new String[4];
    Object obj;
    try {
      obj = parser.parse(line);
      JSONObject jsonObject = (JSONObject) obj;
      for (Object key : jsonObject.keySet()) {
        String s = (String)jsonObject.get(key);
        switch ((String)key) {
          case "D" :
            D = Integer.parseInt(s);
            break;
          case "T" :
            T = Integer.parseInt(s);
            break;
          case "event_type" :
            res[0] = s;
            break;
          case "timestamp" :
            res[1] = s;
            break;
          case "id" :
            res[2] = s;
            break;
          case "id1" :
            res[2] = s;
            break;
          case "amount" :
            res[3] = s;
            break;
          case "id2" :
            res[3] = s;
            break;
          default :
            throw new IllegalArgumentException("Invalid event");
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return res;
  }
  /**
   * update the social network after each line is parsed
   * @param props String array of parsed customer or event properties
   */
  private void updater(String[] props) {
    if (props[0] != null) {
      String type = props[0];
      String id = props[2];
      String timeStamp = props[1];
      if (type.equals("purchase")) {
        String amount = props[3];
        Event event = new Event.EventBuilder().type(type).timeStamp(timeStamp)
            .id(id).amount(amount).position(cnt).build();
        if (!customers.containsKey(id)) {
          Customer newCustomer = new Customer.CustomerBuilder().id(id).build();
          newCustomer.addPurchase(event);
          customers.put(id, newCustomer);
        } else {
          customers.get(id).addPurchase(event);
        }
      } else{
        String id1 = id;
        String id2 = props[3];
        if (!customers.containsKey(id1)) {
          customers.put(id1, new Customer.CustomerBuilder().id(id1).build());
        }
        if (!customers.containsKey(id2)) {
          customers.put(id2, new Customer.CustomerBuilder().id(id2).build());
        }
        if (type.equals("befriend")) {
          customers.get(id1).addFriend(customers.get(id2));
          customers.get(id2).addFriend(customers.get(id1));
        }
        if (type.equals("unfriend")) {
          customers.get(id1).removeFriend(customers.get(id2));
          customers.get(id2).removeFriend(customers.get(id1));
        }
      }
    }
  }
  /**
   * Detect anomaly purchases
   * @param props String array of parsed customer or event properties
   */
  private void detector(String[] props) {
    String id = props[2];
    double amount = Double.valueOf(props[3]);
    Set<Customer> friends = new HashSet<>();
    Customer currCustomer = customers.get(id);
    friends.add(currCustomer);
    for (int i = 0; i < D; i++) {
      Set<Customer> tmps = new HashSet<>(findFriends(friends));
      for (Customer tmp : tmps) {
        if (!friends.contains(tmp)) {
          friends.add(tmp);
        }
      }
    }
    friends.remove(currCustomer);
    Queue<Event> socialPurchases = findAllSocialPurchases(friends);
    if (socialPurchases.size() < 2) {
      return;
    }
    List<Double> purchaseVals = new ArrayList<>();
    int min = Math.min(getT(), socialPurchases.size());
    for (int i = 0; i < min; i++) {
      Event purchase = socialPurchases.poll();
      purchaseVals.add(Double.valueOf(purchase.getAmount()));
    }
    double mean = meanCalculator(purchaseVals);
    double sd = sdCalculator(purchaseVals, mean);
    if (amount > mean + sd * 3) {
      String[] tmp = {props[0], props[1], props[2], props[3],
          Double.toString(Math.floor(mean * 100) / 100),
          Double.toString(Math.floor(sd * 100) / 100)};
      anomalys.add(tmp);
    }
  }
  /**
   * locate all the social purchases
   * @param friends, a list of all friends and Dth friends of current customer
   * @return A queue of social purchases order by timestamp of line count
   */
  private Queue<Event> findAllSocialPurchases(Set<Customer> friends) {
    Queue<Event> socialPurchases = new PriorityQueue<>(new Comparator<Event>(){
      @Override
      public int compare(Event arg0, Event arg1) {
        return arg1.getPosition() - arg0.getPosition();
      }
    });
    for (Customer friend : friends) {
      Set<Event> purchases = friend.getPurchases();
      for (Event purchase : purchases) {
        socialPurchases.add(purchase);
      }
    }
    return socialPurchases;
  }
  /**
   * locate all the Dth friends of current customer's direct friends list
   * @param friends list of current customer's direct friends
   * @return all the Dth friends and direct friends.
   */
  private Set<Customer> findFriends(Set<Customer> friends) {
    Set<Customer> newFriends = new HashSet<>();
    for (Customer friend : friends) {
      Set<Customer> tmps = friend.getFriends();
      for (Customer tmp : tmps) {
        if (!friends.contains(tmp)) {
          newFriends.add(tmp);
        }
      }
    }
    return newFriends;
  }
  /**
   * calculate mean of selected purchases.
   * @param values, list of purchase amount
   * @return mean, mean of the list
   */
  private double meanCalculator (List<Double> values) {
    double sum = 0.0;
    for (double value : values) {
      sum += value;
    }
    return sum / values.size();
  }
  /**
   * calculate standard deviation
   * @param values of selected purchase amount
   * @param mean of selected purchase amount
   * @return standard deviation of selected purchase
   */
  private double sdCalculator(List<Double> values, double mean) {
    double sum = 0.0;
    for (double value : values) {
      sum += Math.pow(value - mean, 2);
    }
    return Math.sqrt(sum / values.size());
  }
  /**
   * write anomaly purchases to file
   * @param outputPath to the file
   */
  private void writer(String outputPath) {
    try {
      FileWriter file = new FileWriter(outputPath);
      for (String[] anomaly : anomalys) {
        String out = "{\"" + "event_type" + "\": \"" + anomaly[0] + "\", \""
            + "timestamp" + "\": \"" + anomaly[1] + "\", \""
            + "id" + "\": \"" + anomaly[2] + "\", \""
            + "amount" + "\": \"" + anomaly[3] + "\", \""
            + "mean" + "\": \"" + anomaly[4] + "\", \""
            + "sd" + "\": \"" + anomaly[5] + "\"}";
        file.write(out);
        file.write("\n");
        file.flush();
      }
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * @return the d
   */
  public int getD() {
    return D;
  }
  /**
   * @param d the d to set
   */
  public void setD(int d) {
    if (d < 0) {
      throw new IllegalArgumentException("D can't be negative");
    }
    D = d;
  }
  /**
   * @return the t
   */
  public int getT() {
    return T;
  }
  /**
   * @param t the t to set
   */
  public void setT(int t) {
    if (t < 0) {
      throw new IllegalArgumentException("t can't be negative");
    }
    T = t;
  }
  /**
   * @return the cnt
   */
  public int getCnt() {
    return cnt;
  }
  /**
   * @param cnt the cnt to set
   */
  public void setCnt(int cnt) {
    if (cnt < 0) {
      throw new IllegalArgumentException("line count can't be negative");
    }
    this.cnt = cnt;
  }
  /**
   * @return the customers
   */
  public Map<String, Customer> getCustomers() {
    return customers;
  }
  /**
   * @param customers the customers to set
   */
  public void setCustomers(Map<String, Customer> customers) {
    if (customers == null) {
      throw new IllegalArgumentException("Customers can't be null");
    }
    this.customers = customers;
  }
  /**
   * @return the anomalys
   */
  public List<String[]> getAnomalys() {
    return anomalys;
  }
  /**
   * @param anomalys the anomalys to set
   */
  public void setAnomalys(List<String[]> anomalys) {
    if (anomalys == null) {
      throw new IllegalArgumentException("anomalys can't be null");
    }
    this.anomalys = anomalys;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AnomalyDetection [D=" + D + ", T=" + T + ", cnt=" + cnt + ", customers=" + customers
        + ", anomalys=" + anomalys + "]";
  }

}

