/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:58
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;

import java.util.*;

public class ComputedPath {
    private Vector2    start;
    private Vector2    end;
    private PathFinder pathFinder;

    private List<Vector2> moves;
    private boolean       found;
    private List<Vector2> tried;

    protected ComputedPath(Vector2 start, Vector2 end, PathFinder pathFinder) {
        this.start = start;
        this.end = end;
        this.pathFinder = pathFinder;
        this.moves = new LinkedList<>();
        this.found = false;
        this.tried = new LinkedList<>();
    }

    protected void compute(Vector2[] forbiddenSides) {
        do {
            moves.clear();
            tried.clear();
            found = traverse(start, forbiddenSides);
            if (!found && !findPossibleEnd()) break;
        } while (!found);
    }

    private boolean findPossibleEnd() {
        if (!pathFinder.findPossibleEnd) return false;
        // Finds points where pathfinder can find path to it and also is near the start point.
        Vector2 absEnd = end.copy()
                            .scale(new Vector2(32f, 32f));
        Vector2 absStart = start.copy()
                                .scale(new Vector2(32f, 32f));
        if (absEnd.distance(absStart) > pathFinder.tileSize.onePoint()) {
            absEnd = absEnd.moveTowards(absStart, pathFinder.tileSize.onePoint());
            absEnd.x = Math.round(absEnd.x);
            absEnd.y = Math.round(absEnd.y);
            end = GridUtils.getTileByGridRound(absEnd, new Vector2(32f, 32f));
            return true;
        }
        return false;
    }

    // TODO: Optimizer is still bad, sometimes.
    public ComputedPath optimize() {
        if (!this.found) {
            return this;
        }

        int side = -1;
        int sideStart = 0;
        List<Vector2> newMoves = new LinkedList<>();
        for (int i = 1; i < moves.size(); i++) {
            Vector2 move = moves.get(i);
            if (side == -1) {
                side = getSide(moves.get(sideStart), move);
            }
            int nSide = getSide(moves.get(sideStart), move);
            if (nSide == side) continue;
            else {
                if (!newMoves.contains(moves.get(sideStart))) newMoves.add(moves.get(sideStart));
                if (!moves.get(sideStart)
                          .equals(moves.get(i - 1))) newMoves.add(moves.get(i - 1));
                side = -1;
                sideStart = i - 1;
            }
        }
        newMoves.add(moves.get(moves.size() - 1));

        moves.clear();
        moves.addAll(newMoves);

        return this;
    }

    private int getSide(Vector2 old, Vector2 now) {
        if (old.x > now.x && old.y == now.y) return 0;
        else if (old.x < now.x && old.y == now.y) return 2;
        else if (old.x == now.x && old.y > now.y) return 1;
        else if (old.x == now.x && old.y < now.y) return 0;
        return -1;
    }

    public boolean isFound() {
        return found;
    }

    public Vector2[] moves() {
        return moves.toArray(new Vector2[0]);
    }

    public Vector2 next() {
        return moves.iterator()
                    .hasNext() ? moves.iterator()
                                      .next() : null;
    }

    private boolean traverse(Vector2 tile, Vector2[] forbiddenSides) {
        if (!isValid(tile)) return false;

        if (isEnd(tile)) {
//            moves.add(tile);
            return true;
        } else {
            tried.add(tile);
        }

        Map<Vector2, Float> distances = new LinkedHashMap<>();
        List<Vector2> queue = new LinkedList<>();
        List<Vector2> restrictions = new LinkedList<>();

        restrictions.addAll(Arrays.asList(forbiddenSides));

        if (pathFinder.restrictions.containsKey(tile.copy()))
            restrictions.addAll(Arrays.asList(pathFinder.restrictions.get(tile.copy())));

        if (!restrictions.contains(Vector2.LEFT())) {
            distances.put(tile.copy()
                              .add(Vector2.LEFT()), end.distance(tile.copy()
                                                                     .add(Vector2.LEFT())));
        }
        if (!restrictions.contains(Vector2.RIGHT())) {
            distances.put(tile.copy()
                              .add(Vector2.RIGHT()), end.distance(tile.copy()
                                                                      .add(Vector2.RIGHT())));
        }
        if (!restrictions.contains(Vector2.DOWN())) {
            distances.put(tile.copy()
                              .add(Vector2.DOWN()), end.distance(tile.copy()
                                                                     .add(Vector2.DOWN())));
        }
        if (!restrictions.contains(Vector2.UP())) {
            distances.put(tile.copy()
                              .add(Vector2.UP()), end.distance(tile.copy()
                                                                   .add(Vector2.UP())));
        }

        distances.entrySet()
                 .stream()
                 .sorted((o1, o2) -> Float.compare(o1.getValue(), o2.getValue()))
                 .forEachOrdered(vector2FloatEntry -> queue.add(vector2FloatEntry.getKey()));

        for (Vector2 vector2 : queue) {
            List<Vector2> newForbiddens = new LinkedList<>();
//            if (pathFinder.fixForbiddenSidesUntilSideChange) {
//                for (Vector2 forbiddenSide : forbiddenSides) {
//                    if ((forbiddenSide.equals(Vector2.LEFT()) && vector2.equals(Vector2.RIGHT())) ||
//                        (forbiddenSide.equals(Vector2.RIGHT()) && vector2.equals(Vector2.LEFT())) ||
//                        (forbiddenSide.equals(Vector2.UP()) && vector2.equals(Vector2.DOWN())) ||
//                        (forbiddenSide.equals(Vector2.DOWN()) && vector2.equals(Vector2.UP()))) {
//                        newForbiddens.add(forbiddenSide);
//                    }
//                }
//            }
            if (traverse(vector2, newForbiddens.toArray(new Vector2[0]))) {
                moves.add(0, vector2);
                return true;
            }
        }

        return false;
    }

    private boolean traverse(Vector2 tile) {
        return traverse(tile, new Vector2[0]);
    }

    private boolean isEnd(Vector2 tile) {
        return tile.x == end.x && tile.y == end.y;
    }

    private boolean isValid(Vector2 tile) {
        return inRange(tile) && isOpen(tile) && !isTried(tile);
    }

    private boolean isOpen(Vector2 tile) {
        HitPoint hitPoint = RayCaster.castPoint(HitPoint.class, tile.copy()
                                                                    .scale(pathFinder.tileSize.copy())
                                                                    .add(pathFinder.tileSize.copy()
                                                                                            .scale(new Vector2(0.5f,
                                                                                                               0.5f))));
        return (hitPoint == null && !pathFinder.triggerPathsOnly) || (hitPoint != null && hitPoint.isTrigger());
    }

    private boolean isTried(Vector2 tile) {
        return tried.contains(tile);
    }

    private boolean inRange(Vector2 tile) {
        Vector2 pos = tile.copy()
                          .scale(pathFinder.tileSize.copy())
                          .add(pathFinder.tileSize.copy()
                                                  .scale(new Vector2(0.5f, 0.5f)));
        return pos.x >= 0f && pos.x <= pathFinder.range.x && pos.y >= 0f && pos.y <= pathFinder.range.y;
    }
}
