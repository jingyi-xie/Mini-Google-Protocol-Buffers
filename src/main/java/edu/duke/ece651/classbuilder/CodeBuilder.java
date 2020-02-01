package edu.duke.ece651.classbuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBuilder {
private String className;
private LinkedHashMap<String, String> fieldMap;
private String resCode;

public CodeBuilder(String name, LinkedHashMap<String, String> map) {
  this.className = name;
  this.fieldMap = map;
  this.resCode = "";
}

private boolean isArrayType(String typeName) {
  //To-do: whether is array type, contain "e"?
  return false;
}

//Generate the start of source code
private void codeStart() {
  resCode += "public class " + className + " {" + "\n";
}
//Generate the end of source code
private void codeEnd() {
  resCode += "}";
}
//Get the capitalized field name
private String capName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
}
//Generate the code of non-array type: field + method
private void nonArrayCode(String name, String type) {
  resCode += "private " + type + " " + name + ";" + "\n";
  String capFieldName = capName(name);
  //Get method
  resCode += "public " + type + " get" + capFieldName + "() {" + "\n";
  resCode += "return " + name + ";" + "\n";
  resCode += "}" + "\n"; 
  //Set method
  resCode += "public void set" + capFieldName + "(" + type + " x) {" + "\n";
  resCode += "this." + name + " = x;" + "\n";
  resCode += "}" + "\n";
}
private void generateCode() {
  this.codeStart();
  for (Map.Entry<String, String> cur : fieldMap.entrySet()) {
    if (!isArrayType(cur.getValue())) {
      nonArrayCode(cur.getKey(), cur.getValue());
    }
  }
  this.codeEnd();
}
public String getCode() {
  this.generateCode();
  return resCode;
}
}
