package edu.duke.ece651.classbuilder;

import java.util.*;
import org.json.*;

public class SingleClassBuilder {
private String className;
  private Map<String, String> fieldMap;

  public SingleClassBuilder(JSONObject jo) {
    this.fieldMap  = new LinkedHashMap<String, String>();
    className = jo.getString("name");
    JSONArray fieldJArr = jo.getJSONArray("fields");
    for (int i = 0; i < fieldJArr.length(); i++) {
        JSONObject curJO =  fieldJArr.getJSONObject(i);
        this.fieldMap.put(curJO.getString("name"), curJO.getString("type"));
    }
  }
  public String getClassName() {
    return className;
  }
  public Map<String, String> getFieldMap() {
    return this.fieldMap;
  }
}
