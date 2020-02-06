import java.util.*;
import java.lang.*;
import org.json.*;
public class Course {
private Faculty instructor;
private int numStudents;
private Grade grades;
public Faculty getInstructor() {
return instructor;
}
public void setInstructor(Faculty x) {
this.instructor = x;
}
public int getNumStudents() {
return numStudents;
}
public void setNumStudents(int x) {
this.numStudents = x;
}
public Grade getGrades() {
return grades;
}
public void setGrades(Grade x) {
this.grades = x;
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
ans.put("type", "Course");
JSONArray fieldValuePairs = new JSONArray();
JSONArray arrayField; // for array type field only
JSONObject curPair;
curPair = new JSONObject();
curPair.put("instructor",instructor.toJSONHelper(map));
fieldValuePairs.put(curPair);
curPair = new JSONObject();
curPair.put("numStudents", this.numStudents);
fieldValuePairs.put(curPair);
curPair = new JSONObject();
curPair.put("grades",grades.toJSONHelper(map));
fieldValuePairs.put(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<Object, Integer>());
}
}

