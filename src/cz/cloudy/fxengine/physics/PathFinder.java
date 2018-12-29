/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:55
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;

import java.util.LinkedHashMap;
import java.util.Map;

public class PathFinder {
    protected Vector2                 tileSize;
    protected Vector2                 range;
    protected Map<Vector2, Vector2[]> restrictions;

    public PathFinder(Vector2 tileSize, Vector2 range) {
        this.tileSize = tileSize;
        this.range = range;
        this.restrictions = new LinkedHashMap<>();
    }

    public void addRestrictions(Map<Vector2, Vector2[]> points) {
        this.restrictions.putAll(points);
    }

    public void removeRestrictions() {
        this.restrictions.clear();
    }

    public ComputedPath computePath(Vector2 startTile, Vector2 endTile) {
        ComputedPath computedPath = new ComputedPath(startTile, endTile, this);
        computedPath.compute();
        return computedPath;
    }
}
