package com.parkinglot;

public class Slot {
    private String id;
    private SlotType type;
    private int floor;
    private double x;
    private double y;
    private boolean occupied;

    public Slot(String id, SlotType type, int floor, double x, double y) {
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    public double distanceTo(Gate gate) {
        double dx = this.x - gate.getX();
        double dy = this.y - gate.getY();
        double dz = (this.floor - gate.getFloor()) * 10.0;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public String getId() { return id; }
    public SlotType getType() { return type; }
    public int getFloor() { return floor; }
    public boolean isOccupied() { return occupied; }
    public void occupy() { this.occupied = true; }
    public void free() { this.occupied = false; }
}
