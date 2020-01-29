package edu.duke.ece651.classbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CBTest {
  @Test
  public void test_classbuilder() {
    ClassBuilder cb = new ClassBuilder("{\"name\" : \"Test\"}");
    assertEquals(cb.getClassNames(), "Test");
  }
  

}
