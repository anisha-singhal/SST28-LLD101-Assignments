# Multilevel Parking Lot

A multilevel parking lot system that supports different vehicle types, slot-based pricing, and nearest slot assignment.

## How to Run

```bash
cd parking-lot/src
javac com/parkinglot/*.java
java com.parkinglot.Main
```

## Features

- Multiple floors with entry gates at different locations
- Three slot types: SMALL (2-wheelers), MEDIUM (cars), LARGE (buses)
- Smaller vehicles can park in larger slots when needed
- Billing based on slot type, not vehicle type
- Nearest available slot assignment using Euclidean distance
- Hourly pricing with configurable rates per slot type

## APIs

| Method | Description |
|--------|-------------|
| `park(vehicle, entryTime, slotType, gateId)` | Parks a vehicle and returns a ticket |
| `exit(ticket, exitTime)` | Frees the slot and returns the fee |
| `status()` | Shows available vs total slots by type |

## Design

### Classes

| Class | Responsibility |
|-------|---------------|
| `ParkingLot` | Main orchestrator, exposes park/exit/status APIs |
| `Slot` | Represents a parking spot with id, type, floor, coordinates |
| `Gate` | Entry point with id, floor, and coordinates |
| `Vehicle` | Holds license plate and vehicle type |
| `Ticket` | Links a vehicle to a slot with entry time |
| `SlotAssignmentStrategy` | Strategy interface for finding a slot |
| `NearestSlotStrategy` | Picks nearest compatible slot using Euclidean distance |
| `PricingStrategy` | Strategy interface for calculating fees |
| `HourlyPricing` | Charges per hour based on slot type rates |
| `VehicleType` | Enum: TWO_WHEELER, CAR, BUS |
| `SlotType` | Enum: SMALL, MEDIUM, LARGE |

### Design Patterns

- **Strategy Pattern**: Used for both slot assignment (`SlotAssignmentStrategy`) and pricing (`PricingStrategy`). This makes it easy to swap algorithms without changing the core `ParkingLot` class.

### Slot Compatibility

| Vehicle | Can park in |
|---------|------------|
| TWO_WHEELER | SMALL, MEDIUM, LARGE |
| CAR | MEDIUM, LARGE |
| BUS | LARGE only |

### Nearest Slot Assignment

The system calculates 3D Euclidean distance between the entry gate and each available compatible slot. Floor difference is weighted by 10x to prefer same-floor parking. The closest slot is assigned.

### Billing

Billing is based on the allocated slot type, not the vehicle type. If a bike parks in a MEDIUM slot, it pays the MEDIUM rate. Hours are rounded up (ceiling).

### Class Diagram

![Class Diagram](class-diagram.png)

### Game Flow

1. Create a `ParkingLot` with slot assignment and pricing strategies
2. Add gates and slots with their floor and coordinate info
3. When a vehicle arrives: call `park()` with vehicle details, entry time, requested slot type, and gate ID
4. System finds the nearest compatible slot, marks it occupied, and returns a ticket
5. When vehicle leaves: call `exit()` with the ticket and exit time
6. System calculates hours parked, applies the slot type rate, frees the slot, and returns the fee
