package com.example.sebastian.statki;

/**
 * Created by Sebastian on 2016-05-18.
 */
public class RowOneGame {
    private String title;
    private Boolean occupied;

    public RowOneGame(String title, Boolean occupied) {
        this.title = title;
        this.occupied = occupied;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getOccupied() {
        return occupied;
    }
}