package com.pen;

public class Pen {
    private String color;
    private boolean isOpen;
    private WriteBehavior writeBehavior;
    private RefillBehavior refillBehavior;
    private OpenCloseBehavior openCloseBehavior;

    public Pen(String color, WriteBehavior writeBehavior, RefillBehavior refillBehavior, OpenCloseBehavior openCloseBehavior) {
        this.color = color;
        this.writeBehavior = writeBehavior;
        this.refillBehavior = refillBehavior;
        this.openCloseBehavior = openCloseBehavior;
        this.isOpen = false;
    }

    public void start() {
        openCloseBehavior.open();
        this.isOpen = true;
    }

    public void write() {
        if (!isOpen) {
            System.out.println("Pen is closed! Call start() first.");
            return;
        }
        System.out.print("[" + color + "] ");
        writeBehavior.write();
    }

    public void close() {
        openCloseBehavior.close();
        this.isOpen = false;
    }

    public void refill(String newColor) {
        refillBehavior.refill();
        this.color = newColor;
        System.out.println("Color changed to " + newColor);
    }

    public String getColor() { return color; }
}
