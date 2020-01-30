package edu.duke.ece651.classbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Map;

public class CBTest {
  @Test
  public void test_classbuilder() {
    String str = "{\"classes\" : [{\"name\" : \"Test\", \"fields\" : [{\"name\" : \"x\", \"type\" : \"int\"}]}]}";
    ClassBuilder cb = new ClassBuilder(str);
    assertEquals(cb.className, "Test");
    assertEquals(cb.name, "x");
    assertEquals(cb.type, "int");
    
    
    
  }
  

}
