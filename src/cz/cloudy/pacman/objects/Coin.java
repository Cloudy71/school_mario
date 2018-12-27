/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:53
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.scenes.MenuScene;
import javafx.scene.paint.Color;

public class Coin
        extends GameObject {

    private float opacity;

    @Override
    public void create() {
        opacity = 1f;
        if (Renderer.get()
                    .getGameScene() == MenuScene.class) {
            opacity = 0f;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        Render.begin()
              .oval()
              .setPaint(new Color(1f, 0.76f, 0.643f,
                                  opacity))
              .setPosition(getPosition().copy()
                                        .subtract(new Vector2(8f * getScale().x, 8f * getScale().y)))
              .setSize(new Vector2(16f * getScale().x, 16f * getScale().y))
              .end()
              .finish();
    }

    @Override
    protected void dispose() {
    }
}
