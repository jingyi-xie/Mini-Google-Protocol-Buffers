package edu.duke.ece651.classbuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBuilder {
  private String className;
  private String packageName;
  private ArrayList<SingleFieldBuilder> fieldList;
  private String constructer;
  private String fieldCode;
  private String methodCode;
  private boolean hasNoField;

  public CodeBuilder(String name, ArrayList<SingleFieldBuilder> list, String pkgName) {
    this.className = name;
    this.packageName = pkgName;
    this.fieldList = list;
    this.hasNoField = (list.size() == 0);
    this.constructer = "";
    this.fieldCode = "";
    this.methodCode = "";
  }
  //Code for package and import
  private String getPkgNImport()  {
    String res = "";
    if (!packageName.equals("")) {
      res += "package " + packageName + ";" + "\n";
    }
    res += "import java.util.*" + ";" + "\n";
    res += "import org.json.*" + ";" + "\n";
    return res;
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
  //
  private void addToConstruct(SingleFieldBuilder curField) {
    //int curDim = curField.getDimension();  
    String temp = getWrapper(curField.getFieldType());
    temp = "this." + curField.getFieldName() + " = new ArrayList<>()" + ";" + "\n";
    this.constructer += temp;
  }
  private String getConstructor() {
    String constructStart = "public " + className + "()" + " {" + "\n";  
    String constructEnd = "}" + "\n";
    return constructStart + this.constructer + constructEnd;
  }
  //Generate the code of non-array type: field + method
  private void nonArrayCode(String name, String type) {
    fieldCode += "private " + type + " " + name + ";" + "\n";
    String capFieldName = capName(name);
    //Get method
    methodCode += "public " + type + " get" + capFieldName + "() {" + "\n";
    methodCode += "return " + name + ";" + "\n";
    methodCode += "}" + "\n";  
    //Set method
    methodCode += "public void set" + capFieldName + "(" + type + " x) {" + "\n";
    methodCode += "this." + name + " = x;" + "\n";
  }
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
  private String getNestArrList(String input) {
    return "ArrayList<" + input + ">";
  }
  //Generate the code of array type field
  private void arrayCode(String name, String type, int dim) {
    String nestCollection = getNestCollect(getWrapper(type), dim - 1);
    String nestArrList = getNestArrList(nestCollection);
    fieldCode += "private " +  nestArrList + " " + name + " ;" + "\n";
    String capFieldName = capName(name);
    //num method
    methodCode += "public int " + "num" + capFieldName + "() {" + "\n";
    methodCode += "return " + name + ".size();" + "\n";
    methodCode += "}" + "\n";
    //add method
    methodCode += "public void add" + capFieldName + "(" + nestCollection + " x) {" + "\n";
    methodCode += name + ".add(x);" + "\n";
    methodCode += "}" + "\n"; 
    //Get method
    methodCode += "public " + nestCollection + " get" + capFieldName + "(int index) {" + "\n";
    methodCode += "return " + name + ".get(index);" + "\n";
    methodCode += "}" + "\n";  
    //Set method
    methodCode += "public void set" + capFieldName + "(int index, " + nestCollection + " x) {" + "\n";
    methodCode += name + ".set(index, x);" + "\n";
    methodCode += "}" + "\n";
  }
  private void generateCode() {
    //this.codeStart();
    //this.pkgNImport();
    for (SingleFieldBuilder cur : fieldList) {
      if (cur.getDimension() == 0) {
        //System.out.println(cur.getFieldName() + "is dimension 0\n");
        this.nonArrayCode(cur.getFieldName(), cur.getFieldType());
      }
      else if (cur.getDimension() >= 1) {
        //System.out.println(cur.getFieldName() + "is dimension" + cur.getDimension() + "\n");
        this.arrayCode(cur.getFieldName(), cur.getFieldType(), cur.getDimension());
        this.addToConstruct(cur);
      }
    }
  }
  public String getCode() {
    if (hasNoField) {
      return this.getPkgNImport() + this.getCodeStart() + this.getcodeEnd();
    }
    this.generateCode();
    return this.getPkgNImport() + this.getCodeStart() +  this.fieldCode + this.getConstructor() +  this.methodCode + this.getcodeEnd();
  }
}
