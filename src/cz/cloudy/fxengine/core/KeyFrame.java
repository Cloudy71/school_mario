/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 18:04
*/

package cz.cloudy.fxengine.core;

public class KeyFrame {
    public enum KeyFrameType {
        MOVE, ROTATE, SCALE, CUSTOM
    }

    protected long start, end;
    protected KeyFrameType type;
    protected Value        value;
    protected Object       startValue;
    protected boolean      hasStarted;

    protected KeyFrame(long start, long end, KeyFrameType type, Value value) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.value = value;
        this.startValue = null;
        this.hasStarted = false;
    }
}
