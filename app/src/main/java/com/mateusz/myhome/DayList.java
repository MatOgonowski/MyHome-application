package com.mateusz.myhome;

import com.mateusz.myhome.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * \class DayList
 * klasa tworząca listę dni
 * */
public class DayList {


    /// lista dni w postaci HashMap
    private HashMap<Integer, Day> dayList;
    private Day day;

    /**
    * \param jsonObject obiekt typu JSON otrzymany z Arduino
    * */
    public DayList(JSONObject jsonObject) throws JSONException {
            JSONArray list = jsonObject.getJSONArray(Room.ROOM_FIELD);
            dayList = new HashMap<>();
//            Day day;
            for(int i = 0; i < list.length(); i++) {
                JSONObject jsObject = list.getJSONObject(i);
                Boolean state = jsObject.optBoolean("state");
                Integer dayId = jsObject.optInt("dayId");
                Integer timeTo = jsObject.optInt("timeTo");
                Integer timeFrom = jsObject.optInt("timeFrom");
                day = new Day(state, dayId, timeFrom, timeTo);
                dayList.put(dayId, day);
            }
    }

    public HashMap<Integer, Day> getDayList(){
        return this.dayList;
    }
}
