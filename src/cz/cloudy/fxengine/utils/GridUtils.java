/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 20:57
*/

package cz.cloudy.fxengine.utils;

import cz.cloudy.fxengine.types.Vector2;

public class GridUtils {
    public static Vector2 snapToGrid(Vector2 position, Vector2 grid, Vector2 offset) {
        return new Vector2(offset.x + position.x - position.x % grid.x, offset.y + position.y - position.y % grid.y);
    }

    public static Vector2 snapToGrid(Vector2 position, Vector2 grid) {
        return snapToGrid(position, grid, Vector2.ZERO());
    }
}
