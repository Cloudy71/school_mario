/*
  User: Cloudy
  Date: 27-Dec-18
  Time: 22:15
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.*;
import cz.cloudy.fxengine.physics.ComputedPath;
import cz.cloudy.fxengine.physics.PathFinder;
import cz.cloudy.fxengine.physics.RayCaster;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.pacman.Main;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Ghost
        extends GameObject {
    public enum GhostMode {
        WAIT, CHASE, SCATTER, FRIGHTENED
    }

    protected int ghostId;

    protected int       side;
    protected Image[]   sprites = new Image[5];
    protected Image[]   eyes    = new Image[4];
    private   float     scale;
    protected GhostMode ghostMode;
    protected Vector2   scatterTile;
    protected Vector2   moveTile, oldMoveTile;
    protected List<Vector2> moveQueue;
    private   Vector2       targetTile;
    private   boolean       nextTile;
    protected PacMan        target;
    protected int           timeToReach;
    private   Vector2[]     thinkPoints;
    private   Vector2       lastThinkPoint;
    private   int           animationId;
    private   int           timerId;
    protected boolean       errorFindingPath;
    protected PathFinder    pathFinder;
    protected boolean       dead;
    private   int           deadTimer;

    @Override
    public void create() {
        this.side = 0;
        this.ghostMode = GhostMode.WAIT;
        this.moveTile = Vector2.ZERO();
        this.nextTile = false;
        this.moveQueue = new LinkedList<>();
        this.timeToReach = 250;
        this.thinkPoints = new Vector2[0];
        this.errorFindingPath = false;
        this.animationId = -1;
        this.timerId = -1;
        this.pathFinder = Main.pathFinder;
        this.dead = false;
        this.deadTimer = -1;

        if (Main.currentMap.getName()
                           .equals("main")) {
            this.thinkPoints = new Vector2[] {
                    new Vector2(2f, 3f),
                    new Vector2(5f, 1f),
                    new Vector2(5f, 3f),
                    new Vector2(5f, 5f),
                    new Vector2(7f, 3f),
                    new Vector2(9f, 3f),
                    new Vector2(11f, 3f),
                    new Vector2(13f, 3f),
                    new Vector2(15f, 3f),
                    new Vector2(18f, 3f),
                    new Vector2(15f, 1f),
                    new Vector2(5f, 9f),
                    new Vector2(7f, 9f),
                    new Vector2(13f, 9f),
                    new Vector2(15f, 9f),
                    new Vector2(13f, 11f),
                    new Vector2(13f, 13f),
                    new Vector2(15f, 13f),
                    new Vector2(13f, 15f),
                    new Vector2(15f, 15f),
                    new Vector2(7f, 11f),
                    new Vector2(7f, 13f),
                    new Vector2(5f, 13f),
                    new Vector2(3f, 17f),
                    new Vector2(17f, 17f),
                    new Vector2(9f, 19f),
                    new Vector2(11f, 19f),
                    new Vector2(5f, 15f),
                    new Vector2(7f, 15f),
                    new Vector2(9f, 15f),
                    new Vector2(11f, 15f),
                    new Vector2(15f, 5f),
                    new Vector2(9f, 7f),
                    new Vector2(11f, 7f)
            };
        }
    }

    protected void load() {
        for (int i = 0; i < 4; i++) {
            sprites[i] = Main.tileset.getPart(new Int2(i * 2, 16 + ghostId));
            eyes[i] = Main.tileset.getPart(new Int2(i, 20));
        }
        sprites[PacMan.SIDE_FRIGHTENED] = Main.tileset.getPart(new Int2(10, 19));

        this.scale = 0f;

        setPivot(Pivot.CENTER());
    }

    protected void modeScatter() {
        moveTile = Main.currentMap.getTile(scatterTile.copy());
        if (getDetailedTileByPosition().equals(moveTile)) {
            moveAroundWall();
        }
    }

    protected void modeFrightened() {
        modeScatter();
    }

    protected void modeChase() {

    }

    private void think() {
        if (ghostMode == GhostMode.SCATTER) {
            modeScatter();
        } else if (ghostMode == GhostMode.FRIGHTENED) {
            modeFrightened();
        } else if (ghostMode == GhostMode.CHASE && target != null) {
            modeChase();
        } else if (ghostMode == GhostMode.WAIT) {
            changeMode(GhostMode.SCATTER);
        }
    }

    private void pacManTouch() {
        if (target == null) return;
        Vector2 pos = GridUtils.getTileByGridRound(getPosition().copy(), new Vector2(32f, 32f));
        Vector2 pPos = GridUtils.getTileByGridRound(target.getPosition()
                                                          .copy(), new Vector2(32f, 32f));
        if (pos.equals(pPos)) {
            if (ghostMode == GhostMode.CHASE || ghostMode == GhostMode.SCATTER) target.kill();
            else {
                dead = true;
                deadTimer = TimerService.setTimer(5000, () -> {
                    dead = false;
                });
            }
        }
    }

    public void changeMode(GhostMode mode) {
        // Find nearest think point (just to change ghost's direction)
        Vector2 nearest = null;
        int dist = Integer.MAX_VALUE;
        if (thinkPoints.length > 0) {
            for (Vector2 thinkPoint : thinkPoints) {
                int moves = pathFinder.computePath(getTileByPosition(), Main.currentMap.getTile(thinkPoint))
                                      .optimize()
                                      .moves().length;
                if (moves < dist) {
                    dist = moves;
                    nearest = thinkPoint;
                }
            }
            moveTile = Main.currentMap.getTile(nearest.copy());
        }
        ghostMode = mode;
    }

    protected void moveAroundWall() {

        moveTile = getTileByPosition();
        oldMoveTile = getDetailedTileByPosition();
        nextTile = true;
        Vector2[] priorityArray = new Vector2[] {
                Vector2.UP(),
                Vector2.DOWN(),
                Vector2.LEFT(),
                Vector2.RIGHT(),
                Vector2.UP_LEFT(),
                Vector2.UP_RIGHT(),
                Vector2.DOWN_LEFT(),
                Vector2.DOWN_RIGHT()
        };
        WallTile[] wallTiles = null;
        for (Vector2 vector2 : priorityArray) {
            GameObject gameObject = RayCaster.castPoint(GameObject.class, getTileByPosition().copy()
                                                                                             .add(vector2)
                                                                                             .scale(new Vector2(32f,
                                                                                                                32f))
                                                                                             .add(new Vector2(16f,
                                                                                                              16f)));
            if (gameObject == null) continue;
            if (gameObject instanceof WallTile) {
                WallTile wallTile = (WallTile) gameObject;
                if (!wallTile.hasMovesOnEdges()) continue;
                wallTiles = wallTile.findEdges();
                break;
            }
        }

        Vector2[] side = new Vector2[] {
                Vector2.UP().copy(),
                Vector2.LEFT().copy(),
                Vector2.DOWN().copy(),
                Vector2.RIGHT().copy()
        };
        moveQueue.clear();
        Vector2 start = getTileByPosition().copy();
        Vector2 begin = start.copy();
        moveQueue.add(begin);
        for (int i = 0; i < wallTiles.length + 1; i++) {
            Vector2 pos;
            if (i < 4) {
                MoveTile moveTile = (MoveTile) RayCaster.castPoint(GameObject.class, wallTiles[i].getPosition()
                                                                                                 .copy()
                                                                                                 .add(new Vector2(16f,
                                                                                                                  16f))
                                                                                                 .add(side[i].copy()
                                                                                                             .scale(new Vector2(
                                                                                                                     32f,
                                                                                                                     32f))));
                pos = moveTile.getPosition()
                              .copy()
                              .scale(new Vector2(1 / 32f, 1 / 32f));
            } else {
                pos = begin.copy();
            }

            Vector2[] moves = pathFinder.computePath(start, pos)
                                        .moves();
            if (moves.length > 0) {
                start = moves[moves.length - 1];
            }
            moveQueue.addAll(Arrays.asList(moves));
        }
        moveQueue.add(begin);
    }

    protected boolean checkExistingPath() {
        ComputedPath computedPath = pathFinder.computePath(getTileByPosition(), moveTile);
        return computedPath.isFound();
    }

    @Override
    public void update() {
        if (ghostMode == GhostMode.FRIGHTENED && !dead) side = PacMan.SIDE_FRIGHTENED;
        if (dead) {
            if (side >= 4) side = 0;
            setSprite(eyes[side]);
            Vector2 point = null;
            for (MoveTile tile : Renderer.get()
                                         .getGameObjectCollector()
                                         .getGameObjectsOfType(MoveTile.class)) {
                if (tile.getType() == 2) {
                    point = GridUtils.getTileByGrid(tile.getPosition()
                                                        .copy()
                                                        .subtract(new Vector2(16f, 16f)), new Vector2(32f, 32f));
                    if (getTileByPosition().equals(point)) {
                        dead = false;
                        ghostMode = GhostMode.CHASE;
                        TimerService.killTimer(deadTimer);
                    }
                }
            }
            if (dead) {
                moveTile = point;
            }
        } else {
            setSprite(sprites[side]);
        }
        setScale(new Vector2(scale, scale));
        pacManTouch();
        if (target == null && Renderer.get()
                                      .getGameObjectCollector()
                                      .getGameObjectsOfType(PacMan.class)
                                      .size() > 0) {
            target = Renderer.get()
                             .getGameObjectCollector()
                             .getGameObjectsOfType(PacMan.class)
                             .iterator()
                             .next();
            think();
        }

        if (thinkPoints.length > 0) {
            for (Vector2 thinkPoint : thinkPoints) {
                if (getDetailedTileByPosition().equals(Main.currentMap.getTile(thinkPoint)) &&
                    !getDetailedTileByPosition().equals(lastThinkPoint)) {
                    think();
                    lastThinkPoint = getDetailedTileByPosition();
                }
            }
        }
        if ((moveQueue.size() == 0) || thinkPoints.length == 0) {
            think();
        }

        if (!moveTile.equals(oldMoveTile)) {
            moveQueue.clear();
            ComputedPath computedPath = pathFinder.computePath(getTileByPosition(), moveTile);
            errorFindingPath = !computedPath.isFound();
            moveQueue.addAll(Arrays.asList(computedPath.moves()));
            nextTile = true;
            AnimationService.killAnimation(animationId);
            TimerService.executeTimer(timerId);
            if ((errorFindingPath || moveQueue.size() == 0) && ghostMode == GhostMode.CHASE) {
                lastThinkPoint = Vector2.ZERO();
            }
        }
        if (nextTile && moveQueue != null && moveQueue.size() > 0) {
            Vector2 diff = moveQueue.get(0)
                                    .copy()
                                    .subtract(getTileByPosition().copy());
            if (diff.x > 0f) side = PacMan.SIDE_RIGHT;
            else if (diff.x < 0f) side = PacMan.SIDE_LEFT;
            else if (diff.y > 0f) side = PacMan.SIDE_DOWN;
            else if (diff.y < 0f) side = PacMan.SIDE_UP;

            int time = timeToReach * (int) getTileByPosition().distance(moveQueue.get(0));
            animationId = AnimationService.beginAnimation(this);
            AnimationService.addKeyFrame(time, KeyFrame.KeyFrameType.MOVE, new NormalValue(moveQueue.get(0)
                                                                                                    .copy()
                                                                                                    .scale(new Vector2(
                                                                                                            32f, 32f))
                                                                                                    .add(new Vector2(
                                                                                                            16f,
                                                                                                            16f))));
            AnimationService.endAnimation();
            moveQueue.remove(0);
            timerId = TimerService.setTimer(time, () -> nextTile = true);
            nextTile = false;
        }


        oldMoveTile = moveTile.copy();
    }

    public Vector2 getTileByPosition() {
        return GridUtils.getTileByGrid(getPosition().copy(), new Vector2(32f, 32f));
    }

    public Vector2 getDetailedTileByPosition() {
        return getPosition().copy()
                            .subtract(new Vector2(16f, 16f))
                            .scale(new Vector2(1 / 32f, 1 / 32f));
    }

    @Override
    protected void dispose() {

    }

    @Override
    public void render() {
//        Color c = Color.RED;
//        if (ghostId == 1) c = Color.BLUE;
//        else if (ghostId == 2) c = Color.PINK;
//        else if (ghostId == 3) c = Color.ORANGE;
//        Render r = Render.begin();
//        r.rect()
//         .setPosition(moveTile.copy()
//                              .scale(new Vector2(32f, 32f)))
//         .setSize(new Vector2(32f, 32f))
//         .setPaint(c)
//         .end();
//        r.finish();
        super.render();
    }
}
