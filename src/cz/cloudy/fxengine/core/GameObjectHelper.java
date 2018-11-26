package cz.cloudy.fxengine.core;

public class GameObjectHelper {
    private GameObjectCollector gameObjectCollector;

    /**
     * Generates {@link GameObjectHelper} which helps with object findings, etc..
     *
     * @param gameObjectCollector GameObjectCollector for cooperation
     */
    public GameObjectHelper(GameObjectCollector gameObjectCollector) {
        this.gameObjectCollector = gameObjectCollector;
        gameObjectCollector.setGameObjectHelper(this);
    }

    /**
     * Finds GameObject by specified id.
     *
     * @param id ID of desired object
     * @return GameObject by specified id
     */
    public GameObject findById(int id) {
        for (GameObject gameObject : gameObjectCollector.getGameObjects()) {
            if (gameObject.getId() == id) {
                return gameObject;
            }
        }

        return null;
    }
}
