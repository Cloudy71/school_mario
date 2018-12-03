package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.interfaces.IGameScene;
import cz.cloudy.fxengine.io.KeyboardController;
import cz.cloudy.fxengine.io.MouseController;
import cz.cloudy.fxengine.physics.HitPoint;
import cz.cloudy.fxengine.physics.PhysicsData;
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

    private Surface          surface;
    private Surface          targetSurface;
    private List<IGameScene> gameScenes;
    private IGameScene       gameScene;
    private AnimationTimer   animationTimer;

    private int     currentFramerate;
    private int     framerate;
    private long    lastFramerateCheck;
    private long    lastFixedTime;
    private boolean fixedSection;
    private boolean executingFixed;
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

        executingFixed = false;
        debugMode = false;

        ((Group) Main.scene.getRoot()).getChildren()
                                      .add(surface.getCanvas());

        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.PRESSED, event.getCode());
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.PRESS, event.getCode());
            System.out.println(event.getCode());
            // TODO: Is working continuously. Add another list where we will check if it's continuous press.
        });

        Main.scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            KeyboardController.addKey(KeyboardController.KeyboardKeyType.RELEASED, event.getCode());
            KeyboardController.removeKey(KeyboardController.KeyboardKeyType.PRESSED, event.getCode());
            KeyboardController.removeKey(KeyboardController.KeyboardKeyType.PRESS, event.getCode());
        });

//        Main.scene.addEventHandler(KeyEvent.KEY_TYPED, event -> {
//
//        });

        Main.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            MouseController.addAction(MouseController.MouseActionType.PRESSED, event.getButton());
            MouseController.addAction(MouseController.MouseActionType.PRESS, event.getButton());
        });

        Main.scene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            MouseController.addAction(MouseController.MouseActionType.RELEASED, event.getButton());
            MouseController.removeAction(MouseController.MouseActionType.PRESSED, event.getButton());
            MouseController.removeAction(MouseController.MouseActionType.PRESS, event.getButton());
        });

        Main.scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            MouseController.setPosition(new Vector2(event.getX(), event.getY()));
        });

        GraphicsContext gc = surface.getGraphicsContext();

        lastFramerateCheck = 0;
        gameScenes = new LinkedList<>();
        gameScene = null;
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameScene == null) return;
                if (lastFixedTime == 0) lastFixedTime = now;

                if (now >= lastFramerateCheck + 1_000_000_000) {
                    currentFramerate = framerate;
                    framerate = 0;
                    lastFramerateCheck = now;
                } else {
                    framerate++;
                }

                Render.lock();
                fixedSection = now >= lastFixedTime + 1_000_000_000 / 60;
                gameScene.update();
                if (fixedSection) {
                    executingFixed = true;
                    gameScene.fixedUpdate();
                    executingFixed = false;
                }
                for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                    gameObject.update();
                    if (fixedSection) {
                        executingFixed = true;
                        gameObject.fixedUpdate();
                        executingFixed = false;
                    }
                }
                if (fixedSection) {
//                    lastFixedTime = now - (now - (lastFixedTime + 1_000_000_000 / 60));
//                    lastFixedTime = now;
                    lastFixedTime = lastFixedTime + (1_000_000_000 /
                                                     60); // TODO: Better FixedUpdate calculations (moving around 58 ~ 62 fps).
                    KeyboardController.synchronizeKeys();
                    MouseController.synchronizeActions();

                    if (now >= lastFramerateFixedCheck + 1_000_000_000) {
                        currentFixedFramerate = fixedFramerate;
                        fixedFramerate = 0;
                        lastFramerateFixedCheck = now;
                    } else {
                        fixedFramerate++;
                    }
                }
                KeyboardController.removeReleased();
                KeyboardController.removePressed();
                MouseController.removeReleased();
                MouseController.removePressed();
                Render.unlock();

                gameScene.render();

                // TODO: Object rendering.
                for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                    gameObject.render();
                }

                gameScene.aboveRender();

                if (debugMode) { // Render physics data
                    for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
                        PhysicsData physicsData = gameObject.getPhysicsData();
                        if (physicsData == null) continue;

                        for (HitPoint hitPoint : physicsData.getHitPoints()) {
                            Vector2[] bounds = hitPoint.getPolygons();
                            double[] xPoints = new double[4];
                            double[] yPoints = new double[4];
                            for (int i = 0; i < bounds.length; i++) {
                                xPoints[i] = gameObject.getAbsolutePosition().x + bounds[i].x;
                                yPoints[i] = gameObject.getAbsolutePosition().y + bounds[i].y;
                            }
                            xPoints[3] = gameObject.getAbsolutePosition().x + bounds[0].x;
                            yPoints[3] = gameObject.getAbsolutePosition().y + bounds[0].y;
                            if (hitPoint.isSolid()) gc.setStroke(Color.LIME);
                            else if (hitPoint.isTrigger()) gc.setStroke(Color.PINK);
                            else gc.setStroke(Color.YELLOW);
                            gc.strokePolyline(xPoints, yPoints, 4);
                        }
                    }
                }
            }
        };
        animationTimer.start();
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
        surface.setRedrawFlag();
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

    public int addGameScene(IGameScene gameScene) {
        if (this.gameScenes.contains(gameScene)) return this.gameScenes.indexOf(gameScene);
        this.gameScenes.add(gameScene);
        return this.gameScenes.size() - 1;
    }

    public void setGameScene(int gameSceneId) {
        if (this.gameScene != null) this.gameScene.end();
        this.gameScene = (gameSceneId < this.gameScenes.size()) ? gameScenes.get(gameSceneId) : null;
        if (this.gameScene != null) this.gameScene.start();
    }

    public void setGameScene(Class<? extends IGameScene> gameSceneClass) {
        for (int i = 0; i < this.gameScenes.size(); i++) {
            if (this.gameScenes.get(i)
                               .getClass() == gameSceneClass) {
                setGameScene(i);
                return;
            }
        }
    }

    public Class<? extends IGameScene> getGameScene() {
        return this.gameScene != null ? this.gameScene.getClass() : null;
    }

    public boolean isFixedSection() {
        return this.executingFixed;
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
