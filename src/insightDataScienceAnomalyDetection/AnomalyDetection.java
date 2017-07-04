package insightDataScienceAnomalyDetection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

public class AnomalyDetection {
  public int D = 1;
  public int T = 2;
  public int cnt = 0;
  public Map<String, Customer> customers;
  public List<String[]> anomalys;
  public BufferedReader br;
  public JSONParser parser;
  public SimpleDateFormat formatter;
  public AnomalyDetection() {
    customers = new HashMap<>();
    parser = new JSONParser();
    anomalys = new ArrayList<>();
    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }
  public static void main(String[] args) {
    AnomalyDetection detection = new AnomalyDetection();
    detection.batchReader("log_input/batch_log.json");
    detection.streamReader("log_input/stream_log.json");
    detection.writer();
  }
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
            throw new IllegalArgumentException("Invalid category");
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return res;
  }
  private void updater(String[] props) {
    if (props[0] != null) {
      String type = props[0];
      String id = props[2];
      try {
        Date date = formatter.parse(props[1]);
        if (type.equals("purchase")) {
          double amount = Double.valueOf(props[3]);
          Event event = new Event(type, date, id, amount, cnt);
          if (!customers.containsKey(id)) {
            Customer newCustomer = new Customer(id);
            newCustomer.addPurchase(event);
            customers.put(id, newCustomer);
          } else {
            customers.get(id).addPurchase(event);
          }
        } else{
          String id1 = id;
          String id2 = props[3];
          if (!customers.containsKey(id1)) {
            customers.put(id1, new Customer(id1));
          }
          if (!customers.containsKey(id2)) {
            customers.put(id2, new Customer(id2));
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
      } catch (java.text.ParseException e) {
        e.printStackTrace();
      }
    }
  }
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
    for (int i = 0; i < Math.min(T, socialPurchases.size()); i++) {
      purchaseVals.add(socialPurchases.poll().getAmount());
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
  private double meanCalculator (List<Double> values) {
    double sum = 0.0;
    for (double value : values) {
      sum += value;
    }
    return sum / values.size();
  }
  private double sdCalculator(List<Double> values, double mean) {
    double sum = 0.0;
    for (double value : values) {
      sum += Math.pow(value - mean, 2);
    }
    return Math.sqrt(sum / values.size());
  }
  private void writer() {
    try {
      FileWriter file = new FileWriter("log_output/flagged_purchase.json");
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
}

