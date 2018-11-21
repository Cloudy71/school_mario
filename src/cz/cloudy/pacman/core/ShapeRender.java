/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:47
*/

package cz.cloudy.pacman.core;

import cz.cloudy.pacman.types.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class ShapeRender<T extends ShapeRender>
        implements IRenderInstance {
    protected Vector2 position;
    protected Vector2 size;
    protected Paint   paint;

    protected ShapeRender() {
        Render basic = Render.begin(true);
        this.paint = basic.getColor();
        this.position = Vector2.IDENTITY.copy();
        this.size = Vector2.IDENTITY.copy();
    }

    public T setPosition(Vector2 position) {
        this.position = position;
        return (T) this;
    }

    public T setSize(Vector2 size) {
        this.size = size;
        return (T) this;
    }

    public T setPaint(Paint paint) {
        this.paint = paint;
        return (T) this;
    }

    protected abstract void draw(GraphicsContext gc);
}
