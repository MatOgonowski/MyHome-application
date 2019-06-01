package com.mateusz.myhome.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * \class Request
 * klasa zawierająca wszystkie zapytania wysyłane do Arduino
 * */

public class Request {

    /// stałe określające unikalne numery id zapytań
    public static final Integer ROOM_LIST_REQUEST_ID = 0;
    public static final Integer ROOM_STATE_REQUEST_ID = 1;
    public static final Integer SIMULATION_STATE_REQUEST_ID = 8;
    public static final Integer ROOM_DAYS_REQUEST_ID = 12;
//    public static final Boolean REQUEST_LIST = false;
//    public static final Boolean REQUEST_ROOM_STATE = true;
//    public static final Boolean REQUEST_SIMULATION_STATE = true;


    /// id zapytania
    private Integer requestID;
    /// dodatkowa nazwa wykorzystywana w niektórych zapytaniach
    private String requestName;

    /**
     * konstruktor zapytania
     * \param requestId numer id zapytania
     * \param requestName dodatkowa nazwa wykorzystywana przy tworzeniu zapytania
     * */
    public Request(Integer requestID, String requestName) {
        this.requestID = requestID;
        this.requestName = requestName;
    }

    /**
     * konstruktor zapytania
     * \param requestId numer id zapytania
     * */
    public Request(Integer requestID){
        this.requestID = requestID;
    }



    /**
     * funkcja zamieniająca zapytanie na obiekt typu JSON
     * */
    public JSONObject requestToJSON() throws JSONException {
        JSONObject object = new JSONObject();
        if(requestID.equals(ROOM_STATE_REQUEST_ID)||requestID.equals(ROOM_DAYS_REQUEST_ID)) {
            object.put("id", requestID);
            object.put("name", requestName);
            return object;
        }
        if(requestID.equals(ROOM_LIST_REQUEST_ID)||requestID.equals(SIMULATION_STATE_REQUEST_ID)){
            object.put("id",requestID);
            return object;
        }
        return object;
    }

}
