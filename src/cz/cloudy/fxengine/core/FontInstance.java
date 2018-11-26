package cz.cloudy.fxengine.core;

import javafx.scene.text.Font;

public class FontInstance implements IRenderInstance {
    protected Font font;

    protected FontInstance() {
        this.font = Render.begin(true).getFont();
    }

    public FontInstance font(Font font) {
        this.font = font;
        Render.begin(true).currentFont = font;
        return this;
    }

    @Override
    public void proceed() {
        Render basic = Render.begin(true);

        basic.font = this.font;
    }
}
