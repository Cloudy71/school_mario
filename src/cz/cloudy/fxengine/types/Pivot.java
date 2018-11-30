/*
  User: Cloudy
  Date: 26-Nov-18
  Time: 23:23
*/

package cz.cloudy.fxengine.types;

import java.io.Serializable;

public class Pivot
        implements Serializable {
    public static final Pivot TOP_LEFT() {return new Pivot(new Vector2(.0f, .0f));}

    public static final Pivot TOP_CENTER() {return new Pivot(new Vector2(.5f, .0f));}

    public static final Pivot TOP_RIGHT() {return new Pivot(new Vector2(1f, .0f));}

    public static final Pivot MIDDLE_LEFT() {return new Pivot(new Vector2(.0f, .5f));}

    public static final Pivot MIDDLE_CENTER() {return new Pivot(new Vector2(.5f, .5f));}

    public static final Pivot MIDDLE_RIGHT() {return new Pivot(new Vector2(1f, .5f));}

    public static final Pivot BOTTOM_LEFT() {return new Pivot(new Vector2(.0f, 1f));}

    public static final Pivot BOTTOM_CENTER() {return new Pivot(new Vector2(.5f, 1f));}

    public static final Pivot BOTTOM_RIGHT() {return new Pivot(new Vector2(1f, 1f));}

    public static final Pivot CENTER() {return Pivot.MIDDLE_CENTER();}

    private Vector2 position;

    public Pivot(Vector2 position) {
        this.position = position;

        if (position.x > 1f) position.x = 1f;
        else if (position.x < 0f) position.x = 0f;
        if (position.y > 1f) position.y = 1f;
        else if (position.y < 0f) position.y = 0f;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public float x() {
        return this.position.x;
    }

    public float y() {
        return this.position.y;
    }
}
