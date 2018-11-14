package cz.cloudy.pacman.core;

import cz.cloudy.pacman.interfaces.GameObject;

import java.util.LinkedList;
import java.util.List;

public class GameObjectCollector {
    private static List<GameObject> gameObjects;
    private static IdFactory        idFactory;
    private        GameObjectHelper gameObjectHelper;

    static {
        gameObjects = new LinkedList<>();
        idFactory = new IdFactory();
    }

    protected void setGameObjectHelper(GameObjectHelper gameObjectHelper) {
        this.gameObjectHelper = gameObjectHelper;
    }

    /**
     * Adds new GameObject into {@link GameObjectCollector}
     *
     * @param gameObject GameObject which should be added
     */
    public void addObject(GameObject gameObject) {

    }

    /**
     * Disposes specified GameObject.
     *
     * @param gameObject GameObject which should be disposed
     */
    public void disposeObject(GameObject gameObject) {

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

    /**
     * Returns {@link IdFactory} which contains new requested ID (if was requested else returns null).
     *
     * @return IdFactory or null
     */
    public static final IdFactory getRequestedId() {
        return IdFactory.requested ? idFactory : null;
    }
}
