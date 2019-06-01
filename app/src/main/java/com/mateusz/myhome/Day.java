package com.mateusz.myhome;

/**
 * \class Day
 * klasa zawierająca wszystkie potrzebne atrybuty dnia do przeprowadzenia symulacji
 * */
public class Day {

    /// wartość stanu danego dnia
    private Boolean state;
    /// numer id
    private Integer dayId;
    /// godzina rozpoczęcia typu Integer
    private Integer timeFrom;
    /// godzina zakończenia typu Integet
    private Integer timeTo;
    /// godzina rozpoczęcia typu String
    private String sTimeFrom;
    /// godzina zakończenia typu String
    private String sTimeTo;

    /**
     * \param state stan dnia
     * \param dayId numer id
     * \param timeFrom godzina rozpoczęcia
     * \param timeto godzina zakońcczenia
     * */
    public Day(Boolean state, Integer dayId, Integer timeFrom, Integer timeTo){
        this.state = state;
        this.dayId = dayId;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.sTimeFrom = timeToString(timeFrom);
        this.sTimeTo = timeToString(timeTo);
    }

    public Boolean getState() {
        return state;
    }

    public Integer getDayId() {
        return dayId;
    }

    public String getStringTimeFrom() {
        return sTimeFrom;
    }

    public String getStringTimeTo() {
        return sTimeTo;
    }

    /**
     * funkcja zamieniająca godzinę typu Integer na typ String
     * \param time godzina
     * \return sTime godzina zapisana jako obiekt typu String
     * */
    public String timeToString(Integer time) {
        String sTime, sHour, sMin;
        sHour = Integer.toString(time/100);
        sMin = Integer.toString(time%100);
        if(time%100 < 10)
            sMin = "0" + sMin;
        sTime = sHour + ":" + sMin;

        return sTime;
    }

}
