import java.util.*;
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
public JSONObject toJSONHelper(HashMap<String, JSONObject> map) throws JSONException {
JSONObject ans = new JSONObject();
ans.put("id", 1);
ans.put("type", "Course");
JSONArray fieldValuePairs = new JSONArray();
JSONObject curPair;
curPair = new JSONObject();
curPair.put("instructor", instructor);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("numStudents", numStudents);
fieldValuePairs.add(curPair);
curPair = new JSONObject();
curPair.put("grades", grades);
fieldValuePairs.add(curPair);
ans.put("values", fieldValuePairs);
return ans;
}

public JSONObject toJSON() throws JSONException {
return toJSONHelper(new HashMap<String, JSONObject>());
}
}

