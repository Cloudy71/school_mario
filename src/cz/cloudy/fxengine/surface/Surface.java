package cz.cloudy.fxengine.surface;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Surface {
    private Vector2 size;
    private Canvas  canvas;
    private boolean redraw;

    private SnapshotParameters snapshotParameters;
    private GraphicsContext    graphicsContext;
    private WritableImage      cachedSnapshot;

    public Surface(Vector2 size) {
        this.size = size;
        this.canvas = new Canvas(size.x, size.y);

        this.snapshotParameters = new SnapshotParameters();
        this.snapshotParameters.setFill(Color.TRANSPARENT);
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.redraw = true;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void setTarget() {
        Renderer.instance.setRenderTarget(this);
    }

    protected Canvas getCanvas() {
        return this.canvas;
    }

    private WritableImage makeSnapshot() {
        cachedSnapshot = this.canvas.snapshot(this.snapshotParameters, null);
        return cachedSnapshot;
    }

    protected WritableImage getSnapshot() {
        return !redraw && cachedSnapshot != null ? cachedSnapshot : makeSnapshot();
    }

    protected GraphicsContext getGraphicsContext() {
        return this.graphicsContext;
    }

    protected void setRedrawFlag() {
        this.redraw = true;
    }
}
