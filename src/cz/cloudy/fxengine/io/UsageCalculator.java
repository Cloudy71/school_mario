/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 21:13
*/

package cz.cloudy.fxengine.io;

public class UsageCalculator {
    private static long start;

    public static void start() {
        start = System.nanoTime();
    }

    public static long end() {
        return System.nanoTime() - start;
    }
}
