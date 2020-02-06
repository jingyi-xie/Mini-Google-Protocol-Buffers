package edu.duke.ece651.classbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
//import java.util.Map;
import java.io.*;
import java.util.*;
import java.lang.*;

public class CBTest {
  @Test
  public void test_string1() {
    String r = "{ 'classes' :[{'name' : 'Course', 'fields' : [ {'name' : 'instructor', 'type' : 'Faculty'},{'name' : 'numStudents', 'type' : 'int' },{'name' : 'grades', 'type' :'Grade'}]},{'name' : 'Office',  'fields' : [ {'name' : 'occupant', 'type': 'Faculty'},{'name' : 'number', 'type': 'int'},{'name' : 'building' , 'type': 'String'}]},{'name' : 'Faculty', 'fields' : [ {'name' : 'name', 'type' : 'String' },{'name' : 'taught', 'type' : Course'}]},{'name' : 'Grade', 'fields' : [ {'name' : 'course', 'type' : 'Course'},{'name' : 'student', 'type' : 'String'},{'name' : 'grade', 'type': 'double'}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_nameNoeExist() {
    String r = "{ 'classes' :[{'name' : 'Course', 'fields' : [ {'name' : 'instructor', 'type' : 'Faculty'},{'name' : 'numStudents', 'type' : 'int' },{'name' : 'grades', 'type' :'Grade'}]},{'name' : 'Office',  'fields' : [ {'name' : 'occupant', 'type': 'Faculty'},{'name' : 'number', 'type': 'int'},{'name' : 'building' , 'type': 'String'}]},{'name' : 'Faculty', 'fields' : [ {'name' : 'name', 'type' : 'String' },{'name' : 'taught', 'type' : Course'}]},{'name' : 'Grade', 'fields' : [ {'name' : 'course', 'type' : 'Course'},{'name' : 'student', 'type' : 'String'},{'name' : 'grade', 'type': 'double'}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    System.out.println(cb.getClassNames());
    assertThrows(NoSuchElementException.class, () -> {  cb.getSourceCode("Person"); } );
    cb.createAllClasses("src/test/resources/create_results/");
  }
  

  @Test
  public void test_empty() {
    InputStream r = getClass().getResourceAsStream("/empty.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("/home/jaryn/ece651-hwk1-tester/src/main/java");
    cb.createAllClasses("src/test/resources/create_results");
  }
  
  @Test
  public void test_simple() {
    InputStream r = getClass().getResourceAsStream("/simple.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("/home/jaryn/ece651-hwk1-tester/src/main/java");
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_prims() {
    InputStream r = getClass().getResourceAsStream("/prims.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("/home/jaryn/ece651-hwk1-tester/src/main/java");
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_simplearray() {
    InputStream r = getClass().getResourceAsStream("/simplearray.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("/home/jaryn/ece651-hwk1-tester/src/main/java");
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_nameRef() {
    InputStream r = getClass().getResourceAsStream("/nameRef.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("/home/jaryn/ece651-hwk1-tester/src/main/java");
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_boolean() {
    String r = "{'classes' :[{'name' : 'Boolean', 'fields' : [ {'name' : 'data', 'type': {'e': 'boolean'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results/");

  }

  @Test
  public void test_byte() {
    String r = "{'classes' :[{'name' : 'Byte', 'fields' : [ {'name' : 'data', 'type': {'e': 'byte'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_char() {
    String r = "{'classes' :[{'name' : 'Char', 'fields' : [ {'name' : 'data', 'type': {'e': 'char'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_short() {
    String r = "{'classes' :[{'name' : 'Short', 'fields' : [ {'name' : 'data', 'type': {'e': 'short'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results/");
  }

  @Test
  public void test_long() {
    String r = "{'classes' :[{'name' : 'Long', 'fields' : [ {'name' : 'data', 'type': {'e': 'long'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_float() {
    String r = "{'classes' :[{'name' : 'Float', 'fields' : [ {'name' : 'data', 'type': {'e': 'float'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_double() {
    String r = "{'classes' :[{'name' : 'Double', 'fields' : [ {'name' : 'data', 'type': {'e': 'double'}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results/");
  }

  @Test
  public void test_array3d() {
    InputStream r = getClass().getResourceAsStream("/md-array.json");
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_array4d() {
    String r = "{ 'classes' :[{'name' : 'array3d', 'fields' : [ {'name' : 'data', 'type': {'e' : {'e': {'e' : {'e' : 'int'}}}}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }

  @Test
  public void test_chararray() {
    String r = "{ 'classes' :[{'name' : 'chararray', 'fields' : [ {'name' : 'data', 'type': {'e' : {'e': {'e' : {'e' : 'char'}}}}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results/");
  }

  @Test
  public void test_bytearray() {
    String r = "{ 'classes' :[{'name' : 'bytearray', 'fields' : [ {'name' : 'data', 'type': {'e' : {'e': {'e' : {'e' : 'byte'}}}}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results/");
  }

  @Test
  public void test_shortarray() {
    String r = "{ 'classes' :[{'name' : 'shortarray', 'fields' : [ {'name' : 'data', 'type': {'e' : {'e': {'e' : {'e' : 'short'}}}}}]}]}";
    ClassBuilder cb = new ClassBuilder(r);
    cb.createAllClasses("src/test/resources/create_results");
  }


}