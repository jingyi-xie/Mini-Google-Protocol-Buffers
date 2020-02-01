package edu.duke.ece651.classbuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import org.json.*;

public class ClassBuilder {
  private LinkedHashMap<String, Map<String, String>> classMap;
  private String packageName;

  private void construct_helper(JSONObject jo) {
    //packageName = jo.getString("package");
    JSONArray classJArr = jo.getJSONArray("classes");
    for (int i = 0; i < classJArr.length(); i++) {
        JSONObject curJO =  classJArr.getJSONObject(i);
        SingleClassBuilder curClass = new SingleClassBuilder(curJO);
        this.classMap.put(curClass.getClassName(), curClass.getFieldMap());
    }
  }

  public ClassBuilder(String str) {
    this.classMap = new LinkedHashMap<>();
    JSONObject jo = new JSONObject(str);
    construct_helper(jo);
  }

  public ClassBuilder(InputStream input) {
    this.classMap = new LinkedHashMap<>();
    JSONTokener tokener = new JSONTokener(input);
    JSONObject jo = new JSONObject(tokener);
    construct_helper(jo);
  }

  public Collection<String> getClassNames() {
    return new ArrayList<>(this.classMap.keySet());
  }
  public String getSourceCode(String className) {
    if (!classMap.keySet().contains(className)) {
        throw new NoSuchElementException("Failed to find class name!\n");
    }
    CodeBuilder codeB = new CodeBuilder(className, (LinkedHashMap)classMap.get(className)); 
    return codeB.getCode(); 
  }
  /*
  public void createAllClasses(String basePath) {
    String path = basePath + className + ".java";
    try {
      File file = new File(path);
      file.createNewFile();
      PrintWriter out = new PrintWriter(file);
      out.println(getSourceCode());
      out.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
    }  
    
  } */
    
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
