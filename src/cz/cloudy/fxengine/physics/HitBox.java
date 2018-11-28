/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 19:10
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

import java.util.Arrays;

public class HitBox {
    protected String    name;
    protected Vector2[] polygons;
    protected boolean   trigger;

    protected HitBox(String name, Vector2[] polygons, boolean trigger) {
        this.name = name;
        this.polygons = polygons;
        this.trigger = trigger;

        if (polygons.length != 4) {
            int len = polygons.length;
            Vector2[] polys = new Vector2[4];
            for (int i = 0; i < 4; i++) {
                if (polygons.length > i) polys[i] = polygons[i];
                else polys[i] = Vector2.IDENTITY();
            }
            this.polygons = polys;
        }
    }

    public boolean isHit(Vector2 position) {
        // TODO: Do hit check.
        return false;
    }

    public String getName() {
        return this.name;
    }

    public Vector2[] getBounds() {
        return Arrays.copyOf(this.polygons, this.polygons.length);
    }
}
