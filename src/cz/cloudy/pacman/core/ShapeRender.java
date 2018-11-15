/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:47
*/

package cz.cloudy.pacman.core;

import cz.cloudy.pacman.types.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class ShapeRender<T extends ShapeRender> {
    protected Vector2 position;
    protected Vector2 size;
    protected Paint   paint;

    protected ShapeRender() {
        this.paint = Render.begin(true)
                           .getColor();
        this.position = Vector2.IDENTITY;
        this.size = Vector2.IDENTITY;
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

    public Render end() {
        return Render.begin(true)
                     .addToQueue(this);
    }
}
