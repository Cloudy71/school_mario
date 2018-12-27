/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 18:03
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.interfaces.IWorkable;
import cz.cloudy.fxengine.types.Vector2;

import java.util.LinkedList;
import java.util.List;

public class Animation
        implements IWorkable {
    private static int ids = 0;

    protected int            id;
    protected GameObject     gameObject;
    protected long           start;
    protected List<KeyFrame> keyFrames;

    protected Animation() {
        this.id = ids++;
        this.start = Renderer.get()
                             .getTime();
        this.keyFrames = new LinkedList<>();
    }

    @Override
    public boolean work() {
        if (keyFrames.size() == 0) return true;
        KeyFrame keyFrame = keyFrames.get(0);
        long time = Renderer.get()
                            .getTime();
        if (time < keyFrame.start) return false;
        if (!keyFrame.hasStarted) {
            keyFrame.value.source = gameObject;
            keyFrame.value.keyFrameType = keyFrame.type;
            keyFrame.startValue = keyFrame.value.getStartValue();
            keyFrame.hasStarted = true;
        }
        float completion = (float) (time - keyFrame.start) / (float) (keyFrame.end - keyFrame.start);
        if (completion > 1f) completion = 1f;
        Object now = keyFrame.startValue;
        if (keyFrame.value.value instanceof Float) {
            Float value = (Float) keyFrame.value.value;
            Float startValue = (Float) keyFrame.startValue;
            now = startValue + (value - startValue) * completion;

        } else if (keyFrame.value.value instanceof Vector2) {
            Vector2 value = (Vector2) keyFrame.value.value;
            Vector2 startValue = (Vector2) keyFrame.startValue;
            now = new Vector2(startValue.x + (value.x - startValue.x) * completion,
                              startValue.y + (value.y - startValue.y) * completion);
        }
        keyFrame.value.setObjectValue(now);
        if (time >= keyFrame.end) keyFrames.remove(keyFrame);
        return false;
    }
}
