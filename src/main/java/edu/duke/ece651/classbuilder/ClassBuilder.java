package edu.duke.ece651.classbuilder;

import org.json.*;

public class ClassBuilder {
  private String name;
  public ClassBuilder(String str) {
    JSONObject j = new JSONObject(str); 
    name = j.getString("name");
  }

  public String getClassNames() {
    return name;
  }

  //public ClassBuilder(InputStream input) {
  //}
  
  public static void main(String[] args) {
    ClassBuilder cb = new ClassBuilder("{\"name\" : \"Test\"}"); 
    String name = cb.getClassNames();
    System.out.println(name);
  }
}
