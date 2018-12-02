/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 14:53
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.physics.RayCaster;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.scenes.EditorScene;
import javafx.scene.paint.Color;

public class MoveTile
        extends GameObject {

    @Override
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setScalable(false);
        physicsData.setSolid(false);
        physicsData.setTrigger(true);
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
        if (Renderer.instance.getGameScene() == EditorScene.class) {
            Render.begin()
                  .rect()
                  .setPosition(getPosition().copy()
                                            .add(new Vector2(12f, 12f)))
                  .setPaint(Color.LIGHTGREEN)
                  .setSize(new Vector2(8f, 8f))
                  .end()
                  .finish();

            Render r = Render.begin();
            GameObject collider = RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                                                     .add(new Vector2(16f, 16 - 32f)));
            if (collider != null && collider.getClass() == MoveTile.class) {
                r.rect()
                 .setPosition(collider.getPosition()
                                      .copy()
                                      .add(new Vector2(16f, 16f)))
                 .setSize(new Vector2(2f, 32f))
                 .setPaint(Color.DARKGREEN)
                 .end();
            }
            collider = RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                                          .add(new Vector2(16f - 32f, 16f)));
            if (collider != null && collider.getClass() == MoveTile.class) {
                r.rect()
                 .setPosition(collider.getPosition()
                                      .copy()
                                      .add(new Vector2(16f, 16f)))
                 .setSize(new Vector2(32f, 2f))
                 .setPaint(Color.DARKGREEN)
                 .end();
            }

            r.finish();
        }
    }
}
