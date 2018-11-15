/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 18:53
*/

package cz.cloudy.pacman.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class TextRender
        extends ShapeRender<TextRender> {

    private String text;
    private Font   font;

    protected TextRender() {
        super();
        this.font = Render.begin(true)
                          .getFont();
    }

    public TextRender setText(String text) {
        this.text = text;
        return this;
    }

    public TextRender setFont(Font font) {
        this.font = font;
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.setFill(paint);
        gc.setFont(font);
        gc.fillText(text, position.x, position.y);
    }
}
