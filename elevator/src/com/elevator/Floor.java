package com.elevator;

public class Floor {
    private int floorNumber;
    private boolean underMaintenance;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.underMaintenance = false;
    }

    public Request pressUp() {
        if (underMaintenance) {
            System.out.println("Floor " + floorNumber + " is under maintenance.");
            return null;
        }
        System.out.println("Floor " + floorNumber + ": UP button pressed.");
        return new Request(floorNumber, Direction.UP);
    }

    public Request pressDown() {
        if (underMaintenance) {
            System.out.println("Floor " + floorNumber + " is under maintenance.");
            return null;
        }
        System.out.println("Floor " + floorNumber + ": DOWN button pressed.");
        return new Request(floorNumber, Direction.DOWN);
    }

    public void setMaintenance(boolean maintenance) {
        this.underMaintenance = maintenance;
        if (maintenance) {
            System.out.println("Floor " + floorNumber + " is now under maintenance.");
        } else {
            System.out.println("Floor " + floorNumber + " is back in service.");
        }
    }

    public int getFloorNumber() { return floorNumber; }
    public boolean isUnderMaintenance() { return underMaintenance; }
}
