package cz.cloudy.pacman.core;

import cz.cloudy.pacman.interfaces.GameObject;

public class GameObjectFactory {
    private GameObjectCollector gameObjectCollector;
    private GameObjectHelper    gameObjectHelper;

    public GameObjectFactory(GameObjectCollector gameObjectCollector, GameObjectHelper gameObjectHelper) {
        this.gameObjectCollector = gameObjectCollector;
        this.gameObjectHelper = gameObjectHelper;
    }

    /**
     * Adds new GameObject into {@link GameObjectCollector}
     *
     * @param gameObject GameObject which should be added
     */
    public void addObject(GameObject gameObject) {
        gameObjectCollector.addGameObject(gameObject);
        gameObject.create();
    }

    /**
     * Disposes specified GameObject.
     *
     * @param gameObject GameObject which should be disposed
     */
    public void disposeObject(GameObject gameObject) {
        gameObjectCollector.removeGameObject(gameObject);
    }
}
