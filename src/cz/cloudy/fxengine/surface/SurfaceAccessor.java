package cz.cloudy.fxengine.surface;

import cz.cloudy.fxengine.io.CodeAccessProtector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class SurfaceAccessor {
    public static Canvas getCanvas(Surface surface) {
        if (!CodeAccessProtector.isAccessedFromPackage("cz.cloudy.")) {
            return null;
        }

        return surface.getCanvas();
    }

    public static WritableImage getSnapshot(Surface surface) {
        if (!CodeAccessProtector.isAccessedFromPackage("cz.cloudy.")) {
            return null;
        }

        return surface.getSnapshot();
    }

    public static GraphicsContext getGraphicsContext(Surface surface) {
        if (!CodeAccessProtector.isAccessedFromPackage("cz.cloudy.")) {
            return null;
        }

        return surface.getGraphicsContext();
    }

    public static void setRedrawFlag(Surface surface) {
        if (!CodeAccessProtector.isAccessedFromPackage("cz.cloudy.")) {
            return;
        }

        surface.setRedrawFlag();
    }

}
