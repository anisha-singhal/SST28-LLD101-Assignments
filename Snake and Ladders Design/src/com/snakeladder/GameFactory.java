package com.snakeladder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameFactory {

    public static GameRunner createGame(int boardSize, List<String> playerNames, Difficulty difficulty) {
        Board board = new Board(boardSize);

        Queue<Player> playerQueue = new LinkedList<>();
        for (String name : playerNames) {
            playerQueue.offer(new Player(name));
        }

        MoveRule rules;
        if (difficulty == Difficulty.EASY) {
            rules = new EasyRules();
        } else {
            rules = new HardRules();
        }

        Dice dice = new Dice(6);
        return new GameRunner(board, playerQueue, dice, rules);
    }
}
