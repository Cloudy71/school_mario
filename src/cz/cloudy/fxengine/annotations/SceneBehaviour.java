/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 15:32
*/

package cz.cloudy.fxengine.annotations;

import cz.cloudy.fxengine.interfaces.IGameScene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: FINAL
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Deprecated(since = "Still not implemented.")
public @interface SceneBehaviour {
    enum BehaviourType {
        ON_START, ON_RENDER, ON_UPDATE
    }
    /**
     * Behaviour will be always notified after scene change to one of specified scenes classes.
     */
    Class<? extends IGameScene>[] scenes();

    BehaviourType type();
}
