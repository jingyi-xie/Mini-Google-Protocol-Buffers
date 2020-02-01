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
    return true;
  }

  //Generate the start of source code
  private void codeStart() {
    resCode += "public class " + className + " {" + "\n";
  }
  //Generate the end of source code
  private void codeEnd() {
    resCode += "}";
  }
  //Generate the code of non-array type: field + method
  private void nonArrayCode(String name, String type) {
    resCode += "private " + type + " " + name + ";" + "\n";
  }
  private void generateCode() {
    this.codeStart();
    for (Map.Entry<String, String> cur : fieldMap.entrySet()) {
      if (isArrayType(cur.getValue())) {
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
