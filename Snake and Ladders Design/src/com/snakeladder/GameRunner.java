package com.snakeladder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameRunner {
    private Board board;
    private Queue<Player> players;
    private Dice dice;
    private MoveRule rules;
    private List<Player> winners;

    public GameRunner(Board board, Queue<Player> players, Dice dice, MoveRule rules) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.rules = rules;
        this.winners = new ArrayList<>();
    }

    public void start() {
        System.out.println("\n--- Game Started! ---\n");

        while (players.size() >= 2) {
            Player current = players.poll();
            playTurn(current);

            if (current.hasFinished()) {
                winners.add(current);
                System.out.println("    " + current.getName() + " finished! Rank #" + winners.size());
            } else {
                players.offer(current);
            }
        }

        if (!players.isEmpty()) {
            Player last = players.poll();
            winners.add(last);
        }

        printResults();
    }

    private void playTurn(Player player) {
        int sixesInARow = 0;
        boolean turnDone = false;

        while (!turnDone) {
            int rolled = dice.roll();
            System.out.println(player.getName() + " rolled a " + rolled);

            int oldPos = player.getPosition();
            int newPos = rules.calculateNewPosition(oldPos, rolled, board.getTotalCells());

            if (newPos == oldPos) {
                System.out.println("    Can't move beyond the last cell. Staying at " + oldPos);
            } else {
                player.setPosition(newPos);
                int afterEffect = board.resolvePosition(newPos);
                player.setPosition(afterEffect);
            }

            System.out.println("    " + player.getName() + " is now at position " + player.getPosition());

            if (rules.checkWin(player.getPosition(), board.getTotalCells())) {
                player.markFinished();
                turnDone = true;
                continue;
            }

            if (rolled == 6) {
                sixesInARow++;
                int maxSixes = rules.maxSixesAllowed();

                if (maxSixes != -1 && sixesInARow >= maxSixes) {
                    System.out.println("    " + sixesInARow + " sixes in a row! Turn is over.");
                    turnDone = true;
                } else {
                    System.out.println("    Rolled a 6! Gets another turn.");
                }
            } else {
                turnDone = true;
            }
        }
        System.out.println();
    }

    private void printResults() {
        System.out.println("\n--- Game Over! ---\n");
        System.out.println("Final standings:");
        for (int i = 0; i < winners.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + winners.get(i).getName());
        }
    }

    public Board getBoard() { return board; }
}
