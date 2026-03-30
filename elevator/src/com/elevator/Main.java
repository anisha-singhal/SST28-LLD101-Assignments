package com.elevator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(new Elevator("E1", 700));
        elevators.add(new Elevator("E2", 500));

        Building building = new Building(10, elevators, new NearestElevatorStrategy());

        System.out.println("=== Elevator System Demo ===\n");

        building.status();

        System.out.println("--- Outside button pressed ---");
        building.pressUp(3);
        building.step();

        System.out.println("\n--- Inside button pressed ---");
        building.pressFloorButton("E1", 7);
        building.step();

        building.status();

        System.out.println("--- Overweight scenario ---");
        building.setElevatorWeight("E1", 800);
        building.pressFloorButton("E1", 9);
        building.step();

        System.out.println("\n--- Weight reduced ---");
        building.setElevatorWeight("E1", 400);
        building.step();

        System.out.println("\n--- Alarm pressed ---");
        building.pressFloorButton("E2", 5);
        building.step();
        building.pressAlarm("E2");

        System.out.println("\n--- Floor maintenance ---");
        building.setFloorMaintenance(4, true);
        building.pressUp(4);

        System.out.println("\n--- Elevator maintenance ---");
        building.setElevatorMaintenance("E2", true);
        building.pressUp(6);
        building.step();

        building.status();
    }
}
