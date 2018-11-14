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

    public GameObject[] getGameObjects() {
        return gameObjects.toArray(new GameObject[gameObjects.size()]);
    }

    public void requestNewId() {
        idFactory.requestId();
    }

    public static final IdFactory getRequestedId() {
        return IdFactory.requested ? idFactory : null;
    }
}
