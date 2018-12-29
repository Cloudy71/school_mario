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

    private Field getField() {
        return Reflection.findField(source.getClass(), field);
    }

    @Override
    protected void setObjectValue(Object value) {
        try {
            Field field = getField();
            if (field == null) return;
            field.set(source, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object getStartValue() {
        try {
            Field field = getField();
            if (field == null) return null;
            return field.get(source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
