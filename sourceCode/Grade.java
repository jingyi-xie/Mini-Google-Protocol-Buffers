import java.util.*;
import java.lang.*;
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
public JSONObject toJSONHelper(HashMap<Object, Integer> map) throws JSONException {
JSONObject ans = new JSONObject();
if (map.containsKey(this)) {
ans.put("ref", map.get(this));
return ans;
}
int uniqueid = map.size() + 1;
ans.put("id", uniqueid);
map.put(this, uniqueid);
ans.put("type", "Grade");
JSONArray fieldValuePairs = new JSONArray();
JSONArray arrayField; // for array type field only
JSONObject curPair;
curPair = new JSONObject();
curPair.put("course",course.toJSONHelper(map));
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("student", this.student);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("grade", this.grade);
fieldValuePairs.add(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<Object, Integer>());
}
}

