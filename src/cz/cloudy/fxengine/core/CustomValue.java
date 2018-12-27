/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 18:19
*/

package cz.cloudy.fxengine.core;

import java.lang.reflect.Field;

public class CustomValue
        extends Value {
    protected String field;

    public CustomValue(String field, Object value) {
        super(value);
        this.field = field;
    }

    @Override
    protected void setObjectValue(Object value) {
        try {
            Field field = source.getClass()
                                .getDeclaredField(this.field);
            field.setAccessible(true);
            field.set(source, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object getStartValue() {
        try {
            Field field = source.getClass()
                                .getDeclaredField(this.field);
            field.setAccessible(true);
            return field.get(source);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
