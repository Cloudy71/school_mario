/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 18:37
*/

package cz.cloudy.fxengine.core;

public abstract class Value {
    protected GameObject            source;
    protected Object                value;
    protected KeyFrame.KeyFrameType keyFrameType;

    protected Value(Object value) {
        this.value = value;
    }

    protected Object getValue() {
        return this.value;
    }

    protected abstract void setObjectValue(Object value);

    protected abstract Object getStartValue();

    protected void setSource(GameObject source) {
        this.source = source;
    }
}
