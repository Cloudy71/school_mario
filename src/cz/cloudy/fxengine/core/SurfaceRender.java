/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 16:11
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class SurfaceRender
        extends ShapeRender<SurfaceRender> {

    private Surface surface;

    protected SurfaceRender() {
        super();
        this.size = Vector2.SCALE_IDENTITY();
    }

    public SurfaceRender setSurface(Surface surface) {
        this.surface = surface;
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.drawImage(surface.getSnapshot(), position.x - surface.getSize().x * pivot.x(),
                     position.y - surface.getSize().y * pivot.y(), size.x * surface.getSize().x,
                     size.y * surface.getSize().y);
    }
}
