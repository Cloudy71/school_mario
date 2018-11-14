package cz.cloudy.pacman.core;

public class IdFactory {
    private static   int     id        = 0;
    protected static boolean requested = false;

    public int getId() {
        requested = false;
        return id++;
    }

    protected void requestId() {
        requested = true;
    }
}
