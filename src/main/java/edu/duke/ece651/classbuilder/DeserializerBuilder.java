package edu.duke.ece651.classbuilder;
import java.util.*;
import org.json.*;

public class DeserializerBuilder {
    private Map<String, ArrayList<SingleFieldBuilder>> myMap;
    private StringBuilder resCode;
    private String packageName;


    //Constructor for deserializer
    public DeserializerBuilder(Map<String, ArrayList<SingleFieldBuilder>> m, String pkgName) {
      this.myMap = m;
      this.resCode = new StringBuilder();
      this.packageName = pkgName;
    }
    //Get the package and import information
    private String getPkgNImport()  {
        StringBuilder res = new StringBuilder();
        if (!packageName.equals("")) {
          res.append("package " + packageName + ";\n");
        }
        res.append("import java.util.*" + ";\n");
        res.append("import java.lang.*" + ";\n");
        res.append("import org.json.*" + ";\n");
        return res.toString();
    }
    //Get the start code of deserializer
    private String getCodeStart() {
      return "public class Deserializer {\n";
    }
    //get the end code of deserializer
    private String getcodeEnd() {
      return "}" + "\n";
    }
    //Get the field declaration and constructor
    private String getFieldNConctructor() {
        StringBuilder res = new StringBuilder();
        res.append("HashMap<Integer, Object> idMap;" + "\n");
        res.append("public Deserializer() {" + "\n");
        res.append("this.idMap = new HashMap<>();");
        res.append("}" + "\n");
        return res.toString();
    }
    //Captialize the first character of typename
    private String capName(String name) {
      return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    //return true if primitive type or strings
    private boolean primitiveOrStr(String type) {
        return type.equals("boolean") || type.equals("byte") || type.equals("char") || type.equals("short") || 
              type.equals("int") || type.equals("long") || type.equals("float") || type.equals("double") || 
              type.equals("String");
    }
    //get the wrapper of primitive type
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
    //Generate the core part of deserializer
    private void generateCode() {
      for (Map.Entry<String, ArrayList<SingleFieldBuilder>> curClass : myMap.entrySet()) {
        resCode.append("public " + curClass.getKey() + " readHelper" + capName(curClass.getKey()) + "(JSONObject js, Map<Integer, Object> idMap) throws JSONException {\n");
        resCode.append("int id = js.getInt(\"id\");\n");
        //If already exists in map, use the reference to that object
        resCode.append("if (idMap.containsKey(id)) {\n");
        resCode.append("return (" + capName(curClass.getKey()) + ")idMap.get(id);\n");
        resCode.append("}\n");
        resCode.append(curClass.getKey() + " ans = new " + curClass.getKey() + "();\n");
        resCode.append("String type = js.getString(\"type\");\n");
        resCode.append("JSONArray valueArr = js.getJSONArray(\"values\");\n");
        resCode.append("int index = 0;\n");
        resCode.append("JSONObject curPair;\n");
        int obj_index = 0;
        for (SingleFieldBuilder curField : curClass.getValue()) {
          resCode.append("curPair = valueArr.getJSONObject(index);\n");
          if (primitiveOrStr(curField.getFieldType())) {
            //No getChar, use getInt instead, then cast to char
            if (curField.getFieldType().equals("char")) {
                if (curField.getDimension() == 0) {
                    resCode.append("ans.set" + capName(curField.getFieldName()) + "((char)curPair.getInt(\"" + curField.getFieldName() + "\"));\n");
                }
                else {
                    resCode.append("for (int i = 0; i < curPair.getJSONArray(\"" + curField.getFieldName() + "\").length(); i++) {\n");
                    resCode.append("ans.add" + capName(curField.getFieldName()) + "((char)curPair.getJSONArray(\"" + curField.getFieldName() + "\").getInt(i));\n");
                    resCode.append("}\n");
                }
            }
            //No getShort, use getInt instead, then cast to short
            else if (curField.getFieldType().equals("short")) {
                if (curField.getDimension() == 0) {
                    resCode.append("ans.set" + capName(curField.getFieldName()) + "((short)curPair.getInt(\"" + curField.getFieldName() + "\"));\n");
                }
                else {
                    resCode.append("for (int i = 0; i < curPair.getJSONArray(\"" + curField.getFieldName() + "\").length(); i++) {\n");
                    resCode.append("ans.add" + capName(curField.getFieldName()) + "((short)curPair.getJSONArray(\"" + curField.getFieldName() + "\").getInt(i));\n");
                    resCode.append("}\n");
                }
            }
            //No getByte, use getInt instead, then cast to byte
            else if (curField.getFieldType().equals("byte")) {
                if (curField.getDimension() == 0) {
                    resCode.append("ans.set" + capName(curField.getFieldName()) + "((byte)curPair.getInt(\"" + curField.getFieldName() + "\"));\n");
                }
                else {
                    resCode.append("for (int i = 0; i < curPair.getJSONArray(\"" + curField.getFieldName() + "\").length(); i++) {\n");
                    resCode.append("ans.add" + capName(curField.getFieldName()) + "((byte)curPair.getJSONArray(\"" + curField.getFieldName() + "\").getInt(i));\n");
                    resCode.append("}\n");
                }
            }
            //Other types
            else {
                if (curField.getDimension() == 0) {
                    resCode.append("ans.set" + capName(curField.getFieldName()) + "(curPair.get" + capName(curField.getFieldType()) + "(\"" + curField.getFieldName() + "\"));\n");
                }
                else {
                    resCode.append("for (int i = 0; i < curPair.getJSONArray(\"" + curField.getFieldName() + "\").length(); i++) {\n");
                    resCode.append("ans.add" + capName(curField.getFieldName()) + "(curPair.getJSONArray(\"" + curField.getFieldName() + "\").get" + capName(curField.getFieldType()) + "(i));\n");
                    resCode.append("}\n");
                }
            }
          }
          //Object type
          else {
              if (curField.getDimension() == 0) {
                resCode.append(curField.getFieldType() + " obj_" + obj_index + " = new " + curField.getFieldType() + "();\n");
                resCode.append(" obj_" + obj_index + " = readHelper" + capName(curField.getFieldType()) + "(js, idMap);\n");
                resCode.append("ans.set" + capName(curField.getFieldName()) + "(obj_" + obj_index + ");\n");
              }
              else {
                resCode.append("for (int i = 0; i < curPair.getJSONArray(\"" + curField.getFieldName() + "\").length(); i++) {\n");
                resCode.append("ans.add" + capName(curField.getFieldName()) + "(readHelper" + capName(curField.getFieldType()) + "(curPair.getJSONArray(\"" + curField.getFieldName() + "\").getJSONObject(i), idMap));\n");
                resCode.append("}\n");
              }          
          }
          resCode.append("index++;\n");
          obj_index++;
        }
        resCode.append("idMap.put(id, ans);\n");
        resCode.append("return (" + capName(curClass.getKey()) + ")ans;\n");
        resCode.append("}\n"); 
        resCode.append("public static " + curClass.getKey() + " read" + capName(curClass.getKey()) + "(JSONObject js) throws JSONException {\n");
        resCode.append("Deserializer ds = new Deserializer();\n");
        resCode.append("return ds.readHelper" + capName(curClass.getKey()) + "(js, ds.idMap);\n");
        resCode.append("}\n");
      }
    }
    //Get the code of deserializer
    public String getDeserializer() {
        this.generateCode();
        return this.getPkgNImport() + this.getCodeStart() + this.getFieldNConctructor() + this.resCode.toString() + this.getcodeEnd();
    }
}
