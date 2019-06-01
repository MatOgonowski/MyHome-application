package com.mateusz.myhome;

import android.bluetooth.BluetoothSocket;

import com.mateusz.myhome.model.Command;
import com.mateusz.myhome.model.Request;
import com.mateusz.myhome.model.RoomList;
import com.mateusz.myhome.model.Room;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;


/**
 * \class Communicator
 * klasa służąca do komunikacji aplikacji z miktokontrolerem
 * */
public class Communicator {
    /// socket wykorzystywany do komunikacji za pomocą bluetooth
    private BluetoothSocket bluetoothSocket;
    /// reader do odczytywania wiadomości
    private BufferedReader reader;
    /// writer do wysyłania wiadomości
    private PrintWriter writer;


    /**
     * konstruktor tworzący komunikator
     * */
    public Communicator() throws IOException, NullPointerException {
        if (GlobalSocket.bluetoothSocket == null) {
            throw new NullPointerException();
        }
        this.bluetoothSocket = GlobalSocket.bluetoothSocket;
        this.reader = new BufferedReader(new InputStreamReader(this.bluetoothSocket.getInputStream()));
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.bluetoothSocket.getOutputStream())));
    }

    /**
     * funkcja wysyłająca zapytanie o listę pomieszczeń
     * \return roomList lista pomieszczeń
     * */
    public RoomList getRoomList()
    {
        RoomList roomList = null;
        try {

            Request request = new Request(Request.ROOM_LIST_REQUEST_ID);
            this.writer.println(request.requestToJSON());
            this.writer.flush();
            String roomListJSON = this.reader.readLine();

            roomList = new RoomList(new JSONObject(roomListJSON));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return roomList;
    }

//    ------------------------------------------------------------------------------

    /**
     * funkcja wysyłająca zapytanie o wszystkie ustawienia danego pomieszczenia
     * \param roomName nazwa pomieszczenia
     * \return room obiekt typu room
     * */
    public Room getRoomState(String roomName) {
        Room room = null;
        try {

            Request request = new Request(Request.ROOM_STATE_REQUEST_ID, roomName);

            this.writer.println(request.requestToJSON());
            this.writer.flush();
            String roomStateJSON = this.reader.readLine();

            room = new Room(new JSONObject(roomStateJSON));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return room;
    }

    /**
     * funkcja wysyłająca zapytanie o stan symulacji
     * \return state wartośc stanu symulacji
     * */
    public Boolean getSimulationState() {
        Boolean state = false;
        try {
            Request request = new Request(Request.SIMULATION_STATE_REQUEST_ID);

            this.writer.println(request.requestToJSON());
            this.writer.flush();

            String simulationStateJSON = this.reader.readLine();
            JSONObject jsonObject = new JSONObject(simulationStateJSON);
            state = jsonObject.optBoolean("state");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * funkcja wysyłająca zapytania o listę dni dotyczących symulacji danego pomieszczenia
     * \param roomName nazwa pomieszczenia
     * \return hashMapDayList lista dni
     * */
    public HashMap<Integer,Day> getRoomDays(String roomName){
        HashMap<Integer, Day> hashMapDayList = null;
        DayList dayList;
        try {
            Request request = new Request(Request.ROOM_DAYS_REQUEST_ID, roomName);

            this.writer.println(request.requestToJSON());
            this.writer.flush();

            String daysJSON = this.reader.readLine();
            dayList = new DayList(new JSONObject(daysJSON));
            hashMapDayList = dayList.getDayList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return hashMapDayList;
    }

    /**
     * funkcja wysyłająca komendę zmiany stanu danego pomieszczenia
     * \param roomName nazwa pomieszczenia
     * \param state nowa wartość stanu pomieszczenia
     * */
    public void changeState(String roomName, Boolean state)  {
        try {
            Command command = new Command(Command.CHANGE_STATE, roomName, Command.STATE, state);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca komendę zmiany natężenia światła
     * \param roomName nazwa pomieszczenia
     * \param intensity nowa wartośc natężenia
     * */
    public void changeIntensity(String roomName, Integer intensity) {
        try {
            Command command = new Command(Command.CHANGE_INTENSITY, roomName, Command.INTENSITY, intensity);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca komendę zmiany stanu autmatycznego włączania oświetlenia
     * \param roomName nazwa pomieszczenia
     * \param autoOn nowa wartość stanu automatycznego włączania oświetlenia
     * */
    public void changeAutoOn(String roomName, Boolean autoOn) {
        try {
            Command command = new Command(Command.CHANGE_AUTO_ON, roomName, Command.AUTO_ON, autoOn);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca komendę zmiany stopnia zaciemnienia
     * \param roomName nazwa pomieszczenia
     * \param blackout nowa wartość stopnia zaciemnienia
     * */
    public void changeBlackout(String roomName, Integer blackout) {
        try {
            Command command = new Command(Command.CHANGE_BLACKOUT, roomName, Command.BLACKOUT, blackout);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca komendę zmiany nazwy danego pomieszczenia
     * \param roomName obecna nazwa pomieszczenia
     * \param newName nowa nazwa pomieszczenia
     * */
    public void changeName(String roomName, String newName) {
        try {
            Command command = new Command(Command.CHANGE_NAME, roomName ,Command.NEW_ROOM_NAME, newName);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająa polecenie wyłączenia oświetlenia we wszystkich pomieszczeniach
     * */
    public void offAll(){
        try {
            Command command = new Command(Command.OFF_ALL);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca polecenie zmiany wartośći stanu dnia w symulacji
     * \param roomName nazwa pomieszczenia
     * \param dayId numer id dnia
     * \param dayState nowa wartośc stanu dnia w symulacji
     * */
    public void changeDayState(String roomName, Integer dayId, Boolean dayState){
        try {
            Command command = new Command(Command.CHANGE_DAY_STATE, roomName, dayId, dayState );
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca polecenie zmiany godziny rozpoczęcia symulacji
     * \param roomName nazwa pomieszczenia
     * \param dayId numer id dnia
     * \param time nowa wartość godziny rozpoczęcia symulacji
     * */
    public void changeTimeFrom(String roomName, Integer dayId, Integer time){
        try {
            Command command = new Command(Command.CHANGE_TIME_FROM, roomName, dayId, time);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca polecenie zmiany godziny zakończenia symulacji
     * \param roomName nazwa pomieszczenia
     * \param dayId numer id dnia
     * \param time nowa wartość godziny zakończenia symulacji
     * */
    public void changeTimeTo(String roomName, Integer dayId, Integer time){
        try {
            Command command = new Command(Command.CHANGE_TIME_TO, roomName, dayId, time);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja wysyłająca polecenie zmiany stanu symulacji
     * \param simulationState nowa wartośc stanu symulacji
     * */
    public void changeSimulationState(Boolean simulationState){
        try {
            Command command = new Command(Command.CHANGE_SIMULATION_STATE,simulationState);
            this.writer.println(command.commandToJSON());
            this.writer.flush();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
