package cz.cloudy.pacman.surface;

import cz.cloudy.pacman.core.Renderer;
import cz.cloudy.pacman.types.Vector2;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Surface {
    private Vector2 size;
    private Canvas  canvas;

    private SnapshotParameters snapshotParameters;
    private GraphicsContext    graphicsContext;

    public Surface(Vector2 size) {
        this.size = size;
        this.canvas = new Canvas(size.x, size.y);

        this.snapshotParameters = new SnapshotParameters();
        this.snapshotParameters.setFill(Color.TRANSPARENT);
        this.graphicsContext = this.canvas.getGraphicsContext2D();
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

    protected WritableImage getSnapshot() {
        return this.canvas.snapshot(this.snapshotParameters, null);
    }

    protected GraphicsContext getGraphicsContext() {
        return this.graphicsContext;
    }
}
