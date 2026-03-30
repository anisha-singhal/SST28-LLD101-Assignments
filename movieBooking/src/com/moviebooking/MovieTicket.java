package com.moviebooking;

import java.util.List;

public class MovieTicket {
    private static int counter = 0;

    private String ticketId;
    private Show show;
    private List<String> bookedSeats;
    private double totalAmount;
    private BookingStatus status;
    private String userId;
    private PaymentMode paymentMode;

    public MovieTicket(Show show, List<String> bookedSeats, double totalAmount, String userId, PaymentMode paymentMode) {
        this.ticketId = "TICKET-" + (++counter);
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.CONFIRMED;
        this.userId = userId;
        this.paymentMode = paymentMode;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<String> getBookedSeats() { return bookedSeats; }
    public double getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }
    public String getUserId() { return userId; }
    public PaymentMode getPaymentMode() { return paymentMode; }
}
