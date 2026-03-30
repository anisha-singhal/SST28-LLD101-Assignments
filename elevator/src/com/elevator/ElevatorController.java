package com.elevator;

import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;
    private DispatchStrategy strategy;

    public ElevatorController(List<Elevator> elevators, DispatchStrategy strategy) {
        this.elevators = elevators;
        this.strategy = strategy;
    }

    public void handleRequest(Request request) {
        if (request == null) return;

        Elevator selected = strategy.selectElevator(request, elevators);
        if (selected == null) {
            System.out.println("No elevator available for this request.");
            return;
        }

        System.out.println("Dispatching " + selected.getId() + " to floor " + request.getFloor());
        selected.addDestination(request.getFloor());
    }

    public void step() {
        for (Elevator elevator : elevators) {
            if (elevator.hasWork()) {
                elevator.move();
            }
        }
    }

    public void status() {
        System.out.println("\n--- Elevator Status ---");
        for (Elevator e : elevators) {
            System.out.println(e.getId() + " | Floor: " + e.getCurrentFloor()
                    + " | State: " + e.getState()
                    + " | Weight: " + e.getCurrentWeight() + "/" + e.getWeightLimit() + "kg");
        }
        System.out.println();
    }
}
