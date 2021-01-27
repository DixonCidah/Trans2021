package com.mespana.trans2021.models;

import java.util.Date;

public class Event {

    private Integer nbOfEvent;
    private String salle;
    private Date date;

    public Event(Integer nbOfEvent, String salle, Date date) {
        this.nbOfEvent = nbOfEvent;
        this.salle = salle;
        this.date = date;
    }

    public Integer getNbOfEvent() {
        return nbOfEvent;
    }

    public void setNbOfEvent(Integer nbOfEvent) {
        this.nbOfEvent = nbOfEvent;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
