/*
  User: Cloudy
  Date: 27-Dec-18
  Time: 22:15
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.*;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.pacman.Main;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Ghost
        extends GameObject {
    public enum GhostMode {
        WAIT, CHASE, SCATTER, FRIGHTENED
    }

    protected int ghostId;

    protected int       side;
    protected Image[]   sprites = new Image[4];
    private   float     scale;
    protected GhostMode ghostMode;
    protected Vector2   scatterTile;
    protected Vector2   moveTile, oldMoveTile;
    private   List<Vector2> moveQueue;
    private   Vector2       targetTile;
    private   boolean       nextTile;
    protected PacMan        target;
    protected int           timeToReach;
    private   Vector2[]     thinkPoints;
    private   Vector2       lastThinkPoint;
    private   int           animationId;
    private   int           timerId;

    @Override
    public void create() {
        this.side = 0;
        this.ghostMode = GhostMode.WAIT;
        this.moveTile = Vector2.ZERO();
        this.nextTile = false;
        this.moveQueue = new LinkedList<>();
        this.timeToReach = 250;
        this.thinkPoints = new Vector2[0];

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
        }

        this.scale = 0f;

        setPivot(Pivot.CENTER());
    }

    protected void modeScatter() {
        moveTile = Main.currentMap.getTile(scatterTile.copy());
    }

    protected void modeFrightened() {

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

    protected void changeMode(GhostMode mode) {
        ghostMode = mode;
        // Find nearest think point (just to change ghost's direction)
        Vector2 nearest = null;
        int dist = Integer.MAX_VALUE;
        if (thinkPoints.length > 0) {
            for (Vector2 thinkPoint : thinkPoints) {
                int moves = Main.pathFinder.computePath(getTileByPosition(), Main.currentMap.getTile(thinkPoint))
                                           .optimize()
                                           .moves().length;
                if (moves < dist) {
                    dist = moves;
                    nearest = thinkPoint;
                }
            }
            moveTile = Main.currentMap.getTile(nearest.copy());
        }
    }

    @Override
    public void update() {
        setSprite(sprites[side]);
        setScale(new Vector2(scale, scale));
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
        } else {
            think();
        }

        if (!moveTile.equals(oldMoveTile)) {
            moveQueue.clear();
            moveQueue.addAll(Arrays.asList(Main.pathFinder.computePath(getTileByPosition(), moveTile)
                                                          .optimize()
                                                          .moves()));
            nextTile = true;
            AnimationService.killAnimation(animationId);
            TimerService.executeTimer(timerId);
            System.out.println("new move: " + moveTile);
        }
        if (nextTile && moveQueue != null && moveQueue.size() > 0) {
            int time = 250 * (int) getTileByPosition().distance(moveQueue.get(0));
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
}
