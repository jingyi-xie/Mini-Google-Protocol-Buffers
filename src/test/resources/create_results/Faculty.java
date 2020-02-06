import java.util.*;
import java.lang.*;
import org.json.*;
public class Faculty {
private String name;
private Course' taught;
public String getName() {
return name;
}
public void setName(String x) {
this.name = x;
}
public Course' getTaught() {
return taught;
}
public void setTaught(Course' x) {
this.taught = x;
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
ans.put("type", "Faculty");
JSONArray fieldValuePairs = new JSONArray();
JSONArray arrayField; // for array type field only
JSONObject curPair;
curPair = new JSONObject();
curPair.put("name", this.name);
fieldValuePairs.put(curPair);
curPair = new JSONObject();
curPair.put("taught",taught.toJSONHelper(map));
fieldValuePairs.put(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<Object, Integer>());
}
}

