package com.moviebooking;

public class Seat {
    private String seatNumber;
    private SeatType type;
    private double price;

    public Seat(String seatNumber, SeatType type, double price) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.price = price;
    }

    public String getSeatNumber() { return seatNumber; }
    public SeatType getType() { return type; }
    public double getPrice() { return price; }
}
