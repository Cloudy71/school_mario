/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 14:29
*/

package cz.cloudy.fxengine.types;

import cz.cloudy.fxengine.interfaces.IMeasurable;

public class Int2
        implements IMeasurable {
    public int x;
    public int y;

    public Int2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Int2[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Int2) {
            if (((Int2) obj).x == x && ((Int2) obj).y == y) return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (x + y) * (y + x);
    }
}
