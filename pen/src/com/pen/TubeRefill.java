package com.pen;

public class TubeRefill implements RefillBehavior {
    @Override
    public void refill() {
        System.out.println("Replacing the ink tube...");
    }
}
