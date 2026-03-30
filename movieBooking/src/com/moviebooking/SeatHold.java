package com.moviebooking;

import java.util.List;

public class SeatHold {
    private static int counter = 0;

    private String holdId;
    private Show show;
    private List<String> seatNumbers;
    private String userId;
    private long holdTime;
    private long expiryMs;
    private boolean confirmed;

    public SeatHold(Show show, List<String> seatNumbers, String userId) {
        this.holdId = "HOLD-" + (++counter);
        this.show = show;
        this.seatNumbers = seatNumbers;
        this.userId = userId;
        this.holdTime = System.currentTimeMillis();
        this.expiryMs = 5 * 60 * 1000;
        this.confirmed = false;
    }

    public boolean isExpired() {
        return !confirmed && (System.currentTimeMillis() - holdTime > expiryMs);
    }

    public void confirm() {
        this.confirmed = true;
    }

    public String getHoldId() { return holdId; }
    public Show getShow() { return show; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public String getUserId() { return userId; }
    public boolean isConfirmed() { return confirmed; }
}
