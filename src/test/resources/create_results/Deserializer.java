import java.util.*;
import java.lang.*;
import org.json.*;
public class Deserializer {
HashMap<Integer, Object> idMap;
public Deserializer() {
this.idMap = new HashMap<>();}
public static Short readHelperShort(JSONObject js, Map<Integer, Object> idMap) {
int id;
if (js.opt("id") == null) { 
id = js.getInt("ref");}
else {
id = js.getInt("id");
}
if (idMap.containsKey(id)) {
return (Short)idMap.get(id);
}
Short ans = new Short();
JSONArray valueArr = new JSONArray(); 
 valueArr = js.getJSONArray("values");
int index = 0;
JSONObject curPair;
curPair = new JSONObject();
curPair = valueArr.getJSONObject(index);
for (int i = 0; i < curPair.getJSONArray("data").length(); i++) {
ans.addData((short)curPair.getJSONArray("data").getInt(i));
}
index++;
idMap.put(id, ans);
return (Short)ans;
}
public static Short readShort(JSONObject js) throws JSONException {
return readHelperShort(js, new HashMap<Integer, Object>());
}
}

