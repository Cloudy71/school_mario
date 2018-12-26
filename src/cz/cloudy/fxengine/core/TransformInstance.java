/*
  User: Cloudy
  Date: 20-Nov-18
  Time: 00:00
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;

public class TransformInstance
        implements IRenderInstance {
    protected Vector2 position;
    protected Vector2 size;

    protected TransformInstance() {
        Render basic = Render.begin(true);
        this.position = basic.getTransformPosition();
        this.size = basic.getTransformSize();
    }

    public TransformInstance position(Vector2 position) {
        this.position = position;

        return this;
    }

    public TransformInstance size(Vector2 size) {
        this.size = size;

        return this;
    }

    public TransformInstance reset() {
        this.position = Vector2.ZERO();
        this.size = Vector2.ZERO();

        return this;
    }

    public Render end() {
        return Render.begin(true)
                     .addToQueue(this);
    }

    @Override
    public void proceed() {
        Render basic = Render.begin(true);
        basic.transformPosition = position;
        basic.transformSize = size;
    }
}
