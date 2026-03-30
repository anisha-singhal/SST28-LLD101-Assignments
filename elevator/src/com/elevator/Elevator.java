package com.elevator;

import java.util.TreeSet;

public class Elevator {
    private String id;
    private int currentFloor;
    private ElevatorState state;
    private double weightLimit;
    private double currentWeight;
    private TreeSet<Integer> upQueue;
    private TreeSet<Integer> downQueue;

    public Elevator(String id, double weightLimit) {
        this.id = id;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.weightLimit = weightLimit;
        this.currentWeight = 0;
        this.upQueue = new TreeSet<>();
        this.downQueue = new TreeSet<>(java.util.Collections.reverseOrder());
    }

    public void addDestination(int floor) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("[" + id + "] Under maintenance. Cannot accept requests.");
            return;
        }

        if (floor > currentFloor) {
            upQueue.add(floor);
        } else if (floor < currentFloor) {
            downQueue.add(floor);
        }
    }

    public void move() {
        if (state == ElevatorState.MAINTENANCE) return;

        if (!checkWeight()) return;

        if (state == ElevatorState.UP || state == ElevatorState.IDLE) {
            if (!upQueue.isEmpty()) {
                state = ElevatorState.UP;
                int next = upQueue.first();
                currentFloor = next;
                upQueue.remove(next);
                System.out.println("[" + id + "] Arrived at floor " + currentFloor);
                openDoor();
                closeDoor();
            } else if (!downQueue.isEmpty()) {
                state = ElevatorState.DOWN;
                move();
                return;
            } else {
                state = ElevatorState.IDLE;
            }
        } else if (state == ElevatorState.DOWN) {
            if (!downQueue.isEmpty()) {
                int next = downQueue.first();
                currentFloor = next;
                downQueue.remove(next);
                System.out.println("[" + id + "] Arrived at floor " + currentFloor);
                openDoor();
                closeDoor();
            } else if (!upQueue.isEmpty()) {
                state = ElevatorState.UP;
                move();
                return;
            } else {
                state = ElevatorState.IDLE;
            }
        }
    }

    public void openDoor() {
        System.out.println("[" + id + "] Door opened at floor " + currentFloor);
    }

    public void closeDoor() {
        if (!checkWeight()) return;
        System.out.println("[" + id + "] Door closed at floor " + currentFloor);
    }

    public void pressOpenDoor() {
        System.out.println("[" + id + "] Open door button pressed.");
        openDoor();
    }

    public void pressCloseDoor() {
        System.out.println("[" + id + "] Close door button pressed.");
        closeDoor();
    }

    public void pressAlarm() {
        state = ElevatorState.IDLE;
        upQueue.clear();
        downQueue.clear();
        System.out.println("[" + id + "] ALARM! Elevator stopped. Doors opening.");
        openDoor();
    }

    public void setWeight(double weight) {
        this.currentWeight = weight;
        checkWeight();
    }

    private boolean checkWeight() {
        if (currentWeight > weightLimit) {
            System.out.println("[" + id + "] OVERWEIGHT! Limit: " + weightLimit + "kg, Current: " + currentWeight + "kg");
            System.out.println("[" + id + "] ALARM! Elevator stopped. Please reduce weight.");
            openDoor();
            return false;
        }
        return true;
    }

    public void setMaintenance(boolean maintenance) {
        if (maintenance) {
            state = ElevatorState.MAINTENANCE;
            upQueue.clear();
            downQueue.clear();
            System.out.println("[" + id + "] Entering maintenance mode.");
        } else {
            state = ElevatorState.IDLE;
            System.out.println("[" + id + "] Back in service.");
        }
    }

    public boolean hasWork() {
        return !upQueue.isEmpty() || !downQueue.isEmpty();
    }

    public String getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public ElevatorState getState() { return state; }
    public double getWeightLimit() { return weightLimit; }
    public double getCurrentWeight() { return currentWeight; }
}
