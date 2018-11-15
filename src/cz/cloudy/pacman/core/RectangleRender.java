/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:44
*/

package cz.cloudy.pacman.core;

import javafx.scene.canvas.GraphicsContext;

public class RectangleRender
        extends ShapeRender<RectangleRender> {

    protected RectangleRender() {
        super();
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.setFill(paint);
        gc.fillRect(position.x, position.y, size.x, size.y);
    }
}
