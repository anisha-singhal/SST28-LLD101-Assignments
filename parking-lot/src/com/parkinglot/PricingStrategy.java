package com.parkinglot;

public interface PricingStrategy {
    double calculateFee(Ticket ticket, long exitTime);
}
