/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 13:56
*/

package cz.cloudy.pacman.types;

import cz.cloudy.pacman.core.Cache;
import cz.cloudy.pacman.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.InputStream;

public class Tileset {
    private WritableImage writableImage;
    private Int2          dimension;

    public Tileset(InputStream inputStream, Int2 dimension) {
        Image image = new Image(inputStream, 0, 0, false, false);
        this.writableImage = new WritableImage(image.getPixelReader(), 0, 0, (int) image.getWidth(),
                                               (int) image.getHeight());
        this.dimension = dimension;
    }

    public Int2 getDimension() {
        return this.dimension;
    }

    public Vector2 getSize() {
        return new Vector2(writableImage.getWidth(), writableImage.getHeight());
    }

    public Int2 getParts() {
        return new Int2((int) writableImage.getWidth() / dimension.x, (int) writableImage.getHeight() / dimension.y);
    }

    public Image getSource() {
        return this.writableImage;
    }

    private Image makePart(Int2 position) {
        Cache.cache(false);
        Image part = ImageUtils.getImagePart(writableImage,
                                             new Int2(position.x * dimension.x, position.y * dimension.y),
                                             new Int2(dimension.x, dimension.y));
        Cache.cache(true);

        Cache.cache(this, position, part);
        return part;
    }

    public Image getPart(Int2 position) {
        return Cache.contains(this, position) ? Cache.get(this, position) : makePart(position);
    }
}
