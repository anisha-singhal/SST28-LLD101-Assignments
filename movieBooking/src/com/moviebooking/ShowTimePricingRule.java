package com.moviebooking;

public class ShowTimePricingRule implements PricingRule {
    private String primeTimeStart;
    private String primeTimeEnd;
    private double multiplier;

    public ShowTimePricingRule(String primeTimeStart, String primeTimeEnd, double multiplier) {
        this.primeTimeStart = primeTimeStart;
        this.primeTimeEnd = primeTimeEnd;
        this.multiplier = multiplier;
    }

    @Override
    public double applyRule(double currentPrice, double basePrice, Show show, Seat seat) {
        String showTime = show.getStartTime();
        if (showTime.compareTo(primeTimeStart) >= 0 && showTime.compareTo(primeTimeEnd) <= 0) {
            return Math.max(currentPrice * multiplier, basePrice);
        }
        return currentPrice;
    }
}
