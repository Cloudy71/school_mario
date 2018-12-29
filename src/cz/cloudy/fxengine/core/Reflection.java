/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:42
*/

package cz.cloudy.fxengine.core;

import java.lang.reflect.Field;

public class Reflection {
    public static Field findField(Class<?> clazz, String field) {
        Field ffield = null;
        try {
            ffield = clazz.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) ffield = findField(clazz.getSuperclass(), field);
        }
        if (ffield != null) ffield.setAccessible(true);
        return ffield;
    }
}
