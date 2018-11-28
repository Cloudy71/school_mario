package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.interfaces.IGame;
import cz.cloudy.fxengine.io.KeyboardController;
import cz.cloudy.fxengine.physics.HitBox;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.surface.Surface;
import cz.cloudy.fxengine.surface.SurfaceAccessor;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Renderer {
    public static Renderer instance;

    private GameObjectCollector gameObjectCollector;
    private GameObjectHelper    gameObjectHelper;
    private GameObjectFactory   gameObjectFactory;

    private Surface        surface;
    private Surface        targetSurface;
    private List<IGame>    gameHandlers;
    private AnimationTimer animationTimer;

    private int     currentFramerate;
    private int     framerate;
    private long    lastFramerateCheck;
    private long    lastFixedTime;
    private boolean fixedSection;
    private int     currentFixedFramerate;
    private int     fixedFramerate;
    private long    lastFramerateFixedCheck;
    private boolean debugMode;

    /**
     * Will create new {@link Surface}.
     * This {@link Surface} is main render target.
     */
    private Renderer() {
        gameObjectCollector = new GameObjectCollector();
        gameObjectHelper = new GameObjectHelper(gameObjectCollector);
        gameObjectFactory = new GameObjectFactory(gameObjectCollector, gameObjectHelper);

        instance = this;
        surface = new Surface(new Vector2(Main.scene.getWidth(), Main.scene.getHeight()));
        surface.setTarget();

        debugMode = true;

        ((Group) Main.scene.getRoot()).getChildren()
                                      .add(SurfaceAccessor.getCanvas(surface));

        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.PRESSED, event.getCode());
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.PRESS, event.getCode());
        });

        Main.scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.RELEASED, event.getCode());
            KeyboardController.removeKey(KeyboardController.KeyboardKeyType.PRESSED, event.getCode());
            KeyboardController.removeKey(KeyboardController.KeyboardKeyType.PRESS, event.getCode());
        });

//        Main.scene.addEventHandler(KeyEvent.KEY_TYPED, event -> {
//
//        });

        Main.scene.addEventHandler(MouseEvent.ANY, event -> {

        });

        GraphicsContext gc = SurfaceAccessor.getGraphicsContext(surface);

        lastFramerateCheck = 0;
        gameHandlers = new LinkedList<>();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameHandlers == null) return;
                if (lastFixedTime == 0) lastFixedTime = now;

                if (now >= lastFramerateCheck + 1_000_000_000) {
                    currentFramerate = framerate;
                    framerate = 0;
                    lastFramerateCheck = now;
                }
                else {
                    framerate++;
                }

                Render.lock();
                fixedSection = now >= lastFixedTime + 1_000_000_000 / 60;
                for (IGame gameHandler : gameHandlers) {
                    gameHandler.update();
                    if (fixedSection) gameHandler.fixedUpdate();
                }
                for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                    gameObject.update();
                    if (fixedSection) gameObject.fixedUpdate();
                }
                if (fixedSection) {
//                    lastFixedTime = now - (now - (lastFixedTime + 1_000_000_000 / 60));
//                    lastFixedTime = now;
                    lastFixedTime = lastFixedTime + (1_000_000_000 /
                                                     60); // TODO: Better FixedUpdate calculations (moving around 58 ~ 62 fps).
                    KeyboardController.synchronizeKeys();

                    if (now >= lastFramerateFixedCheck + 1_000_000_000) {
                        currentFixedFramerate = fixedFramerate;
                        fixedFramerate = 0;
                        lastFramerateFixedCheck = now;
                    }
                    else {
                        fixedFramerate++;
                    }
                }
                KeyboardController.removeReleased();
                Render.unlock();

                for (IGame gameHandler : gameHandlers) {
                    gameHandler.render();
                }

                // TODO: Object rendering.
                for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                    gameObject.render();
                }

                for (IGame gameHandler : gameHandlers) {
                    gameHandler.aboveRender();
                }

                if (debugMode) { // Render physics data
                    gc.setStroke(Color.LIME);
                    for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                        PhysicsData physicsData = gameObject.getPhysicsData();
                        if (physicsData == null)
                            continue;

                        for (HitBox hitBox : physicsData.getHitBoxes()) {
                            Vector2[] bounds = hitBox.getBounds();
                            double[] xPoints = new double[5];
                            double[] yPoints = new double[5];
                            for (int i = 0; i < bounds.length; i++) {
                                xPoints[i] = gameObject.getAbsolutePosition().x + bounds[i].x;
                                yPoints[i] = gameObject.getAbsolutePosition().y + bounds[i].y;
                            }
                            xPoints[4] = gameObject.getAbsolutePosition().x + bounds[0].x;
                            yPoints[4] = gameObject.getAbsolutePosition().y + bounds[0].y;
                            gc.strokePolyline(xPoints, yPoints, 5);
                        }
                    }
                }
            }
        };
    }

    public static Renderer get() {
        return instance == null ? new Renderer() : instance;
    }

    /**
     * Sets the current render target. Every render call will be rendered into this surface.
     *
     * @param surface Desired surface to be content rendered into
     */
    public void setRenderTarget(Surface surface) {
        this.targetSurface = surface;
        SurfaceAccessor.setRedrawFlag(surface);
    }

    /**
     * Resets the target surface to the default main surface.
     */
    public void resetRenderTarget() {
        this.targetSurface = surface;
    }

    protected Surface getCurrentTarget() {
        return this.targetSurface;
    }

    public void addGameHandler(IGame gameHandler) {
        this.gameHandlers.add(gameHandler);
        gameHandler.start();
        this.animationTimer.start();
    }

    public boolean isFixedSection() {
        return this.fixedSection;
    }

    public int getFramerate() {
        return currentFramerate;
    }

    public int getFixedFramerate() {
        return currentFixedFramerate;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public GameObjectCollector getGameObjectCollector() {
        return gameObjectCollector;
    }

    public GameObjectHelper getGameObjectHelper() {
        return gameObjectHelper;
    }

    public GameObjectFactory getGameObjectFactory() {
        return gameObjectFactory;
    }
}
