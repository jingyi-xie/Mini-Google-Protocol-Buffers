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
  //Generate the code of array type field
  private void arrayCode(String name, String type, int dim) {
    resCode += "private " +  "ArrayList<" + getWrapper(type) + "> " + name + ";" + "\n";
  }
  private void generateCode() {
    this.codeStart();
    for (SingleFieldBuilder cur : fieldList) {
      if (!isArrayType(cur.getDimension())) {
        //System.out.println(cur.getFieldName() + "is dimension 0\n");
        this.nonArrayCode(cur.getFieldName(), cur.getFieldType());
      }
      else {
        //System.out.println(cur.getFieldName() + "is dimension" + cur.getDimension() + "\n");
        this.arrayCode(cur.getFieldName(), cur.getFieldType(), 1/*cur.getDimension()*/);
      }
    }
    this.codeEnd();
  }
  public String getCode() {
    this.generateCode();
    return resCode;
  }
}
