/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:53
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.scenes.MenuScene;
import javafx.scene.paint.Color;

public class Coin
        extends GameObject {

    private float opacity;
    private int   type;

    @Override
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(16f, 16f));
        physicsData.setScalable(false);
        physicsData.setSolid(true);
        physicsData.setParent(this);
        setPhysicsData(physicsData);
        setPivot(Pivot.CENTER());
        opacity = 1f;
        type = 0;
        if (Renderer.get()
                    .getGameScene() == MenuScene.class) {
            opacity = 0f;
        }
    }

    @Override
    public void update() {
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void render() {
        Render.begin()
              .oval()
              .setPaint(new Color(1f, 0.76f, 0.643f, opacity))
              .setPosition(getPosition().copy()
                                        .subtract(new Vector2((4f * (type + 1)) * getScale().x,
                                                              (4f * (type + 1)) * getScale().y)))
              .setSize(new Vector2((8f * (type + 1)) * getScale().x, (8f * (type + 1)) * getScale().y))
              .end()
              .finish();
    }

    @Override
    protected void dispose() {
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
