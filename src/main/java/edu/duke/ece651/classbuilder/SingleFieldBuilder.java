package edu.duke.ece651.classbuilder;
import org.json.*;

public class SingleFieldBuilder {
  private String fieldName;
  private String fieldType;
  private int dimension;

  private void calDimension(JSONObject obj) {
    while(obj.optJSONObject("e") != null) {
      dimension++;
      obj = obj.optJSONObject("e");
    }
    this.fieldType = obj.getString("e");
    dimension++;
  }
  public SingleFieldBuilder(JSONObject jo) {
    this.fieldName = jo.getString("name");
    if (jo.optJSONObject("type") == null) {
      this.fieldType = jo.getString("type");
      this.dimension = 0;
    }
    else {
      JSONObject typeObj = jo.getJSONObject("type");
      this.calDimension(typeObj);
    }
  }

  public String getFieldName() {
    return fieldName;
  }
  public String getFieldType() {
    return fieldType;
  }
  public int getDimension() {
    return dimension;
  }
}
