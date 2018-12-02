/*
  User: Cloudy
  Date: 26-Nov-18
  Time: 19:26
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.io.Keyboard;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.input.KeyCode;

public class PacMan
        extends GameObject {

    private int side;

    @Override
    public void create() {
        setScale(new Vector2(2f, 2f));
        setPivot(Pivot.CENTER());
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setScalable(false);
        physicsData.setSolid(true);
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
    public void fixedUpdate() {
        if (Keyboard.isKeyPress(KeyCode.UP)) {
            this.move(new Vector2(0f, -2f));
        }
    }
}
