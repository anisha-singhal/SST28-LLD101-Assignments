package com.parkinglot;

public class Ticket {
    private static int counter = 0;

    private String ticketId;
    private Vehicle vehicle;
    private Slot slot;
    private long entryTime;

    public Ticket(Vehicle vehicle, Slot slot, long entryTime) {
        this.ticketId = "TKT-" + (++counter);
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public Slot getSlot() { return slot; }
    public long getEntryTime() { return entryTime; }
}
