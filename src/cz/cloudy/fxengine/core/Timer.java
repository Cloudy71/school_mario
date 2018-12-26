/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:04
*/

package cz.cloudy.fxengine.core;

public class Timer {
    private static int ids = 0;

    protected int  id;
    protected long start, end;
    protected Runnable callback;

    protected Timer(long start, long end, Runnable callback) {
        this.id = ids++;
        this.start = start;
        this.end = end;
        this.callback = callback;
    }
}
