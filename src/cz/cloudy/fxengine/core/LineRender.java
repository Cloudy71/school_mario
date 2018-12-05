/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:44
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class LineRender
        extends ShapeRender<LineRender> {

    private Vector2 start;
    private Vector2 end;

    protected LineRender() {
        super();

        this.start = Vector2.IDENTITY();
        this.end = Vector2.IDENTITY();
    }

    public LineRender startPoint(Vector2 start) {
        this.start = start;

        return this;
    }

    public LineRender endPoint(Vector2 end) {
        this.end = end;

        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.setStroke(paint);
        gc.strokeLine(start.x, start.y, end.x, end.y);
    }
}
