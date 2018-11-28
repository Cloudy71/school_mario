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

    public static PhysicsData buildFromImage(Image image, PhysicsDataQuality quality) {
        PhysicsData physicsData = null;
        if (quality == PhysicsDataQuality.BOUNDS) {
//            HitBox hitBox = new HitBox(null, new Vector2[] {new Vector2(0f, 0f), new Vector2(0f, image.getHeight()),
//                                                            new Vector2(image.getWidth(), image.getHeight()),
//                                                            new Vector2(image.getWidth(), 0f)}, false);
//            physicsData = new PhysicsData(false, new HitBox[] {hitBox});
            physicsData = buildRectangle(new Vector2(image.getWidth(), image.getHeight()));
        } else if (quality == PhysicsDataQuality.EDGE) {
            // TODO: Per pixel checking... (V:FINAL)
        }
        return physicsData;
    }

    public static PhysicsData buildRectangle(Vector2 size) {
        return new PhysicsData(new HitBox[] {new HitBox(null, new Vector2[] {new Vector2(0f, 0f),
                                                                                    new Vector2(0f, size.y),
                                                                                    new Vector2(size.x, size.y),
                                                                                    new Vector2(size.x, 0f)}, false)});
    }
}
