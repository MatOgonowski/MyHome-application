package com.mateusz.myhome.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * \class RoomList
 * klasa tworząca listę pomieszczeń
 * */
public class RoomList {

    private static final String LIST_FIELD = "roomList";
    /// lista nazw pomieszczeń typu
    public static String[] roomList;
    private Integer roomNr;

/**
 * konstruktor tworzący listę pomieszczeń na podstawie obiektu typu JSON
 * \param jsonObject oiekt typu JSON otrzymany z odpowiedzi z Arduino
 * */
    public RoomList(JSONObject jsonObject) throws JSONException {
        roomNr = 0;
        JSONArray list = jsonObject.getJSONArray(LIST_FIELD);
        this.roomList = new String[list.length()];
        for(int i = 0; i < list.length(); ++i) {
            JSONObject jsObject = list.getJSONObject(i);
            String roomName = jsObject.optString("name");
            this.roomList[i] = roomName;
            this.roomNr++;
        }

    }


    public Integer getRoomNr() {
        return this.roomNr;
    }

    public String[] gRoomList() {
        return this.roomList;
    }

    public String gStringRoom(int i) { return this.roomList[i]; }

}


