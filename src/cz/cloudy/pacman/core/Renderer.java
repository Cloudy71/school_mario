package cz.cloudy.pacman.core;

import cz.cloudy.pacman.Main;
import cz.cloudy.pacman.surface.Surface;
import cz.cloudy.pacman.surface.SurfaceAccessor;
import cz.cloudy.pacman.types.Vector2;
import javafx.scene.Group;

public class Renderer {
    public static Renderer instance;

    private GameObjectCollector gameObjectCollector;
    private GameObjectHelper    gameObjectHelper;
    private GameObjectFactory   gameObjectFactory;

    private Surface surface;
    private Surface targetSurface;

    /**
     * Will create new {@link Surface}.
     * This {@link Surface} is main render target.
     */
    public Renderer() {
        gameObjectCollector = new GameObjectCollector();
        gameObjectHelper = new GameObjectHelper(gameObjectCollector);
        gameObjectFactory = new GameObjectFactory(gameObjectCollector,
                                                  gameObjectHelper);

        instance = this;
        surface = new Surface(new Vector2(Main.scene.getWidth(),
                                          Main.scene.getHeight()));
        surface.setTarget();

        ((Group) Main.scene.getRoot()).getChildren().add(SurfaceAccessor.getCanvas(surface));
    }

    /**
     * Sets the current render target. Every render call will be rendered into this surface.
     *
     * @param surface Desired surface to be content rendered into
     */
    public void setRenderTarget(Surface surface) {
        this.targetSurface = surface;
    }

    /**
     * Resets the target surface to the default main surface.
     */
    public void resetRenderTarget() {
        this.targetSurface = surface;
    }
}
