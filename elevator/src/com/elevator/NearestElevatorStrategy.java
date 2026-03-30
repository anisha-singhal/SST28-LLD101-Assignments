package com.elevator;

import java.util.List;

public class NearestElevatorStrategy implements DispatchStrategy {

    @Override
    public Elevator selectElevator(Request request, List<Elevator> elevators) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) continue;

            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());

            if (elevator.getState() == ElevatorState.IDLE) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            } else if (elevator.getState() == ElevatorState.UP && request.getDirection() == Direction.UP) {
                if (elevator.getCurrentFloor() <= request.getFloor() && distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            } else if (elevator.getState() == ElevatorState.DOWN && request.getDirection() == Direction.DOWN) {
                if (elevator.getCurrentFloor() >= request.getFloor() && distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            }
        }

        if (best == null) {
            for (Elevator elevator : elevators) {
                if (elevator.getState() == ElevatorState.MAINTENANCE) continue;
                int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
                if (distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            }
        }

        return best;
    }
}
