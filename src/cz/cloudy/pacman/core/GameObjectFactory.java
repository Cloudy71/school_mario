package cz.cloudy.pacman.core;

public class GameObjectFactory {
    private GameObjectCollector gameObjectCollector;
    private GameObjectHelper    gameObjectHelper;

    public GameObjectFactory(GameObjectCollector gameObjectCollector,
                             GameObjectHelper gameObjectHelper) {
        this.gameObjectCollector = gameObjectCollector;
        this.gameObjectHelper = gameObjectHelper;
    }
}
