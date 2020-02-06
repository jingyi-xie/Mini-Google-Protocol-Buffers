package edu.duke.ece651.classbuilder;

import java.util.*;
import org.json.*;

public class SingleClassBuilder {
  private String className;
  private ArrayList<SingleFieldBuilder> fieldList;

  //Constructor for SingleClassBuilder
  public SingleClassBuilder(JSONObject jo) {
    this.fieldList  = new ArrayList<SingleFieldBuilder>();
    //Get className
    className = jo.getString("name");
    //Get the JSONArray associated with "fields"
    JSONArray fieldJArr = jo.optJSONArray("fields");
    //Declare a SingleFieldBuilde to handle the info of a single field in the current class
    for (int i = 0; i < fieldJArr.length(); i++) {
        JSONObject curJO =  fieldJArr.getJSONObject(i);
        SingleFieldBuilder curField = new SingleFieldBuilder(curJO);
        fieldList.add(curField);
    }
  }
  //Get the name of current class
  public String getClassName() {
    return className;
  }
  //Get the list of fields of current class
  public ArrayList<SingleFieldBuilder> getFieldList() {
    return this.fieldList;
  }
}
