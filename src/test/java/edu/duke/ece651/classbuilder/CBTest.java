package edu.duke.ece651.classbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
//import java.util.Map;
import java.io.*;

public class CBTest {
  @Test
  public void test_classbuilder() {
    String r = "{ 'classes' :[{'name' : 'Course', 'fields' : [ {'name' : 'instructor', 'type' : 'Faculty'},{'name' : 'numStudents', 'type' : 'int' },{'name' : 'grades', 'type' :'Grade'}]},{'name' : 'Office',  'fields' : [ {'name' : 'occupant', 'type': 'Faculty'},{'name' : 'number', 'type': 'int'},{'name' : 'building' , 'type': 'String'}]},{'name' : 'Faculty', 'fields' : [ {'name' : 'name', 'type' : 'String' },{'name' : 'taught', 'type' : Course'}]},{'name' : 'Grade', 'fields' : [ {'name' : 'course', 'type' : 'Course'},{'name' : 'student', 'type' : 'String'},{'name' : 'grade', 'type': 'double'}]}]}";
    //InputStream r = getClass().getResourceAsStream("/md-array.json");
    ClassBuilder cb = new ClassBuilder(r);
    System.out.println(cb.getClassNames());
    System.out.println(cb.getSourceCode("Grade"));
    //cb.createAllClasses("/home/jaryn/jx95-json-651/sourceCode");
  }
}
