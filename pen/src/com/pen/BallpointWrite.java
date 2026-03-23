package com.pen;

public class BallpointWrite implements WriteBehavior {
    @Override
    public void write() {
        System.out.println("Writing with ballpoint tip...");
    }
}
