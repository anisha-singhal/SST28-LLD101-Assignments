package com.snakeladder;

public class HardRules implements MoveRule {

    @Override
    public int calculateNewPosition(int currentPos, int diceValue, int lastCell) {
        int newPos = currentPos + diceValue;
        if (newPos > lastCell) {
            return currentPos;
        }
        return newPos;
    }

    @Override
    public boolean checkWin(int position, int lastCell) {
        return position == lastCell;
    }

    @Override
    public int maxSixesAllowed() {
        return 3;
    }
}
