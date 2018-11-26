/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:44
*/

package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TextureRender
        extends ShapeRender<TextureRender> {

    private Image texture;

    protected TextureRender() {
        super();
        this.size = Vector2.SCALE_IDENTITY();
    }

    public TextureRender setTexture(Image image) {
        this.texture = image;
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.drawImage(texture, position.x - size.x * pivot.x(), position.y - size.y * pivot.y(),
                     size.x * texture.getWidth(), size.y * texture.getHeight());
    }
}
