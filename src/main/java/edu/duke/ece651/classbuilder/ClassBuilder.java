package edu.duke.ece651.classbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Iterator; 
//import java.util.Map;
import java.io.InputStream;
import org.json.*;
import java.io.PrintWriter;

public class ClassBuilder {
  // private String className;
  // private Map<String, String> fields;
  public String className;
  public String name;
  public String type;
  // public Map<String, String> fields;

  private void construct_helper(JSONObject jo) {
    JSONArray classes = jo.getJSONArray("classes");
    JSONObject oneclass = classes.getJSONObject(0);
    // Iterator it = classes.iterator();

    className = oneclass.getString("name");
    JSONArray ja = oneclass.getJSONArray("fields");
    JSONObject onefield = ja.getJSONObject(0);
    name = onefield.getString("name");
    type = onefield.getString("type");
    // fields.put(onefield.getString("name"), onefield.getString("type"));
  }

  public ClassBuilder(String str) {
    JSONObject jo = new JSONObject(str);
    construct_helper(jo);
  }

  public ClassBuilder(InputStream input) {
    JSONObject jo = new JSONObject(input);
    construct_helper(jo);
  }

  public String getSourceCode(/* String className */) {
    String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
    return "public class " + className + "{\n private " + type + " " + name + ";\n public int get" + capName
        + "() {\n return " + name + ";\n }\n  public void set" + capName + "(" + type + " x) { \n this." + name
        + " = x; }}";
  }

  public void createAllClasses(String basePath) throws FileNotFoundException {
    String path = basePath + className + ".java";
    File file = new File(path);
    PrintWriter out = new PrintWriter(file);
    out.println(getSourceCode());
    out.close();
  }
    
/*
class Main {
  public static void main(String[] args) {
    String str = "{\"classes\" : [{\"name\" : \"Test\", \"fields\" : [{\"name\" : \"x\", \"type\" : \"int\"}]}]}";
    ClassBuilder cb = new ClassBuilder(str);
    System.out.println(cb.className);
    System.out.println(cb.name);
    System.out.println(cb.type);
    /*
    for (Map.Entry<String, String> entry : cb.fields.entrySet()) {
      String k = entry.getKey();
      String v = entry.getValue();
      System.out.println("Key: " + k + ", Value: " + v);
    }
    
  }
*/
}
