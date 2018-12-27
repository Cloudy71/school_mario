/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:04
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.interfaces.IWorkable;

public class Timer implements IWorkable {
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

    @Override
    public boolean work() {
        if (Renderer.get()
                    .getTime() < end) return false;
        callback.run();
        return true;
    }
}
