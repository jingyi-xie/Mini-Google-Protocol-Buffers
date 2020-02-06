import java.util.*;
import java.lang.*;
import org.json.*;
public class Float {
private ArrayList<Float> data;
public Float() {
this.data = new ArrayList<>();
}
public int numData() {
return data.size();
}
public void addData(float x) {
data.add(x);
}
public float getData(int index) {
return data.get(index);
}
public void setData(int index, float x) {
data.set(index, x);
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
ans.put("type", "Float");
JSONArray fieldValuePairs = new JSONArray();
JSONArray arrayField; // for array type field only
JSONObject curPair;
curPair = new JSONObject();
curPair.put("data", this.data);
fieldValuePairs.put(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<Object, Integer>());
}
}

