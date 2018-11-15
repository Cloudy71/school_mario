package cz.cloudy.pacman.surface;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class SurfaceAccessor {
    public static Canvas getCanvas(Surface surface) {
        return surface.getCanvas();
    }

    public static WritableImage getSnapshot(Surface surface) {
        return surface.getSnapshot();
    }

    public static GraphicsContext getGraphicsContext(Surface surface) {
        return surface.getGraphicsContext();
    }

    public static void setRedrawFlag(Surface surface) {
        surface.setRedrawFlag();
    }

}
