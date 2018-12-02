/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 21:07
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.paint.Color;

public class WallTile
        extends GameObject {

    @Override
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setSolid(true);
        physicsData.setTrigger(false);
        physicsData.setParent(this);
        setPhysicsData(physicsData);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        Render.begin()
              .rect()
              .setPosition(getPosition())
              .setPaint(Color.LIGHTBLUE)
              .setSize(new Vector2(32f, 32f))
              .end()
              .finish();
    }
}
