/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:53
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.paint.Color;

public class Coin
        extends GameObject {
    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Render.begin()
              .oval()
              .setPosition(getPosition().subtract(new Vector2(8f, 8f)))
              .setSize(new Vector2(16f, 16f))
              .setPaint(Color.LIGHTYELLOW)
              .end()
              .finish();
    }

    @Override
    public void dispose() {
    }
}
