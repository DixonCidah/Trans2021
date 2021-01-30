package com.mespana.trans2021.models;

import java.util.function.Predicate;

public class ArtistFilter {

    private String name;
    private Predicate<Artist> predicate;

    public ArtistFilter(String name, Predicate<Artist> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Predicate<Artist> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<Artist> predicate) {
        this.predicate = predicate;
    }
}
