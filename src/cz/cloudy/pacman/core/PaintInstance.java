package cz.cloudy.pacman.core;

import javafx.scene.paint.Paint;

public class PaintInstance implements IRenderInstance {
    protected Paint paint;

    protected PaintInstance() {
        this.paint = Render.begin(true).paint;
    }

    public PaintInstance paint(Paint paint) {
        this.paint = paint;
        Render.begin(true).currentPaint = paint;
        return this;
    }

    @Override
    public void proceed() {
        Render basic = Render.begin(true);

        basic.paint = this.paint;
    }
}
