package com.snakeladder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Snake and Ladder Game ===\n");

        System.out.print("Enter board size (n for n x n board): ");
        int n = sc.nextInt();

        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();

        System.out.print("Enter difficulty (easy/hard): ");
        String diffInput = sc.next();
        Difficulty difficulty = Difficulty.valueOf(diffInput.toUpperCase());

        List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            playerNames.add(sc.next());
        }

        GameRunner game = GameFactory.createGame(n, playerNames, difficulty);

        Board board = game.getBoard();
        System.out.println("\nBoard: " + n + " x " + n + " (" + (n * n) + " cells)");
        System.out.println("Difficulty: " + difficulty);

        System.out.println("\nSnakes (" + board.getSnakes().size() + "):");
        for (Snake s : board.getSnakes()) {
            System.out.println("  " + s.getHead() + " --> " + s.getTail());
        }

        System.out.println("\nLadders (" + board.getLadders().size() + "):");
        for (Ladder l : board.getLadders()) {
            System.out.println("  " + l.getBottom() + " --> " + l.getTop());
        }

        game.start();

        sc.close();
    }
}
