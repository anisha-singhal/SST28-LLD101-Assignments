package com.snakeladder;

public class Player {
    private String name;
    private int position;
    private boolean finished;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.finished = false;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
    public boolean hasFinished() { return finished; }
    public void markFinished() { this.finished = true; }
}
