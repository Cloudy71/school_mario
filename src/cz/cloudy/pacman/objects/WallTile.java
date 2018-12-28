/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 21:07
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.annotations.Deserialization;
import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.physics.RayCaster;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.scenes.EditorScene;
import javafx.scene.paint.Color;

public class WallTile
        extends GameObject {
    private static final long serialVersionUID = 24L;

    transient private boolean hasRight       = false;
    transient private boolean hasTop         = false;
    transient private boolean hasLeft        = false;
    transient private boolean hasBottom      = false;
    transient private boolean hasTopRight    = false;
    transient private boolean hasBottomRight = false;
    transient private boolean hasTopLeft     = false;
    transient private boolean hasBottomLeft  = false;

    transient private boolean isCalculated  = false;
    transient private int     round         = 6;
    transient private Color   wallColor     = Color.LIGHTBLUE;
    transient private Color   wallLineColor = new Color(0.3f, 0.3f, 0.3f, 1f);
    transient private Color   wallDarkColor = new Color(0.1f, 0.1f, 0.1f, 1f);

    @Override
    @Deserialization
    public void create() {
        PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
        physicsData.setSolid(true);
        physicsData.setTrigger(false);
        physicsData.setParent(this);
        setPhysicsData(physicsData);

        this.hasRight = false;
        this.hasTop = false;
        this.hasLeft = false;
        this.hasBottom = false;
        this.hasTopRight = false;
        this.hasBottomRight = false;
        this.hasTopLeft = false;
        this.hasBottomLeft = false;
        this.isCalculated = false;
        this.round = 6;
        this.wallColor = Color.LIGHTBLUE;
        this.wallLineColor = new Color(0.3f, 0.3f, 0.3f, 1f);
        this.wallDarkColor = new Color(0.1f, 0.1f, 0.1f, 1f);
    }

    @Override
    public void update() {
        if (Renderer.get()
                    .getGameScene() != EditorScene.class) {
            if (!isCalculated) {
                calculateWalls();
                isCalculated = true;
            }
        }
    }

    private void calculateWalls() {
        RayCaster.checkFor(RayCaster.RayCastCheck.SOLIDS);
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(48f, 16f))) != null) {
            hasRight = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(16f, -16f))) != null) {
            hasTop = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(-16f, 16f))) != null) {
            hasLeft = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(16f, 48f))) != null) {
            hasBottom = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(48f, -16f))) != null) {
            hasTopRight = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(-16f, -16f))) != null) {
            hasTopLeft = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(48f, 48f))) != null) {
            hasBottomRight = true;
        }
        if (RayCaster.castPoint(GameObject.class, getPosition().copy()
                                                               .add(new Vector2(-16f, 48f))) != null) {
            hasBottomLeft = true;
        }
        RayCaster.checkFor(RayCaster.RayCastCheck.ALL);
        isCalculated = true;
    }

    @Override
    protected void dispose() {

    }

    @Override
    public void render() {
        if (Renderer.get()
                    .getGameScene() == EditorScene.class) {
            Render.begin()
                  .rect()
                  .setPosition(getPosition())
                  .setPaint(Color.LIGHTBLUE)
                  .setSize(new Vector2(32f, 32f))
                  .end()
                  .finish();
        } else {
            if (!isCalculated) return;
            Render r = Render.begin();

            r.rect()
             .setPosition(getPosition())
             .setSize(new Vector2(32f, 32f))
             .setPaint(wallDarkColor)
             .end();

            if (!hasRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(32f, round)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f, 32f - round)))
                 .setPaint(wallColor)
                 .end();
            }
            if (!hasTop) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(round, 0f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f - round, 0f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (!hasLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(0f, round)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(0f, 32f - round)))
                 .setPaint(wallColor)
                 .end();
            }
            if (!hasBottom) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(round, 32f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f - round, 32f)))
                 .setPaint(wallColor)
                 .end();
            }

            if (hasBottom && !hasRight && !hasBottomRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(32f, 32f - round)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f, 32f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasBottom && !hasLeft && !hasBottomLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(0f, 32f - round)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(0f, 32f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasTop && !hasRight && !hasTopRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(32f, 0f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f, round)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasTop && !hasLeft && !hasTopLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(0f, 0f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(0f, round)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasLeft && !hasTop && !hasTopLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(0f, 0f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(round, 0f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasRight && !hasTop && !hasTopRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(32f - round, 0f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f, 0f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasRight && !hasBottom && !hasBottomRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(32f - round, 32f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(32f, 32f)))
                 .setPaint(wallColor)
                 .end();
            }
            if (hasLeft && !hasBottom && !hasBottomLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(round, 32f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(0f, 32f)))
                 .setPaint(wallColor)
                 .end();
            }

//            if (hasTop && hasTopLeft) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(0f, round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(-round / 2f, round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && hasBottomRight) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f - round, 32f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f - round / 2f, 32f + round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasTop && hasTopRight) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f, round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f + round / 2f, round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasLeft && hasBottomLeft) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(round, 32f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(round / 2f, 32f + round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasLeft && hasTopLeft) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(round, 0f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(round / 2f, -round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasBottom && hasBottomRight) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f, 32f - round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f + round / 2f, 32f - round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && hasTopRight) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f - round, 0f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f - round / 2f, -round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasBottom && hasBottomLeft) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(0f, 32f - round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(-round / 2f, 32f - round / 2f)))
//                 .setPaint(wallColor)
//                 .end();
//            }

//            if (!hasRight && !hasTop && hasLeft && hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32 - round, 0f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f, round)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (!hasRight && hasTop && hasLeft && !hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32, 32f - round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f - round, 32f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && !hasTop && !hasLeft && hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(0, round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(round, 0f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && hasTop && !hasLeft && !hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(0f, 32f - round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(round, 32f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (!hasRight && !hasTop && hasLeft && hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(-round, 32f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(0f, 32f + round)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && !hasTop && !hasLeft && hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f + round, 32f)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f, 32f + round)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (hasRight && hasTop && !hasLeft && !hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(32f, -round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(32f + round, 0f)))
//                 .setPaint(wallColor)
//                 .end();
//            }
//            if (!hasRight && hasTop && hasLeft && !hasBottom) {
//                r.line()
//                 .startPoint(getPosition().copy()
//                                          .add(new Vector2(0f, -round)))
//                 .endPoint(getPosition().copy()
//                                        .add(new Vector2(-round, 0f)))
//                 .setPaint(wallColor)
//                 .end();
//            }

            if (hasLeft && hasBottom) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(8f, 16f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(16f, 24f)))
                 .setPaint(wallLineColor)
                 .end();
            }
            if (hasRight && hasBottom) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(16f, 24f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(24f, 16f)))
                 .setPaint(wallLineColor)
                 .end();
            }
            if (hasTop && hasRight) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(16f, 8f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(24f, 16f)))
                 .setPaint(wallLineColor)
                 .end();
            }
            if (hasTop && hasLeft) {
                r.line()
                 .startPoint(getPosition().copy()
                                          .add(new Vector2(16f, 8f)))
                 .endPoint(getPosition().copy()
                                        .add(new Vector2(8f, 16f)))
                 .setPaint(wallLineColor)
                 .end();
            }


            r.finish();
        }
    }
}
