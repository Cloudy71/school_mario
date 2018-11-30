/*
  User: Cloudy
  Date: 28-Nov-18
  Time: 01:27
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.image.Image;

public class PhysicsDataBuilder {
    public enum PhysicsDataQuality {
        BOUNDS, EDGE
    }

    /**
     * Builds @{@link PhysicsData} from provided image.
     *
     * @param image   The image from which @{@link PhysicsData} will be built
     * @param quality The @{@link PhysicsData} quality
     * @return new @{@link PhysicsData}
     */
    public static PhysicsData buildFromImage(Image image, PhysicsDataQuality quality) {
        PhysicsData physicsData = null;
        if (quality == PhysicsDataQuality.BOUNDS) {
            physicsData = buildRectangle(new Vector2(image.getWidth(), image.getHeight()));
        } else if (quality == PhysicsDataQuality.EDGE) {
            // TODO: Per pixel checking... (V:FINAL)
        }
        return physicsData;
    }

    /**
     * Builds rectangular @{@link PhysicsData} from given size.
     *
     * @param size The size of rectangular @{@link PhysicsData}
     * @return new @{@link PhysicsData}
     */
    public static PhysicsData buildRectangle(Vector2 size) {
        return new PhysicsData(new HitPoint[] {
                new HitPoint(new Vector2[] {
                        new Vector2(0f, 0f),
                        new Vector2(0f, size.y),
                        new Vector2(size.x, size.y)
                }),
                new HitPoint(new Vector2[] {
                        new Vector2(0f, 0f),
                        new Vector2(size.x, 0f),
                        new Vector2(size.x, size.y)
                })
        });
    }
}
