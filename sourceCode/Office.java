import java.util.*;
import org.json.*;
public class Office {
private Faculty occupant;
private int number;
private String building;
public Faculty getOccupant() {
return occupant;
}
public void setOccupant(Faculty x) {
this.occupant = x;
}
public int getNumber() {
return number;
}
public void setNumber(int x) {
this.number = x;
}
public String getBuilding() {
return building;
}
public void setBuilding(String x) {
this.building = x;
}
public JSONObject toJSONHelper(HashMap<String, JSONObject> map) throws JSONException {
JSONObject ans = new JSONObject();
ans.put("id", 1);
ans.put("type", "Office");
JSONArray fieldValuePairs = new JSONArray();
JSONObject curPair;
curPair = new JSONObject();
curPair.put("occupant", occupant);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("number", number);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("building", building);
fieldValuePairs.add(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<String, JSONObject>());
}
}

