/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 19:33
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;

public class NormalValue
        extends Value {

    public NormalValue(Vector2 value) {
        super(value);
    }

    @Override
    protected void setObjectValue(Object value) {
        switch (keyFrameType) {
            case MOVE:
                source.setPosition((Vector2) value);
                break;
            default:
                throw new RuntimeException("Not implemented animation type.");
        }
        // TODO: Other possibilities (FINAL)
    }

    @Override
    protected Object getStartValue() {
        switch (keyFrameType) {
            case MOVE:
                return source.getPosition();
            default:
                throw new RuntimeException("Not implemented animation type.");
        }
        // TODO: Other possibilities (FINAL)
    }
}
