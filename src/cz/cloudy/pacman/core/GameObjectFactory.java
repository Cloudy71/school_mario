package cz.cloudy.pacman.core;

import java.lang.reflect.InvocationTargetException;

public class GameObjectFactory {
    private GameObjectCollector gameObjectCollector;
    private GameObjectHelper    gameObjectHelper;

    public GameObjectFactory(GameObjectCollector gameObjectCollector, GameObjectHelper gameObjectHelper) {
        this.gameObjectCollector = gameObjectCollector;
        this.gameObjectHelper = gameObjectHelper;
    }

    public <T extends GameObject> T createObject(Class<? extends GameObject> clazz) {
        try {
            gameObjectCollector.requestNewId();
            T t = (T) clazz.getDeclaredConstructor().newInstance();
            addObject(t);
            return t;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds new GameObject into {@link GameObjectCollector}
     *
     * @param gameObject GameObject which should be added
     */
    private void addObject(GameObject gameObject) {
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
