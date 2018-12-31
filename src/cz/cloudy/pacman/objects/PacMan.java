/*
  User: Cloudy
  Date: 26-Nov-18
  Time: 19:26
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.*;
import cz.cloudy.fxengine.io.Keyboard;
import cz.cloudy.fxengine.physics.HitPoint;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.physics.RayCaster;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.fxengine.utils.ImageUtils;
import cz.cloudy.pacman.Main;
import cz.cloudy.pacman.scenes.GameScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PacMan
        extends GameObject {

    private int     side;
    private int     frame;
    private int     frameSide;
    private int     score;
    private int     plannedSide;
    private Vector2 moveTile, oldMoveTile;
    private List<Vector2> moveQueue;
    private Vector2       targetTile;
    private boolean       nextTile;
    private int           animationId;
    private int           timerId;
    private int           timeToReach;
    private boolean       dead;

    private Image[][] sprites = new Image[4][3];

    public static final int SIDE_RIGHT      = 0;
    public static final int SIDE_DOWN       = 1;
    public static final int SIDE_LEFT       = 2;
    public static final int SIDE_UP         = 3;
    public static final int SIDE_FRIGHTENED = 4;

    private long lastFrameChange;
    private long frameChangeInterval = 50 * 1_000_000;

    public Vector2 getSideVector() {
        switch (side) {
            case SIDE_RIGHT:
                return Vector2.RIGHT();
            case SIDE_DOWN:
                return Vector2.DOWN();
            case SIDE_LEFT:
                return Vector2.LEFT();
            case SIDE_UP:
                return Vector2.UP();
        }
        return Vector2.ZERO();
    }

    @Override
    public void create() {
        this.frame = 0;
        this.side = 0;
        this.frameSide = 0;
        this.lastFrameChange = Renderer.get()
                                       .getTime();
        this.score = 0;
        this.plannedSide = -1;
        this.moveTile = Vector2.ZERO();
        this.animationId = -1;
        this.timerId = -1;
        this.timeToReach = 250;
        this.nextTile = true;
        this.dead = false;

        this.moveQueue = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                sprites[i][j] = Main.tileset.getPart(new Int2(i * 3 + j, 21));
            }
        }

        if (Renderer.get()
                    .getGameScene() == GameScene.class) {
            setScale(new Vector2(2f, 2f));
            PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
            physicsData.setScalable(false);
            physicsData.setSolid(true);
            physicsData.setParent(this);
            setPhysicsData(physicsData);
        } else {
            for (int i = 0; i < 3; i++) {
                sprites[SIDE_RIGHT][i] = ImageUtils.getImagePart(Main.tileset.getSource(), new Int2(128 + 32 * i, 256),
                                                                 new Int2(32, 32));
            }
        }
        setPivot(Pivot.CENTER());
    }

    private void checkForCoin() {
        GameObject[] gameObjects = RayCaster.castPoint(GameObject[].class, getPosition().copy());
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Coin) {
                if (((Coin) gameObject).getType() == 1) {
                    for (Ghost ghost : Renderer.get()
                                               .getGameObjectCollector()
                                               .getGameObjectsOfType(Ghost.class)) {
                        ghost.changeMode(Ghost.GhostMode.FRIGHTENED);
                    }
                }
                this.score += 10;
                gameObject.destroy();
            }
        }

        int count = Renderer.get()
                            .getGameObjectCollector()
                            .getGameObjectsOfType(Coin.class)
                            .size();
        if (count == 0) {
            killGhosts();
        }
    }

    private void killGhosts() {
        for (Ghost ghost : Renderer.get()
                                   .getGameObjectCollector()
                                   .getGameObjectsOfType(Ghost.class)) {
            ghost.destroy();
        }
    }

    @Override
    public void update() {
        setSprite(sprites[side][frame]);
        if (Renderer.get()
                    .getTime() >= lastFrameChange + frameChangeInterval) {
            lastFrameChange = Renderer.get()
                                      .getTime();
            frame = (frameSide == 0) ? frame - 1 : frame + 1;
            if (frame > 2 || frame < 0) {
                frame = frame < 0 ? 0 : 2;
                frameSide = frameSide == 0 ? 1 : 0;
            }
        }

        if ((Keyboard.isKeyPress(KeyCode.A) || Keyboard.isKeyPress(KeyCode.LEFT) && side != SIDE_LEFT)) {
            plannedSide = SIDE_LEFT;
        }
        if ((Keyboard.isKeyPress(KeyCode.D) || Keyboard.isKeyPress(KeyCode.RIGHT) && side != SIDE_RIGHT)) {
            plannedSide = SIDE_RIGHT;
        }
        if ((Keyboard.isKeyPress(KeyCode.W) || Keyboard.isKeyPress(KeyCode.UP) && side != SIDE_UP)) {
            plannedSide = SIDE_UP;
        }
        if ((Keyboard.isKeyPress(KeyCode.S) || Keyboard.isKeyPress(KeyCode.DOWN) && side != SIDE_DOWN)) {
            plannedSide = SIDE_DOWN;
        }

        if (plannedSide != -1 && moveQueue.size() == 0) {
            side = plannedSide;
            plannedSide = -1;
        }

        if (moveQueue.size() == 0 && nextTile) {
            checkForCoin();
            Vector2 point = getTileByPosition().copy()
                                               .add(getSideVector())
                                               .scale(new Vector2(32f, 32f))
                                               .add(new Vector2(16f, 16f));
            HitPoint hitPoint = RayCaster.castPoint(HitPoint.class, point);
            if (hitPoint != null && hitPoint.isTrigger()) {
                moveQueue.add(point.subtract(new Vector2(16f, 16f))
                                   .scale(new Vector2(1f / 32f, 1f / 32f)));
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

    private void setGhostsMode(Ghost.GhostMode ghostsMode) {
        for (Ghost ghost : Renderer.get()
                                   .getGameObjectCollector()
                                   .getGameObjectsOfType(Ghost.class)) {
            ghost.changeMode(ghostsMode);
        }
    }

    @Override
    protected void dispose() {

    }

    @Override
    public void fixedUpdate() {

    }

    public void kill() {
        dead = true;
        killGhosts();
    }
}
