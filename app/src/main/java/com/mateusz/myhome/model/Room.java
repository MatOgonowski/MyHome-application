package com.mateusz.myhome.model;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * \class Room
 * klasa zawierająca wszystkie zmienne pommieszczenia
 * */
public class Room {
    /// stan włączenia oświetlenia
    private Boolean state;
    /// nazwa pomieszczenia
    private String roomName;
    /// natężenie oświetlenia
    private Integer intensity;
    /// stan włączenia automatycznego włączania oświetlenia przy odpowiednim zaciemnieniu pomieszczenia
    private Boolean autoOn;
    /// stopień zaciemnienia wykorzystywany przy automatycznym włączaniu oświetlenia
    private Integer blackout;
    public static final String ROOM_FIELD = "room";

/**
 * tworzenie pomieszczenia na podstawie otrzymanego obiektu typu JSON
 * \param jsonObject obiekt typu JSON otrzymany z odpowiedzi Arduino
 * */
    public Room(JSONObject jsonObject) throws JSONException {

        JSONObject jsObject = jsonObject.getJSONObject(ROOM_FIELD);
        this.state = jsObject.optBoolean("state");
        this.roomName = jsObject.optString("roomName");
        this.intensity = jsObject.optInt("intensity");
        this.autoOn = jsObject.optBoolean("autoOn");
        this.blackout = jsObject.optInt("blackout");

    }

    public Boolean getState() {
        return this.state;
    }

    public String getRoomName() { return  this.roomName; }

    public Integer getIntensity() { return this.intensity; }

    public Boolean getAutoOn() { return this.autoOn; }

    public Integer getBlackout() { return  this.blackout; }

}
