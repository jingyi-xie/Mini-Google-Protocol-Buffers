import java.util.*;
import org.json.*;
public class Grade {
private Course course;
private String student;
private double grade;
public Course getCourse() {
return course;
}
public void setCourse(Course x) {
this.course = x;
}
public String getStudent() {
return student;
}
public void setStudent(String x) {
this.student = x;
}
public double getGrade() {
return grade;
}
public void setGrade(double x) {
this.grade = x;
}
public JSONObject toJSONHelper(HashMap<String, JSONObject> map) throws JSONException {
JSONObject ans = new JSONObject();
ans.put("id", 1);
ans.put("type", "Grade");
JSONArray fieldValuePairs = new JSONArray();
JSONObject curPair;
curPair = new JSONObject();
curPair.put("course", course);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("student", student);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("grade", grade);
fieldValuePairs.add(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<String, JSONObject>());
}
}

