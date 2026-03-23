package com.pen;

public class BottleRefill implements RefillBehavior {
    @Override
    public void refill() {
        System.out.println("Drawing ink from bottle using converter...");
    }
}
