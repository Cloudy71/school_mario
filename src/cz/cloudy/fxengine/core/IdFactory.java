package cz.cloudy.fxengine.core;

public class IdFactory {
    protected static boolean requested = false;
    private static   int     id        = 0;

    public int getId() {
        requested = false;
        return id++;
    }

    protected void requestId() {
        requested = true;
    }
}
