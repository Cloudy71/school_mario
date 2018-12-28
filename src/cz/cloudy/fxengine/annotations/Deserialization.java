/*
  User: Cloudy
  Date: 27-Dec-18
  Time: 18:14
*/

package cz.cloudy.fxengine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Deserialization {}
