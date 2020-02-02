package edu.duke.ece651.classbuilder;
import org.json.*;

public class SingleFieldBuilder {
  private String fieldName;
  private String fieldType;
  private int dimension;

  public SingleFieldBuilder(JSONObject jo) {
    this.fieldName = jo.getString("name");
    this.fieldType = jo.getString("type");
    this.dimension = 0;
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
