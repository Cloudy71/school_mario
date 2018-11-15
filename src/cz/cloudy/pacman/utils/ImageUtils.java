/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 15:19
*/

package cz.cloudy.pacman.utils;

import cz.cloudy.pacman.core.Cache;
import cz.cloudy.pacman.types.Int2;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class ImageUtils {
    public static Image getImagePart(Image source, Int2 position, Int2 size) {
        Int2 key = new Int2(position.x * size.x, position.y * size.y);

        if (Cache.contains(source, key)) return Cache.get(source, key);
        Image img = new WritableImage(source.getPixelReader(), position.x, position.y, size.x, size.y);
        Cache.cache(source, key, img);
        return img;
    }
}
