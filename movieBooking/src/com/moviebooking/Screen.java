package com.moviebooking;

import java.util.List;

public class Screen {
    private String id;
    private String name;
    private List<Seat> seats;

    public Screen(String id, String name, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Seat> getSeats() { return seats; }
}
