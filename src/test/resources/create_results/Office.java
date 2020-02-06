import java.util.*;
import java.lang.*;
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
public JSONObject toJSONHelper(HashMap<Object, Integer> map) throws JSONException {
JSONObject ans = new JSONObject();
if (map.containsKey(this)) {
ans.put("ref", map.get(this));
return ans;
}
int uniqueid = map.size() + 1;
ans.put("id", uniqueid);
map.put(this, uniqueid);
ans.put("type", "Office");
JSONArray fieldValuePairs = new JSONArray();
JSONArray arrayField; // for array type field only
JSONObject curPair;
curPair = new JSONObject();
curPair.put("occupant",occupant.toJSONHelper(map));
fieldValuePairs.put(curPair);
curPair = new JSONObject();
curPair.put("number", this.number);
fieldValuePairs.put(curPair);
curPair = new JSONObject();
curPair.put("building", this.building);
fieldValuePairs.put(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<Object, Integer>());
}
}

