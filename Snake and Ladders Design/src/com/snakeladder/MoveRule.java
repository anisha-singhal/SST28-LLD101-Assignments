package com.snakeladder;

public interface MoveRule {

    int calculateNewPosition(int currentPos, int diceValue, int lastCell);

    boolean checkWin(int position, int lastCell);

    int maxSixesAllowed();
}
