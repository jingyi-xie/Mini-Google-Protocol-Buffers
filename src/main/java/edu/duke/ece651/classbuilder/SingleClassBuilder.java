package edu.duke.ece651.classbuilder;

import java.util.*;
import org.json.*;

public class SingleClassBuilder {
  private String className;
  private ArrayList<SingleFieldBuilder> fieldList;

  public SingleClassBuilder(JSONObject jo) {
    this.fieldList  = new ArrayList<SingleFieldBuilder>();
    className = jo.getString("name");
    JSONArray fieldJArr = jo.optJSONArray("fields");
    for (int i = 0; i < fieldJArr.length(); i++) {
        JSONObject curJO =  fieldJArr.getJSONObject(i);
        SingleFieldBuilder curField = new SingleFieldBuilder(curJO);
        fieldList.add(curField);
    }
  }
  public String getClassName() {
    return className;
  }
  public ArrayList<SingleFieldBuilder> getFieldList() {
    return this.fieldList;
  }
}
