/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 19:06
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

public class PhysicsData {
    private boolean  solid;
    private HitBox[] hitBoxes;

    protected PhysicsData(boolean solid, HitBox[] hitBoxes) {
        this.solid = solid;
        this.hitBoxes = hitBoxes;
    }

    public boolean isSolid() {
        return this.solid;
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
}
