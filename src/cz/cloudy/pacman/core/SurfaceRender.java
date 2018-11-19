/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 16:11
*/

package cz.cloudy.pacman.core;

import cz.cloudy.pacman.surface.Surface;
import cz.cloudy.pacman.surface.SurfaceAccessor;
import cz.cloudy.pacman.types.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class SurfaceRender
        extends ShapeRender<SurfaceRender> {

    private Surface surface;

    protected SurfaceRender() {
        super();
        this.size = Vector2.SCALE_IDENTITY.copy();
    }

    public SurfaceRender setSurface(Surface surface) {
        this.surface = surface;
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.drawImage(SurfaceAccessor.getSnapshot(surface), position.x, position.y, size.x * surface.getSize().x,
                     size.y * surface.getSize().y);
    }
}
