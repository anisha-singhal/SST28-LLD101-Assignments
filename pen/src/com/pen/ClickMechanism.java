package com.pen;

public class ClickMechanism implements OpenCloseBehavior {
    @Override
    public void open() {
        System.out.println("Clicking to extend the tip...");
    }

    @Override
    public void close() {
        System.out.println("Clicking to retract the tip...");
    }
}
