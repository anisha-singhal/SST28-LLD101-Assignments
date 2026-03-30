package com.moviebooking;

public class DemandPricingRule implements PricingRule {
    private double threshold;
    private double multiplier;

    public DemandPricingRule(double threshold, double multiplier) {
        this.threshold = threshold;
        this.multiplier = multiplier;
    }

    @Override
    public double applyRule(double currentPrice, double basePrice, Show show, Seat seat) {
        int totalSeats = show.getSeatAvailability().size();
        int bookedSeats = 0;
        for (Boolean available : show.getSeatAvailability().values()) {
            if (!available) bookedSeats++;
        }
        double occupancy = (double) bookedSeats / totalSeats;

        if (occupancy >= threshold) {
            return Math.max(currentPrice * multiplier, basePrice);
        }
        return currentPrice;
    }
}
