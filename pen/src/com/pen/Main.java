package com.pen;

public class Main {
    public static void main(String[] args) {
        Pen gelPen = PenFactory.createPen(PenType.GEL, "Blue", MechanismType.CLICK);

        gelPen.write();

        gelPen.start();
        gelPen.write();
        gelPen.close();

        System.out.println();

        gelPen.refill("Red");
        gelPen.start();
        gelPen.write();
        gelPen.close();

        System.out.println("\n---\n");

        Pen fountainPen = PenFactory.createPen(PenType.FOUNTAIN, "Black", MechanismType.CAP);

        fountainPen.start();
        fountainPen.write();
        fountainPen.refill("Green");
        fountainPen.write();
        fountainPen.close();

        System.out.println("\n---\n");

        Pen ballpoint = PenFactory.createPen(PenType.BALLPOINT, "Black", MechanismType.CLICK);
        ballpoint.start();
        ballpoint.write();
        ballpoint.close();
    }
}
