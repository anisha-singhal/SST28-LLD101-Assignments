package com.moviebooking;

public interface PricingRule {
    double applyRule(double currentPrice, double basePrice, Show show, Seat seat);
}
