import java.util.*;
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
public JSONObject toJSONHelper(HashMap<String, JSONObject> map) throws JSONException {
JSONObject ans = new JSONObject();
ans.put("id", 1);
ans.put("type", "Faculty");
JSONArray fieldValuePairs = new JSONArray();
JSONObject curPair;
curPair = new JSONObject();
curPair.put("name", name);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("taught", taught);
fieldValuePairs.add(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<String, JSONObject>());
}
}

