package com.pen;

public class FountainWrite implements WriteBehavior {
    @Override
    public void write() {
        System.out.println("Writing with fountain nib...");
    }
}
