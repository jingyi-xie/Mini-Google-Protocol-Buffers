package edu.duke.ece651.classbuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBuilder {
  private String className;
  private ArrayList<SingleFieldBuilder> fieldList;
  private String resCode;

  public CodeBuilder(String name, ArrayList<SingleFieldBuilder> list) {
    this.className = name;
    this.fieldList = list;
    this.resCode = "";
  }

  private boolean isArrayType(int dimension) {
    return dimension != 0;
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
    for (SingleFieldBuilder cur : fieldList) {
      if (!isArrayType(cur.getDimension())) {
        nonArrayCode(cur.getFieldName(), cur.getFieldType());
      }
    }
    this.codeEnd();
  }
  public String getCode() {
    this.generateCode();
    return resCode;
  }
}
