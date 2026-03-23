package com.parkinglot;

import java.util.Map;

public class HourlyPricing implements PricingStrategy {
    private Map<SlotType, Double> rates;

    public HourlyPricing(Map<SlotType, Double> rates) {
        this.rates = rates;
    }

    @Override
    public double calculateFee(Ticket ticket, long exitTime) {
        long durationMs = exitTime - ticket.getEntryTime();
        double hours = Math.ceil(durationMs / (1000.0 * 60 * 60));
        if (hours < 1) hours = 1;

        double rate = rates.getOrDefault(ticket.getSlot().getType(), 10.0);
        return hours * rate;
    }
}
