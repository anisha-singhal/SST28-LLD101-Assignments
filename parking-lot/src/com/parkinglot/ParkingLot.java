package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private List<Slot> slots;
    private Map<String, Gate> gates;
    private SlotAssignmentStrategy assignmentStrategy;
    private PricingStrategy pricingStrategy;

    public ParkingLot(SlotAssignmentStrategy assignmentStrategy, PricingStrategy pricingStrategy) {
        this.slots = new ArrayList<>();
        this.gates = new HashMap<>();
        this.assignmentStrategy = assignmentStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addSlot(Slot slot) { slots.add(slot); }

    public void addGate(Gate gate) { gates.put(gate.getId(), gate); }

    public Ticket park(Vehicle vehicle, long entryTime, SlotType requestedType, String entryGateId) {
        Gate gate = gates.get(entryGateId);
        if (gate == null) {
            System.out.println("Gate " + entryGateId + " not found");
            return null;
        }

        Slot assigned = assignmentStrategy.findSlot(gate, slots, requestedType);
        if (assigned == null) {
            System.out.println("No available slot for type " + requestedType);
            return null;
        }

        assigned.occupy();
        Ticket ticket = new Ticket(vehicle, assigned, entryTime);
        System.out.println(vehicle.getLicensePlate() + " parked at slot " + assigned.getId()
                + " (floor " + assigned.getFloor() + ", " + assigned.getType() + ")");
        return ticket;
    }

    public double exit(Ticket ticket, long exitTime) {
        ticket.getSlot().free();
        double fee = pricingStrategy.calculateFee(ticket, exitTime);
        System.out.println(ticket.getVehicle().getLicensePlate() + " exited from slot "
                + ticket.getSlot().getId() + " | Fee: Rs " + fee);
        return fee;
    }

    public void status() {
        Map<SlotType, int[]> counts = new HashMap<>();
        for (SlotType type : SlotType.values()) {
            counts.put(type, new int[]{0, 0});
        }

        for (Slot slot : slots) {
            int[] arr = counts.get(slot.getType());
            arr[0]++;
            if (!slot.isOccupied()) arr[1]++;
        }

        System.out.println("\n--- Parking Lot Status ---");
        for (SlotType type : SlotType.values()) {
            int[] arr = counts.get(type);
            System.out.println(type + ": " + arr[1] + " available / " + arr[0] + " total");
        }
        System.out.println("--------------------------\n");
    }
}
