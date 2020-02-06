package edu.duke.ece651.classbuilder;
import org.json.*;

public class SingleFieldBuilder {
  private String fieldName;
  private String fieldType;
  private int dimension;

  //Calculate the dimension of the array
  private void calDimension(JSONObject obj) {
    while(obj.optJSONObject("e") != null) {
      dimension++;
      obj = obj.optJSONObject("e");
    }
    this.fieldType = obj.getString("e");
    dimension++;
  }
  //Constructor for the SingleFieldBuilder
  public SingleFieldBuilder(JSONObject jo) {
    this.fieldName = jo.getString("name");
    //If "type" is a string, it's not an array type
    if (jo.optJSONObject("type") == null) {
      this.fieldType = jo.getString("type");
      //If not array type, dimension is 0
      this.dimension = 0;
    }
    else {
      JSONObject typeObj = jo.getJSONObject("type");
      this.calDimension(typeObj);
    }
  }

  //get the name of current field
  public String getFieldName() {
    return fieldName;
  }
  //get the type of current field
  public String getFieldType() {
    return fieldType;
  }
  //get the dimension of current field
  public int getDimension() {
    return dimension;
  }
}
