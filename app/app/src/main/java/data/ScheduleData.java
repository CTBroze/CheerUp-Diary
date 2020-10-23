package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScheduleData {
    public String date;
    public String title;
    public String description;
    public String time;
    public int scheduleType;

    public ScheduleData(String title, String desc, String date, int type, String time){
        this.title = title;
        this.description = desc;
        this.date = date;
        this.scheduleType = type;
        this.time = time;
    }


}
