/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 15:29
*/

package cz.cloudy.fxengine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation should be used on GameObject classes which will make actual GameObject class to persist over scene changes.
 * TODO: FINAL
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated(since = "Still not implemented.")
public @interface Persistent {}
