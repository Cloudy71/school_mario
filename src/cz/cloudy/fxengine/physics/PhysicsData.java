/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 19:06
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

import java.io.Serializable;
import java.util.Arrays;

public class PhysicsData implements Serializable {
    private boolean    scalable;
    private HitPoint[] hitPoints;

    protected PhysicsData(HitPoint[] hitPoints) {
        this.scalable = true;
        this.hitPoints = hitPoints;
    }

    public void setScalable(boolean scalable) {
        this.scalable = scalable;
    }

    public boolean isScalable() {
        return this.scalable;
    }

    public HitPoint isTrigger(Vector2 position) {
        for (HitPoint hitPoint : hitPoints) {
            if (hitPoint.isTrigger() && hitPoint.isHit(position)) return hitPoint;
        }
        return null;
    }

    public HitPoint isHit(Vector2 position) {
        for (HitPoint hitPoint : hitPoints) {
            if (hitPoint.isSolid() && hitPoint.isHit(position)) return hitPoint;
        }
        return null;
    }

    public boolean isTriggerOnly(Vector2 position) {
        return isTrigger(position) != null;
    }

    public boolean isHitOnly(Vector2 position) {
        return isHit(position) != null;
    }

    public HitPoint[] getHitPoints() {
        return Arrays.copyOf(this.hitPoints, this.hitPoints.length);
    }

    public void setSolid(boolean solid) {
        for (HitPoint hitPoint : hitPoints) {
            hitPoint.setSolid(solid);
        }
    }

    public void setTrigger(boolean trigger) {
        for (HitPoint hitPoint : hitPoints) {
            hitPoint.setTrigger(trigger);
        }
    }
}
