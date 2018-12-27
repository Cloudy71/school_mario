/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 18:02
*/

package cz.cloudy.fxengine.core;

import java.util.LinkedList;
import java.util.List;

public class AnimationService {
    protected static List<Animation> animations = new LinkedList<>();
    private static   Animation       current    = null;
    private static   int             stackMs    = 0;

    public static int beginAnimation(GameObject object) {
        current = new Animation();
        current.gameObject = object;
        stackMs = 0;
        return current.id;
    }

    public static void endAnimation() {
        if (current == null) return;
        animations.add(current);
        current = null;
        stackMs = 0;
    }

    public static void addKeyFrame(int ms, KeyFrame.KeyFrameType type, Value value) {
        if (current == null) return;
        current.keyFrames.add(new KeyFrame(((long) stackMs * 1_000_000) + current.start,
                                           ((long) stackMs * 1_000_000) + current.start + ((long) ms * 1_000_000), type,
                                           value));
        stackMs += ms;
    }

    public static void killAnimation(int id) {
        for (Animation animation : animations) {
            if (animation.id == id) {
                animations.remove(animation);
                return;
            }
        }
    }
}
