package edu.duke.ece651.classbuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import org.json.*;

public class ClassBuilder {
  private Map<String, ArrayList<SingleFieldBuilder>> classMap;
  private String packageName;

  private void construct_helper(JSONObject jo) {
    packageName = jo.optString("package");
    JSONArray classJArr = jo.optJSONArray("classes");
    for (int i = 0; i < classJArr.length(); i++) {
        JSONObject curJO =  classJArr.getJSONObject(i);
        SingleClassBuilder curClass = new SingleClassBuilder(curJO);
        this.classMap.put(curClass.getClassName(), curClass.getFieldList());
    }
  }

  public ClassBuilder(String str) {
    this.classMap = new HashMap<>();
    JSONObject jo = new JSONObject(str);
    construct_helper(jo);
  }

  public ClassBuilder(InputStream input) {
    this.classMap = new HashMap<>();
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
    CodeBuilder codeB = new CodeBuilder(className, classMap.get(className), packageName);
    return codeB.getCode(); 
  }
  
  public void createAllClasses(String basePath) {
    if (packageName != null) {
      String pkgPath = packageName.replace('.', '/');
      if (basePath.charAt(basePath.length() -1) != '/') {
        basePath += "/" + pkgPath + "/";
      }
      else {
        basePath += pkgPath + "/";
      }
    }
    for (String curName : this.getClassNames()) {
      String path = basePath + curName + ".java";
      try {
        File file = new File(path);
        file.createNewFile();
        PrintWriter out = new PrintWriter(file);
        out.println(getSourceCode(curName));
        out.close();
      } catch (IOException e) {
        System.out.println("An IO exception occurred in createAllClassed.\n");
      }  
    }
    String deserialpath = basePath + "Deserializer.java";
    try {
      File file = new File(deserialpath);
      file.createNewFile();
      PrintWriter out = new PrintWriter(file);
      DeserializerBuilder db = new DeserializerBuilder(classMap, packageName);
      out.println(db.getDeserializer());
      out.close();
    } catch (IOException e) {
      System.out.println("An IO exception occurred in createAllClassed.\n");
    }  
  } 
}
