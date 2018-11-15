/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:34
*/

package cz.cloudy.pacman.core;

import cz.cloudy.pacman.surface.SurfaceAccessor;
import cz.cloudy.pacman.types.Vector2;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.LinkedList;
import java.util.List;

public class Render {
    private static Render instance;

    private Paint             paint;
    private Font              font;
    private List<ShapeRender> queue;

    private static boolean locked;

    private Render() {
        instance = this;
        paint = Color.BLACK;
        font = Font.getDefault();
        queue = new LinkedList<>();
        locked = false;
        getContext().setTextAlign(TextAlignment.LEFT);
        getContext().setTextBaseline(VPos.TOP);
    }

    protected static void lock() {
        locked = true;
        System.out.println("locked");
    }

    protected static void unlock() {
        locked = false;
        System.out.println("unlocked");
    }

    public static Render begin(boolean dirtyRender) {
        if (locked) return null;
        return instance == null ? new Render() : ((dirtyRender) ? instance : getCleanRender());
    }

    public static Render begin() {
        return begin(false);
    }

    protected static Render getCleanRender() {
        instance.queue.clear();
        return instance;
    }

    protected static GraphicsContext getContext() {
        return SurfaceAccessor.getGraphicsContext(Renderer.get()
                                                          .getCurrentTarget());
    }

    protected static Canvas getCanvas() {
        return SurfaceAccessor.getCanvas(Renderer.get()
                                                 .getCurrentTarget());
    }

    protected Render addToQueue(ShapeRender shapeRender) {
        queue.add(shapeRender);
        return this;
    }

    public Paint getColor() {
        return this.paint;
    }

    public Font getFont() {return this.font;}

    public Render color(Paint paint) {
        this.paint = paint;

        return this;
    }

    public Render font(Font font) {
        this.font = font;

        return this;
    }

    public Render clear(boolean solid) {
        return solid ? rect().setPosition(Vector2.IDENTITY)
                             .setSize(new Vector2(getCanvas().getWidth(), getCanvas().getHeight()))
                             .end() : new ClearRender().end();
    }

    public Render clear() {
        return clear(false);
    }

    public RectangleRender rect() {
        return new RectangleRender();
    }

    public TextureRender tex() {return new TextureRender();}

    public SurfaceRender surface() {return new SurfaceRender();}

    public TextRender text() {return new TextRender();}

    public void finish() {
        for (ShapeRender shapeRender : queue) {
            shapeRender.draw(Render.getContext());
        }
        queue.clear();
    }
}
