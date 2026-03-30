package com.moviebooking;

public class Payment {
    private String paymentId;
    private String ticketId;
    private double amount;
    private String type;

    public Payment(String paymentId, String ticketId, double amount, String type) {
        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.type = type;
    }

    public String getPaymentId() { return paymentId; }
    public String getTicketId() { return ticketId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
}
