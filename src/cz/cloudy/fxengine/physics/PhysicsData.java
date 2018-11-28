/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 19:06
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

import java.util.Arrays;

public class PhysicsData {
    private boolean  solid;
    private boolean  scalable;
    private HitBox[] hitBoxes;

    protected PhysicsData(HitBox[] hitBoxes) {
        this.solid = true;
        this.scalable = true;
        this.hitBoxes = hitBoxes;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public void setScalable(boolean scalable) {
        this.scalable = scalable;
    }

    public boolean isScalable() {
        return this.scalable;
    }

    public HitBox isHit(Vector2 position) {
        for (HitBox hitBox : hitBoxes) {
            if (hitBox.isHit(position)) return hitBox;
        }
        return null;
    }

    public boolean isHitOnly(Vector2 position) {
        for (HitBox hitBox : hitBoxes) {
            if (hitBox.isHit(position)) return true;
        }
        return false;
    }

    public HitBox[] getHitBoxes() {
        return Arrays.copyOf(this.hitBoxes, this.hitBoxes.length);
    }
}
