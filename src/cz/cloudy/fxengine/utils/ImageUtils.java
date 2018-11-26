/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 15:19
*/

package cz.cloudy.fxengine.utils;

import cz.cloudy.fxengine.core.Cache;
import cz.cloudy.fxengine.types.Int2;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class ImageUtils {
    /**
     * Creates a new part {@link Image} from another {@link Image}.
     *
     * @param source   Source image
     * @param position Destination
     * @param size     Size
     * @return New {@link Image}
     */
    public static Image getImagePart(Image source, Int2 position, Int2 size) {
        Int2 key = new Int2(position.x * size.x, position.y * size.y);

        if (Cache.contains(source, key)) return Cache.get(source, key);
        Image img = new WritableImage(source.getPixelReader(), position.x, position.y, size.x, size.y);
        Cache.cache(source, key, img);
        return img;
    }
}
