package edu.duke.ece651.classbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
//import java.util.Map;
import java.io.*;

public class CBTest {
  @Test
  public void test_classbuilder() {
    String r = "{\"classes\" : [{\"name\" : \"Test\", \"fields\" : [{\"name\" : \"x\", \"type\" : \"int\"}]}]}";
                                         
    //String path = "/home/jaryn/jx95-json-651/json/simple.json";
    //InputStream inputStream = new FileInputStream(new File(path));   
   ClassBuilder c = new ClassBuilder(r);
   System.out.println(c.className);
   System.out.println(c.name);
   System.out.println(c.type);
   System.out.println(c.getSourceCode());
   c.createAllClasses("/home/jaryn/jx95-json-651");
    //assertEquals(c.className, "Test");
    //assertEquals(c.name, "x");
    //assertEquals(c.type, "int");
    
    
    //ClassBuilder cb = new ClassBuilder(str);
  
    
    
  
  
  }
}
