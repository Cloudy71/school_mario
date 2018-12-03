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
    private int type;

    @Override
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setScalable(false);
        physicsData.setSolid(false);
        physicsData.setTrigger(true);
        physicsData.setParent(this);
        setPhysicsData(physicsData);

        this.type = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private float[] calcSizes(GameObject collider) {
        float p1 = 16f + 4f;
        float p2 = type == 0 ? 24f : 20f;
        if (collider.getClass() == MoveTile.class) {
            MoveTile moveTile = (MoveTile) collider;
            if (moveTile.getType() != 0) {
                p1 = 16f + 8f;
                p2 = 24f - (type != 0 ? 4f : 0f) - 4f;
            }
        }
        return new float[] {
                p1,
                p2
        };
    }

    @Override
    public void render() {
        Render r = Render.begin();
        GameObject collider = RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                                                 .add(new Vector2(16f, 16 - 32f)));
        if (collider != null && collider.getClass() == MoveTile.class) {
            float[] sizes = calcSizes(collider);

            r.rect()
             .setPosition(collider.getPosition()
                                  .copy()
                                  .add(new Vector2(16f, sizes[0])))
             .setSize(new Vector2(2f, sizes[1]))
             .setPaint(Color.PINK)
             .end();
        }
        collider = RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                                      .add(new Vector2(16f - 32f, 16f)));
        if (collider != null && collider.getClass() == MoveTile.class) {
            float[] sizes = calcSizes(collider);

            r.rect()
             .setPosition(collider.getPosition()
                                  .copy()
                                  .add(new Vector2(sizes[0], 16f)))
             .setSize(new Vector2(sizes[1], 2f))
             .setPaint(Color.PINK)
             .end();
        }

        r.finish();

        if (Renderer.instance.getGameScene() == EditorScene.class) {
            if (type == 0) {
                Render.begin()
                      .rect()
                      .setPosition(getPosition().copy()
                                                .add(new Vector2(12f, 12f)))
                      .setPaint(Color.LIGHTGREEN)
                      .setSize(new Vector2(8f, 8f))
                      .end()
                      .finish();
            } else if (type == 1) {
                Render.begin()
                      .rect()
                      .setPosition(getPosition().copy()
                                                .add(new Vector2(8f, 8f)))
                      .setPaint(Color.YELLOW)
                      .setSize(new Vector2(16f, 16f))
                      .end()
                      .finish();
            } else if (type == 2) {
                Render.begin()
                      .rect()
                      .setPosition(getPosition().copy()
                                                .add(new Vector2(8f, 8f)))
                      .setPaint(Color.GHOSTWHITE)
                      .setSize(new Vector2(16f, 16f))
                      .end()
                      .finish();
            }
        }
    }
}
