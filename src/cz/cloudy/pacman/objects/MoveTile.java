/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 14:53
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.types.Vector2;

public class MoveTile
        extends GameObject {

    @Override
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setScalable(false);
        physicsData.setSolid(false);
        physicsData.setTrigger(true);
        setPhysicsData(physicsData);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
