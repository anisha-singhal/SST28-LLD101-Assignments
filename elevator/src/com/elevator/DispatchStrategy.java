package com.elevator;

import java.util.List;

public interface DispatchStrategy {
    Elevator selectElevator(Request request, List<Elevator> elevators);
}
