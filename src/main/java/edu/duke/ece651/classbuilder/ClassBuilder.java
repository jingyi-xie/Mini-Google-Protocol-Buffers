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

  //Parse the input JSON file, get the package name and the classMap
  private void construct_helper(JSONObject jo) {
    packageName = jo.optString("package");
    JSONArray classJArr = jo.optJSONArray("classes");
    for (int i = 0; i < classJArr.length(); i++) {
        JSONObject curJO =  classJArr.getJSONObject(i);
        //Declare a SingleClassBuilder to deal with imfo of a single class
        SingleClassBuilder curClass = new SingleClassBuilder(curJO);
        //Put the classname and fieldlist of a single class to the overall Map
        this.classMap.put(curClass.getClassName(), curClass.getFieldList());
    }
  }

  //Constructor for the String input
  public ClassBuilder(String str) {
    this.classMap = new HashMap<>();
    JSONObject jo = new JSONObject(str);
    construct_helper(jo);
  }

  //Constructor for the inputstream input
  public ClassBuilder(InputStream input) {
    this.classMap = new HashMap<>();
    JSONTokener tokener = new JSONTokener(input);
    JSONObject jo = new JSONObject(tokener);
    construct_helper(jo);
  }

  //Return all the classNames, which is the keySet of the classMap
  public Collection<String> getClassNames() {
    return new ArrayList<>(this.classMap.keySet());
  }
  
  //Declare a CodeBuilder to get the source code, throw exception if className doesn't exist
  public String getSourceCode(String className) {
    if (!classMap.keySet().contains(className)) {
        throw new NoSuchElementException("Failed to find class name!\n");
    }
    CodeBuilder codeB = new CodeBuilder(className, classMap.get(className), packageName);
    return codeB.getCode(); 
  }
  
  //Create all the class and Deserializer
  public void createAllClasses(String basePath) {
    //Handle package
    if (packageName.length() == 0) {
      String pkgPath = packageName.replace('.', '/');
      if (basePath.charAt(basePath.length() -1) != '/') {
        basePath += "/" + pkgPath + "/";
      }
      else {
        basePath += pkgPath + "/";
      }
    }
    //For all the classes returned by getClassNames, generate the source code.
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
    //Generate the deserializer returned by a DeserializerBuilder object
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
