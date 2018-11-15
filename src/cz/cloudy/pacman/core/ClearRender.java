/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 16:11
*/

package cz.cloudy.pacman.core;

import javafx.scene.canvas.GraphicsContext;

public class ClearRender
        extends ShapeRender<ClearRender> {

    protected ClearRender() {
        super();
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.clearRect(position.x, position.y, size.x == 0f ? gc.getCanvas()
                                                              .getWidth() : size.x, size.y == 0f ? gc.getCanvas()
                                                                                                     .getHeight() : size.y);
    }
}
