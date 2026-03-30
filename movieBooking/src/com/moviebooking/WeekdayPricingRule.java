package com.moviebooking;

import java.time.LocalDate;

public class WeekdayPricingRule implements PricingRule {
    private double weekendMultiplier;

    public WeekdayPricingRule(double weekendMultiplier) {
        this.weekendMultiplier = weekendMultiplier;
    }

    @Override
    public double applyRule(double currentPrice, double basePrice, Show show, Seat seat) {
        int day = LocalDate.now().getDayOfWeek().getValue();
        if (day == 6 || day == 7) {
            return Math.max(currentPrice * weekendMultiplier, basePrice);
        }
        return currentPrice;
    }
}
