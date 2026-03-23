package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    private List<SlotType> getCompatibleTypes(SlotType requested) {
        List<SlotType> compatible = new ArrayList<>();
        switch (requested) {
            case SMALL:
                compatible.add(SlotType.SMALL);
                compatible.add(SlotType.MEDIUM);
                compatible.add(SlotType.LARGE);
                break;
            case MEDIUM:
                compatible.add(SlotType.MEDIUM);
                compatible.add(SlotType.LARGE);
                break;
            case LARGE:
                compatible.add(SlotType.LARGE);
                break;
        }
        return compatible;
    }

    @Override
    public Slot findSlot(Gate entryGate, List<Slot> slots, SlotType requestedType) {
        List<SlotType> compatibleTypes = getCompatibleTypes(requestedType);

        Slot nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Slot slot : slots) {
            if (slot.isOccupied()) continue;
            if (!compatibleTypes.contains(slot.getType())) continue;

            double dist = slot.distanceTo(entryGate);
            if (dist < minDist) {
                minDist = dist;
                nearest = slot;
            }
        }
        return nearest;
    }
}
