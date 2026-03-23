package com.snakeladder;

import java.util.Random;

public class Dice {
    private int faces;
    private Random rng;

    public Dice(int faces) {
        this.faces = faces;
        this.rng = new Random();
    }

    public int roll() {
        return rng.nextInt(faces) + 1;
    }
}
