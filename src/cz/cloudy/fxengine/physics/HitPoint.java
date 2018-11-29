/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 19:10
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

public class HitPoint {
    protected Vector2[] polygons;
    protected boolean   trigger;
    protected boolean   solid;

    protected HitPoint(Vector2[] polygons) {
        this.polygons = polygons;
        this.trigger = false;
        this.solid = true;

        if (polygons.length != 3) {
            int len = polygons.length;
            Vector2[] polys = new Vector2[3];
            for (int i = 0; i < 3; i++) {
                if (polygons.length > i) polys[i] = polygons[i];
                else polys[i] = Vector2.IDENTITY();
            }
            this.polygons = polys;
        }
    }

    private float sign(Vector2 p1, Vector2 p2, Vector2 p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    public boolean isHit(Vector2 position) {
        float d1, d2, d3;
        boolean hasNeg, hasPos;

        d1 = sign(position, polygons[0], polygons[1]);
        d2 = sign(position, polygons[1], polygons[2]);
        d3 = sign(position, polygons[2], polygons[0]);

        hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Vector2[] getPolygons() {
        return this.polygons;
    }
}
