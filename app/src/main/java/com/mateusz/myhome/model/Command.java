package com.mateusz.myhome.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * \class Command
 * klasa zawierająca wszystkie komendy wysyłane przez aplikację do Arduino
 */

public class Command {

    /// stałe określającę ID konkretnych komend
    public static final Integer CHANGE_STATE = 2;
    public static final Integer CHANGE_NAME = 3;
    public static final Integer CHANGE_INTENSITY = 4;
    public static final Integer CHANGE_AUTO_ON = 5;
    public static final Integer CHANGE_BLACKOUT = 6;
    public static final Integer OFF_ALL = 7;
    public static final Integer CHANGE_DAY_STATE = 9;
    public static final Integer CHANGE_TIME_FROM = 10;
    public static final Integer CHANGE_TIME_TO = 11;
    public static final Integer CHANGE_SIMULATION_STATE = 13;

    /// stałe zawierające nazwy atrybutów, które mają być zmienione
    public static final String STATE = "state";
    public static final String NEW_ROOM_NAME = "newRoomName";
    public static final String INTENSITY = "intensity";
    public static final String AUTO_ON = "autoOn";
    public static final String BLACKOUT = "blackout";
    public static final String DAY_ID = "dayId";
//    public static final String TIME_TO = "timeTo";
//    public static final String TIME_FROM = "timeFrom";
//    public static final String DAY_STATE = "dayState";

    /// unikalny numer id komendy
    private Integer commandId;
    /// nazwa pomieszczenia
    private String roomName;
    /// nazwa atrybutu, który będzie zmieniony
    private String attributeName;
    /// zmienna typu Boolean
    private Boolean boolValue;
    /// zmienna typu String
    private String stringValue;
    /// zmienna typu Integer
    private Integer intValue;
    /// unikalny numer id dnia tygodnia
    private Integer dayId;
//    private Integer timeFrom;
//    private Integer timeTo;
    /// stan włączenie symulacji dla konkretnego dnia tygodnia
    private Boolean dayState;
    /// zmienna określająca godzinę którą chcemy wprowadzić
    private Integer time;

/**
*  konstruktor komendy wykorzystującej tylko id komendy
 * \param commandId id komendy
 */
    public Command(Integer commandId){
        this.commandId = commandId;
    }

    /**
     * konstruktor komendy zmieniającej atrybuty typu Boolean
     * \param commandId id komendy
     * \param roomName nazwa pomieszczenia w którym chcemy dokonąć zmiany
     * \param attrributeName nazwa atrybutu który chemy zmienić
     * \param boolValue nowa wartość atrybutu
     */
    public Command(Integer commandId, String roomName, String attributeName, Boolean boolValue){
        this.commandId = commandId;
        this.roomName = roomName;
        this.attributeName = attributeName;
        this.boolValue = boolValue;
    }

    /**
     * konstruktor komendy zmieniającej atrybuty typu String
     * \param commandId id komendy
     * \param roomName nazwa pomieszczenia w którym chcemy dokonąć zmiany
     * \param attrributeName nazwa atrybutu który chemy zmienić
     * \param stringValue nowa wartość atrybutu
     */
    public Command(Integer commandId, String roomName, String attributeName, String stringValue){
        this.commandId = commandId;
        this.roomName = roomName;
        this.attributeName = attributeName;
        this.stringValue = stringValue;
    }

    /**
     * konstruktor komendy zmieniającej atrybuty typu Integer
     * \param commandId id komendy
     * \param roomName nazwa pomieszczenia w którym chcemy dokonąć zmiany
     * \param attrributeName nazwa atrybutu który chemy zmienić
     * \param intValue nowa wartość atrybutu
     */
    public Command(Integer commandId, String roomName, String attributeName, Integer intValue){
        this.commandId = commandId;
        this.roomName = roomName;
        this.attributeName = attributeName;
        this.intValue = intValue;
    }

    /**
     * konstruktor komendy zmieniającej stan konkretnego dnia w symulacji
     * \param commandId id komendy
     * \param roomName nazwa pomieszczenia w którym chcemy dokonąć zmiany
     * \param dayId id dnia
     * \param dayState nowa wartość stanu dnia
     */
    public Command(Integer commandId, String roomName, Integer dayId,  Boolean dayState){
        this.commandId = commandId;
        this.roomName = roomName;
        this.dayId = dayId;
        this.dayState = dayState;
    }

    /**
     * konstruktor komendy zmieniającej godzinę rozpoczęcia lub zakończenie symulacji
     * \param commandId id komendy
     * \param roomName nazwa pomieszczenia w którym chcemy dokonąć zmiany
     * \param dayId id dnia
     * \param time nowa wartość godziny
     */
    public Command(Integer commandId, String roomName, Integer dayId, Integer time){
        this.commandId = commandId;
        this.roomName = roomName;
        this.dayId = dayId;
        this.time = time;
    }

    /**
     * konstruktor komendy zmieniającej wartość typu Boolean
     * \param commandId id komendy
     * \param boolValue no wartość zmiennej typu Boolean
     */
    public Command(Integer commandId, Boolean boolValue){
        this.commandId = commandId;
        this.boolValue = boolValue;
    }

    /**
     * funkcja zamieniająca komendy na obiekt typu JSON
     * */
    public JSONObject commandToJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id",this.commandId);
        if(!commandId.equals(OFF_ALL)&&!commandId.equals(CHANGE_SIMULATION_STATE)) {
            object.put("name", this.roomName);
        }

        if(commandId.equals(CHANGE_STATE)){
            object.put(attributeName,this.boolValue);
            return object;
        }

        if(commandId.equals(CHANGE_NAME)){
            object.put(attributeName, this.stringValue);
            return object;
        }

        if(commandId.equals(CHANGE_INTENSITY)){
            object.put(attributeName,this.intValue);
            return object;
        }

        if(commandId.equals(CHANGE_AUTO_ON)){
            object.put(attributeName,this.boolValue);
            return object;
        }

        if(commandId.equals(CHANGE_BLACKOUT)){
            object.put(attributeName,this.intValue);
            return object;
        }

        if(commandId.equals(CHANGE_DAY_STATE)){
            object.put(DAY_ID,this.dayId);
            object.put("state",this.dayState);
            return object;
        }

        if(commandId.equals(CHANGE_TIME_FROM)){
            object.put(DAY_ID,this.dayId);
            object.put("timeFrom",this.time);
            return object;
        }

        if(commandId.equals(CHANGE_TIME_TO)){
            object.put(DAY_ID,this.dayId);
            object.put("timeTo",this.time);
            return object;
        }

        if(commandId.equals(CHANGE_SIMULATION_STATE)){
            object.put("state",this.boolValue);
            return object;
        }

        return object;
    }
}
