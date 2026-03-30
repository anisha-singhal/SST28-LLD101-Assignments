package com.moviebooking;

public class Movie {
    private String id;
    private String title;
    private int durationMinutes;

    public Movie(String id, String title, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
}
