/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:01
*/

package cz.cloudy.fxengine.core;

import java.util.LinkedList;
import java.util.List;

public class TimerService {
    protected static List<Timer> timers  = new LinkedList<>();
    private static   boolean     stack   = false;
    private static   int         stackMs = 0;

    public static int setTimer(int ms, Runnable callback) {
        timers.add(new Timer(Renderer.get()
                                     .getTime() + (stack ? stackMs : 0), Renderer.get()
                                                                                 .getTime() + (stack ? stackMs : 0) +
                                                                         (long) ms * 1_000_000, callback));
        if (stack) stackMs += ms;
        return timers.get(timers.size() - 1).id;
    }

    public static void killTimer(int id) {
        for (Timer timer : timers) {
            if (timer.id == id) {
                timers.remove(timer);
                return;
            }
        }
    }

    public static void executeTimer(int id) {
        for (Timer timer : timers) {
            if (timer.id == id) {
                timer.callback.run();
                killTimer(timer.id);
            }
        }
    }

    public static void beginStack() {
        stack = true;
        stackMs = 0;
    }

    public static void endStack() {
        stack = false;
        stackMs = 0;
    }
}
