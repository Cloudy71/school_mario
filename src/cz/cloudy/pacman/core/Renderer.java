package cz.cloudy.pacman.core;

import cz.cloudy.pacman.Main;
import cz.cloudy.pacman.interfaces.IGame;
import cz.cloudy.pacman.surface.Surface;
import cz.cloudy.pacman.surface.SurfaceAccessor;
import cz.cloudy.pacman.types.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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

    private int  currentFramerate;
    private int  framerate;
    private long lastFramerateCheck;

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

        ((Group) Main.scene.getRoot()).getChildren()
                                      .add(SurfaceAccessor.getCanvas(surface));

        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

        });

        Main.scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {

        });

        Main.scene.addEventHandler(KeyEvent.KEY_TYPED, event -> {

        });

        Main.scene.addEventHandler(MouseEvent.ANY, event -> {

        });

        lastFramerateCheck = 0;
        gameHandlers = new LinkedList<>();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameHandlers == null) return;

                if (now >= lastFramerateCheck + 1_000_000_000) {
                    currentFramerate = framerate;
                    framerate = 0;
                    lastFramerateCheck = now;
                } else {
                    framerate++;
                }

                for (IGame gameHandler : gameHandlers) {
                    Render.lock();
                    gameHandler.update();
                    Render.unlock();
                    gameHandler.render();
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

    public int getFramerate() {
        return currentFramerate;
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
