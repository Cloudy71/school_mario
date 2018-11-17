package cz.cloudy.pacman.core;

import cz.cloudy.pacman.interfaces.GameObject;

import java.util.LinkedList;
import java.util.List;

public class GameObjectCollector {
    private static List<GameObject> gameObjects;
    private static IdFactory        idFactory;

    static {
        gameObjects = new LinkedList<>();
        idFactory = new IdFactory();
    }

    private GameObjectHelper gameObjectHelper;

    protected void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    protected void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    /**
     * Returns {@link IdFactory} which contains new requested ID (if was requested else returns null).
     *
     * @return IdFactory or null
     */
    public static final IdFactory getRequestedId() {
        return IdFactory.requested ? idFactory : null;
    }

    protected void setGameObjectHelper(GameObjectHelper gameObjectHelper) {
        this.gameObjectHelper = gameObjectHelper;
    }

    /**
     * Returns all {@link GameObject} in scene.
     *
     * @return Array of {@link GameObject} in scene.
     */
    public GameObject[] getGameObjects() {
        return gameObjects.toArray(new GameObject[gameObjects.size()]);
    }

    /**
     * Requests new ID for object from {@link IdFactory}.
     */
    public void requestNewId() {
        idFactory.requestId();
    }
}
