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
}
