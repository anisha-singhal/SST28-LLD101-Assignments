package com.snakeladder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board {
    private int size;
    private int totalCells;
    private List<Snake> snakes;
    private List<Ladder> ladders;

    public Board(int size) {
        this.size = size;
        this.totalCells = size * size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        generateBoard(size);
    }

    private void generateBoard(int count) {
        Random rng = new Random();
        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(totalCells);

        int maxPossible = (totalCells - 2) / 4;
        int actualCount = Math.min(count, maxPossible);

        int placed = 0;
        int attempts = 0;
        while (placed < actualCount && attempts < 1000) {
            attempts++;
            int head = rng.nextInt(totalCells - 2) + 2;
            int tail = rng.nextInt(head - 1) + 1;

            if (occupied.contains(head) || occupied.contains(tail)) continue;

            snakes.add(new Snake(head, tail));
            occupied.add(head);
            occupied.add(tail);
            placed++;
        }

        placed = 0;
        attempts = 0;
        while (placed < actualCount && attempts < 1000) {
            attempts++;
            int bottom = rng.nextInt(totalCells - 2) + 2;
            int top = bottom + rng.nextInt(totalCells - bottom);

            if (top >= totalCells) continue;
            if (top <= bottom) continue;
            if (occupied.contains(bottom) || occupied.contains(top)) continue;

            ladders.add(new Ladder(bottom, top));
            occupied.add(bottom);
            occupied.add(top);
            placed++;
        }
    }

    public int resolvePosition(int position) {
        for (Snake snake : snakes) {
            if (snake.getHead() == position) {
                System.out.println("    Oops! Snake at " + position + " -- slides down to " + snake.getTail());
                return snake.getTail();
            }
        }
        for (Ladder ladder : ladders) {
            if (ladder.getBottom() == position) {
                System.out.println("    Nice! Ladder at " + position + " -- climbs up to " + ladder.getTop());
                return ladder.getTop();
            }
        }
        return position;
    }

    public int getTotalCells() { return totalCells; }
    public int getSize() { return size; }
    public List<Snake> getSnakes() { return snakes; }
    public List<Ladder> getLadders() { return ladders; }
}
