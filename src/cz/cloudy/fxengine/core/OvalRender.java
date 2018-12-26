/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 23:54
*/

package cz.cloudy.fxengine.core;

import javafx.scene.canvas.GraphicsContext;

public class OvalRender
        extends ShapeRender<OvalRender> {
    protected OvalRender() {
        super();
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.setFill(paint);
        gc.fillOval(position.x, position.y, size.x, size.y);
    }
}
