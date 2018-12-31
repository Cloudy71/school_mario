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
    protected boolean                 triggerPathsOnly;
    protected boolean                 findPossibleEnd;
    @Deprecated(since = "Not implemeted yet")
    protected boolean                 alwaysFindNewWay;
    @Deprecated(since = "Not implemeted yet")
    protected boolean                 fixForbiddenSidesUntilSideChange;
    protected int                     maxPossibleEndTries;

    public PathFinder(Vector2 tileSize, Vector2 range, boolean triggerPathsOnly) {
        this.tileSize = tileSize;
        this.range = range;
        this.restrictions = new LinkedHashMap<>();
        this.triggerPathsOnly = triggerPathsOnly;
        this.findPossibleEnd = false;
        this.alwaysFindNewWay = false;
        this.fixForbiddenSidesUntilSideChange = false;
        this.maxPossibleEndTries = 0;
    }

    public PathFinder(Vector2 tileSize, Vector2 range) {
        this(tileSize, range, false);
    }

    public void addRestrictions(Map<Vector2, Vector2[]> points) {
        this.restrictions.putAll(points);
    }

    public void removeRestrictions() {
        this.restrictions.clear();
    }

    public ComputedPath computePath(Vector2 startTile, Vector2 endTile, Vector2[] forbiddenSides) {
        ComputedPath computedPath = new ComputedPath(startTile, endTile, this);
        computedPath.compute(forbiddenSides);
        return computedPath;
    }

    public ComputedPath computePath(Vector2 startTile, Vector2 endTile) {
        return computePath(startTile, endTile, new Vector2[0]);
    }

    public void setFindPossibleEnd(boolean findPossibleEnd) {
        this.findPossibleEnd = findPossibleEnd;
    }

    @Deprecated(since = "Not implemeted yet")
    public void setAlwaysFindNewWay(boolean alwaysFindNewWay) {
        this.alwaysFindNewWay = alwaysFindNewWay;
    }

    @Deprecated(since = "Not implemeted yet")
    public void setFixForbiddenSidesUntilSideChange(boolean fixForbiddenSidesUntilSideChange) {
        this.fixForbiddenSidesUntilSideChange = fixForbiddenSidesUntilSideChange;
    }

    public void setMaxPossibleEndTries(int maxPossibleEndTries) {
        this.maxPossibleEndTries = maxPossibleEndTries;
    }
}
