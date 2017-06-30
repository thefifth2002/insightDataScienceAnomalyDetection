package insightDataScienceAnomalyDetection;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AnomalyDetection {
  public Set<Customer> customers;
  public int D = 2;
  public int T = 2;
  public AnomalyDetection() {
    customers = new HashSet<>();
  }
  public static void main(String[] args) {
    AnomalyDetection go = new AnomalyDetection();
    go.batchReader();
  }
  public void batchReader() {
    JSONParser parser = new JSONParser();
    try {
      Object obj = parser.parse(new FileReader("log_input\test.json"));
      JSONObject jsonObject = (JSONObject) obj;
      System.out.println(jsonObject);
    } catch (IOException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
