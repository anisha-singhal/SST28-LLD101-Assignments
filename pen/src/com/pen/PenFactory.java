package com.pen;

public class PenFactory {

    public static Pen createPen(PenType type, String color, MechanismType mechanism) {
        WriteBehavior writeBehavior;
        RefillBehavior refillBehavior;

        switch (type) {
            case BALLPOINT:
                writeBehavior = new BallpointWrite();
                refillBehavior = new TubeRefill();
                break;
            case GEL:
                writeBehavior = new GelWrite();
                refillBehavior = new TubeRefill();
                break;
            case FOUNTAIN:
                writeBehavior = new FountainWrite();
                refillBehavior = new BottleRefill();
                break;
            default:
                throw new IllegalArgumentException("Unknown pen type: " + type);
        }

        OpenCloseBehavior openCloseBehavior;
        switch (mechanism) {
            case CAP:
                openCloseBehavior = new CapMechanism();
                break;
            case CLICK:
                openCloseBehavior = new ClickMechanism();
                break;
            default:
                throw new IllegalArgumentException("Unknown mechanism: " + mechanism);
        }

        return new Pen(color, writeBehavior, refillBehavior, openCloseBehavior);
    }
}
