/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:47
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class ShapeRender<T extends ShapeRender>
        implements IRenderInstance {
    protected Vector2 position;
    protected Vector2 size;
    protected Paint   paint;
    protected Pivot   pivot;

    protected ShapeRender() {
        Render basic = Render.begin(true);
        this.paint = basic.getColor();
        this.position = Vector2.IDENTITY();
        this.size = Vector2.IDENTITY();
        this.pivot = Pivot.TOP_LEFT();
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

    public T setPivot(Pivot pivot) {
        this.pivot = pivot;
        return (T) this;
    }

    protected abstract void draw(GraphicsContext gc);
}
