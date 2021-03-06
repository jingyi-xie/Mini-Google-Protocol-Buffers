package edu.duke.ece651.classbuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBuilder {
  private String className;
  private String packageName;
  private ArrayList<SingleFieldBuilder> fieldList;
  private StringBuilder constructer;
  private StringBuilder fieldCode;
  private StringBuilder methodCode;
  private boolean hasNoField;
  private boolean hasNoArray;

  //Constructor for CodeBuilde
  public CodeBuilder(String name, ArrayList<SingleFieldBuilder> list, String pkgName) {
    this.className = name;
    this.packageName = pkgName;
    this.fieldList = list;
    this.hasNoField = (list.size() == 0);
    this.hasNoArray = true;
    this.constructer = new StringBuilder();
    this.fieldCode = new StringBuilder();
    this.methodCode = new StringBuilder();
  }
  //Code for package and import
  private String getPkgNImport()  {
    StringBuilder res = new StringBuilder();
    if (!packageName.equals("")) {
      res.append("package " + packageName + ";" + "\n");
    }
    res.append("import java.util.*" + ";" + "\n");
    res.append("import java.lang.*" + ";" + "\n");
    res.append("import org.json.*" + ";" + "\n");
    return res.toString();
  }
  //Generate the start of source code
  private String getCodeStart() {
    return "public class " + className + " {" + "\n";
  }
  //Generate the end of source code
  private String getcodeEnd() {
    return "}" + "\n";
  }
  //Get the capitalized field name
  private String capName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
  //Get the wrapper of type
  private String getWrapper(String type) {
    if (type.equals("boolean")) {
      return "Boolean";
    }
    if (type.equals("byte")) {
      return "Byte";
    }
    if (type.equals("char")) {
      return "Character";
    }
    if (type.equals("short")) {
      return "Short";
    }
    if (type.equals("int")) {
      return "Integer";
    }
    if (type.equals("long")) {
      return "Long";
    }
    if (type.equals("float")) {
      return "Float";
    }
    if (type.equals("double")) {
      return "Double";
    }
    return type;
  }
  //Add the current field to the constructor
  private void addToConstruct(SingleFieldBuilder curField) {
    //int curDim = curField.getDimension();  
    String temp = getWrapper(curField.getFieldType());
    temp = "this." + curField.getFieldName() + " = new ArrayList<>()" + ";" + "\n";
    this.constructer.append(temp);
  }
  //Get the constructor: start + nody + end
  private String getConstructor() {
    String constructStart = "public " + className + "()" + " {" + "\n";  
    String constructEnd = "}" + "\n";
    return constructStart + this.constructer + constructEnd;
  }
  //Generate the code of non-array type: field + method
  private void nonArrayCode(String name, String type) {
    fieldCode.append("private " + type + " " + name + ";" + "\n");
    String capFieldName = capName(name);
    //Get method
    methodCode.append("public " + type + " get" + capFieldName + "() {" + "\n");
    methodCode.append("return " + name + ";" + "\n");
    methodCode.append("}" + "\n");  
    //Set method
    methodCode.append("public void set" + capFieldName + "(" + type + " x) {" + "\n");
    methodCode.append("this." + name + " = x;" + "\n");
    methodCode.append("}" + "\n");  
  }
  //Get the Collection of multi-dimensional array, then use getArrayList to add the outer layer
  private String getNestCollect(String type, int dim) {
    if (dim == 0) {
      return type;
    }
    String res = type;
    for (int i = 0; i < dim; i++) {
      res = "Collection<" + res + ">";
    }
    return res;
  }
  //Get the type of multi-dimensinal array
  private String getNestArrList(String input) {
    return "ArrayList<" + input + ">";
  }
  //Return true if primitive type or String
  private boolean primitiveOrStr(String type) {
    return type.equals("boolean") || type.equals("byte") || type.equals("char") || type.equals("short") || 
          type.equals("int") || type.equals("long") || type.equals("float") || type.equals("double") || 
          type.equals("String");
  }
  //Get the code of serializer
  private String getSerializer() {
    StringBuilder resStr = new StringBuilder();
    //Serializer helper function
    resStr.append("public JSONObject toJSONHelper(HashMap<Object, Integer> map) throws JSONException {" + "\n");
    resStr.append("JSONObject ans = new JSONObject();" + "\n");
    resStr.append("if (map.containsKey(this)) {" + "\n");
    resStr.append("ans.put(\"ref\", map.get(this));" + "\n");
    resStr.append("return ans;" + "\n");
    resStr.append("}" + "\n");
    resStr.append("int uniqueid = map.size() + 1;" + "\n");
    resStr.append("ans.put(\"id\", uniqueid);" + "\n");
    resStr.append("map.put(this, uniqueid);" + "\n");
    resStr.append("ans.put(\"type\", \"" + className + "\");" + "\n");
    resStr.append("JSONArray fieldValuePairs = new JSONArray();" + "\n");
    resStr.append("JSONArray arrayField; // for array type field only" + "\n");
    resStr.append("JSONObject curPair;" + "\n");
    for (SingleFieldBuilder curField : fieldList) {
      resStr.append("curPair = new JSONObject();" + "\n");
      if (primitiveOrStr(curField.getFieldType())) {
        resStr.append("curPair.put(\"" + curField.getFieldName() + "\", this." + curField.getFieldName() + ");" + "\n");
      }
      else if (curField.getDimension() == 0) {
        resStr.append("curPair.put(\"" + curField.getFieldName() + "\"," + curField.getFieldName() + ".toJSONHelper(map));" + "\n");
      }  
      else {
        resStr.append("arrayField = new JSONArray();" + "\n");
        resStr.append("JSONObject curEle;" + "\n");
        resStr.append("for (int i = 0; i < " + curField.getFieldName() + ".size(); i++) {" + "\n");
        resStr.append("curEle = new JSONObject();" + "\n");      
        resStr.append("curEle.put(\"" + curField.getFieldName() + "\", this." + curField.getFieldName() + ".get(i).toJSONHelper(map));" + "\n");      
        resStr.append("arrayField.put(curEle);" + "\n"); 
        resStr.append("}" + "\n");
        resStr.append("curPair.put(\"" + curField.getFieldName() + "\", arrayField);" + "\n");
      }           
      resStr.append("fieldValuePairs.put(curPair);" + "\n");
    }    
    resStr.append("ans.put(\"values\", fieldValuePairs);" + "\n"); 
    resStr.append("return ans;" + "\n");
    resStr.append("}" + "\n" + "\n"); 
    //toJSON method
    resStr.append("public JSONObject toJSON() throws JSONException {" + "\n");
    resStr.append("return toJSONHelper(new HashMap<Object, Integer>());" + "\n");
    resStr.append("}" + "\n");
    return resStr.toString();
  }

  //Generate the code of array type field
  private void arrayCode(String name, String type, int dim) {
    String nestCollection = getNestCollect(getWrapper(type), dim - 1);
    String nestArrList = getNestArrList(nestCollection);
    fieldCode.append("private " +  nestArrList + " " + name + ";" + "\n");
    String capFieldName = capName(name);
    //num method
    methodCode.append("public int " + "num" + capFieldName + "() {" + "\n");
    methodCode.append("return " + name + ".size();" + "\n");
    methodCode.append("}" + "\n");
    //add method
    methodCode.append("public void add" + capFieldName + "(" + (dim == 1 ? type : nestCollection) + " x) {" + "\n");
    methodCode.append(name + ".add(x);" + "\n");
    methodCode.append("}" + "\n"); 
    //Get method
    methodCode.append("public " + (dim == 1 ? type : nestCollection) + " get" + capFieldName + "(int index) {" + "\n");
    methodCode.append("return " + name + ".get(index);" + "\n");
    methodCode.append("}" + "\n");  
    //Set method
    methodCode.append("public void set" + capFieldName + "(int index, " + (dim == 1 ? type : nestCollection) + " x) {" + "\n");
    methodCode.append(name + ".set(index, x);" + "\n");
    methodCode.append("}" + "\n");
  }
  private void generateCode() {
    //this.codeStart();
    //this.pkgNImport();
    for (SingleFieldBuilder cur : fieldList) {
      if (cur.getDimension() == 0) {
        //System.out.println(cur.getFieldName() + "is dimension 0\n");
        this.nonArrayCode(cur.getFieldName(), cur.getFieldType());
      }
      else {
        this.hasNoArray = false;
        //System.out.println(cur.getFieldName() + "is dimension" + cur.getDimension() + "\n");
        this.arrayCode(cur.getFieldName(), cur.getFieldType(), cur.getDimension());
        this.addToConstruct(cur);
      }
    }
  }
  //Get the source code of the current class
  public String getCode() {
    //If no field
    if (hasNoField) {
      return this.getPkgNImport() + this.getCodeStart() + this.getSerializer() + this.getcodeEnd();
    }
    this.generateCode();
    //If only primitive type and stings
    if (this.hasNoArray) {
      return this.getPkgNImport() + this.getCodeStart() +  this.fieldCode.toString()  +  this.methodCode.toString() + this.getSerializer() + this.getcodeEnd();
    }
    return this.getPkgNImport() + this.getCodeStart() +  this.fieldCode.toString() + this.getConstructor() +  this.methodCode.toString() + this.getSerializer() + this.getcodeEnd();
  }
}
