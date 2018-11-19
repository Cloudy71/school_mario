/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 12:44
*/

package cz.cloudy.pacman.core;

import cz.cloudy.pacman.types.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TextureRender
        extends ShapeRender<TextureRender> {

    private Image texture;

    protected TextureRender() {
        super();
        this.size = Vector2.SCALE_IDENTITY.copy();
    }

    public TextureRender setTexture(Image image) {
        this.texture = image;
        return this;
    }

    @Override
    protected void draw(GraphicsContext gc) {
        gc.drawImage(texture, position.x, position.y, size.x * texture.getWidth(), size.y * texture.getHeight());
    }
}
