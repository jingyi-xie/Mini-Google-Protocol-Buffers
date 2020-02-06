package edu.duke.ece651.classbuilder;
import java.util.*;
import org.json.*;

public class DeserializerBuilder {
    private Map<String, ArrayList<SingleFieldBuilder>> myMap;
    private StringBuilder resCode;
    private String packageName;

    public DeserializerBuilder(Map<String, ArrayList<SingleFieldBuilder>> m, String pkgName) {
      this.myMap = m;
      this.resCode = new StringBuilder();
      this.packageName = pkgName;
    }
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
    private String getCodeStart() {
      return "public class Deserializer {\n";
    }
    private String getcodeEnd() {
      return "}" + "\n";
    }
    private String getFieldNConctructor() {
        StringBuilder res = new StringBuilder();
        res.append("Map<Integer, Object> idMap;" + "\n");
        res.append("public Deserializer() {" + "\n");
        res.append("idMap = new Map<>();");
        res.append("}" + "\n");
        return res.toString();
    }
    private String capName(String name) {
      return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    private boolean primitiveOrStr(String type) {
        return type.equals("boolean") || type.equals("byte") || type.equals("char") || type.equals("short") || 
              type.equals("int") || type.equals("long") || type.equals("float") || type.equals("double") || 
              type.equals("String");
    }
    private void generateCode() {

        //JSONObject js;////////
        //Map<Integer, Object> idMap; //////

      for (Map.Entry<String, ArrayList<SingleFieldBuilder>> curClass : myMap.entrySet()) {
        resCode.append("public static " + curClass.getKey() + " read" + capName(curClass.getKey()) + "(JSONObject js) throws JSONException {\n");
        resCode.append("int id = js.optInt(\"id\");\n");
        resCode.append("if (id != null && idMap.containsKey(id)) {\n");
        resCode.append("return idMap.get(id);\n");
        resCode.append(curClass.getKey() + "ans = new " + curClass.getKey() + "();\n");
        resCode.append("String type = js.optString(\"type\");\n");
        resCode.append("JSONArray valueArr = js.optJSONArray(\"values\");\n");
        resCode.append("int index = 0;\n");
        for (SingleFieldBuilder curField : curClass.getValue()) {
          resCode.append("JSONObject curPair = valueArr.get(index);\n");
          if (primitiveOrStr(curField.getFieldType())) {
            if (curField.getFieldType().equals("char")) {
                resCode.append("ans.set" + capName(curField.getFieldName()) + "(curPair.getString(\"" + curField.getFieldName() + "\").charAt(0));\n");
            }
            else {
                resCode.append("ans.set" + capName(curField.getFieldName()) + "(curPair.get" + capName(curField.getFieldType()) + "(\"" + curField.getFieldName() + "\").charAt(0));\n");
            }
          }
          resCode.append("index++;\n");
        }
        resCode.append("idMap.put(id, ans);\n");
        resCode.append("return ans;\n");
        resCode.append("}\n"); 
      }
    }
    public String getDeserializer() {
        this.generateCode();
        return this.getPkgNImport() + this.getCodeStart() + this.getFieldNConctructor() + this.resCode.toString() + this.getcodeEnd();
    }
}
