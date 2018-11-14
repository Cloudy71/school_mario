package cz.cloudy.pacman.core;

import cz.cloudy.pacman.interfaces.GameObject;

public class GameObjectHelper {
    private GameObjectCollector gameObjectCollector;

    public GameObjectHelper(GameObjectCollector gameObjectCollector) {
        this.gameObjectCollector = gameObjectCollector;
        gameObjectCollector.setGameObjectHelper(this);
    }

    public GameObject findById(int id) {
        for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
            if (gameObject.getId() == id) {
                return gameObject;
            }
        }

        return null;
    }
}
