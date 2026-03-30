package com.moviebooking;

import java.util.HashMap;
import java.util.Map;

public class Show {
    private String id;
    private Movie movie;
    private Screen screen;
    private String startTime;
    private Map<String, Boolean> seatAvailability;

    public Show(String id, Movie movie, Screen screen, String startTime) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.seatAvailability = new HashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatAvailability.put(seat.getSeatNumber(), true);
        }
    }

    public boolean isSeatAvailable(String seatNumber) {
        return seatAvailability.getOrDefault(seatNumber, false);
    }

    public void bookSeat(String seatNumber) {
        seatAvailability.put(seatNumber, false);
    }

    public void freeSeat(String seatNumber) {
        seatAvailability.put(seatNumber, true);
    }

    public Seat getSeatByNumber(String seatNumber) {
        for (Seat seat : screen.getSeats()) {
            if (seat.getSeatNumber().equals(seatNumber)) return seat;
        }
        return null;
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public String getStartTime() { return startTime; }
    public Map<String, Boolean> getSeatAvailability() { return seatAvailability; }
}
