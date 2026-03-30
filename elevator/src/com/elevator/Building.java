package com.elevator;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;
    private List<Elevator> elevators;
    private ElevatorController controller;

    public Building(int numFloors, List<Elevator> elevators, DispatchStrategy strategy) {
        this.floors = new ArrayList<>();
        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor(i));
        }
        this.elevators = elevators;
        this.controller = new ElevatorController(elevators, strategy);
    }

    public void pressUp(int floorNumber) {
        Floor floor = floors.get(floorNumber);
        Request request = floor.pressUp();
        controller.handleRequest(request);
    }

    public void pressDown(int floorNumber) {
        Floor floor = floors.get(floorNumber);
        Request request = floor.pressDown();
        controller.handleRequest(request);
    }

    public void pressFloorButton(String elevatorId, int targetFloor) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                System.out.println("[" + elevatorId + "] Floor " + targetFloor + " button pressed inside.");
                e.addDestination(targetFloor);
                return;
            }
        }
    }

    public void pressOpenDoor(String elevatorId) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.pressOpenDoor();
                return;
            }
        }
    }

    public void pressCloseDoor(String elevatorId) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.pressCloseDoor();
                return;
            }
        }
    }

    public void pressAlarm(String elevatorId) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.pressAlarm();
                return;
            }
        }
    }

    public void setElevatorWeight(String elevatorId, double weight) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.setWeight(weight);
                return;
            }
        }
    }

    public void setFloorMaintenance(int floorNumber, boolean maintenance) {
        floors.get(floorNumber).setMaintenance(maintenance);
    }

    public void setElevatorMaintenance(String elevatorId, boolean maintenance) {
        for (Elevator e : elevators) {
            if (e.getId().equals(elevatorId)) {
                e.setMaintenance(maintenance);
                return;
            }
        }
    }

    public void step() { controller.step(); }
    public void status() { controller.status(); }
}
