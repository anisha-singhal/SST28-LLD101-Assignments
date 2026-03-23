package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<SlotType, Double> rates = new HashMap<>();
        rates.put(SlotType.SMALL, 10.0);
        rates.put(SlotType.MEDIUM, 20.0);
        rates.put(SlotType.LARGE, 40.0);

        ParkingLot lot = new ParkingLot(new NearestSlotStrategy(), new HourlyPricing(rates));

        lot.addGate(new Gate("G1", 1, 0, 0));
        lot.addGate(new Gate("G2", 2, 0, 0));

        lot.addSlot(new Slot("F1-S1", SlotType.SMALL, 1, 10, 5));
        lot.addSlot(new Slot("F1-S2", SlotType.SMALL, 1, 20, 5));
        lot.addSlot(new Slot("F1-M1", SlotType.MEDIUM, 1, 10, 20));
        lot.addSlot(new Slot("F1-M2", SlotType.MEDIUM, 1, 30, 20));
        lot.addSlot(new Slot("F1-L1", SlotType.LARGE, 1, 10, 40));
        lot.addSlot(new Slot("F2-S1", SlotType.SMALL, 2, 10, 5));
        lot.addSlot(new Slot("F2-M1", SlotType.MEDIUM, 2, 10, 20));
        lot.addSlot(new Slot("F2-L1", SlotType.LARGE, 2, 10, 40));

        lot.status();

        long now = System.currentTimeMillis();

        Vehicle bike = new Vehicle("KA-01-1234", VehicleType.TWO_WHEELER);
        Ticket t1 = lot.park(bike, now, SlotType.SMALL, "G1");

        Vehicle car = new Vehicle("KA-02-5678", VehicleType.CAR);
        Ticket t2 = lot.park(car, now, SlotType.MEDIUM, "G1");

        Vehicle bus = new Vehicle("KA-03-9999", VehicleType.BUS);
        Ticket t3 = lot.park(bus, now, SlotType.LARGE, "G2");

        lot.status();

        long threeHoursLater = now + (3 * 60 * 60 * 1000);
        if (t1 != null) lot.exit(t1, threeHoursLater);
        if (t2 != null) lot.exit(t2, threeHoursLater);
        if (t3 != null) lot.exit(t3, threeHoursLater);

        lot.status();

        System.out.println("--- Bike parking in MEDIUM slot ---");
        Vehicle bike2 = new Vehicle("KA-04-1111", VehicleType.TWO_WHEELER);
        Ticket t4 = lot.park(bike2, now, SlotType.MEDIUM, "G1");
        if (t4 != null) {
            long oneHourLater = now + (60 * 60 * 1000);
            lot.exit(t4, oneHourLater);
        }
    }
}
