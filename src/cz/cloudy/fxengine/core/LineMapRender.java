/*
  User: Cloudy
  Date: 27-Nov-18
  Time: 00:05
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

public class LineMapRender
        extends ShapeRender<LineMapRender> {
    private List<Vector2> lines;

    protected LineMapRender() {
        super();
        this.lines = new LinkedList<>();
    }

    public LineMapRender line(Vector2 position) {
        lines.add(position);
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.fillPolygon(lines.stream()
                            .mapToDouble(value -> value.x)
                            .toArray(), lines.stream()
                                             .mapToDouble(value -> value.y)
                                             .toArray(), lines.size());

    }
}
