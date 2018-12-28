package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.SerializationUtils;
import cz.cloudy.pacman.objects.MoveTile;
import cz.cloudy.pacman.objects.WallTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class GameMap
        implements Serializable {
    private static final long serialVersionUID = 23L;

    private String     name;
    private WallTile[] wallTiles;
    private MoveTile[] moveTiles;

    public GameMap(String name, WallTile[] wallTiles, MoveTile[] moveTiles) {
        this.name = name;
        this.wallTiles = wallTiles;
        this.moveTiles = moveTiles;
    }

    public String getName() {
        return name;
    }

    public WallTile[] getWallTiles() {
        return wallTiles;
    }

    public MoveTile[] getMoveTiles() {
        return moveTiles;
    }

    public void registerObjects() {
        for (WallTile wallTile : wallTiles) {
            GameObjectFactory.registerExistingObject(wallTile);
        }

        for (MoveTile moveTile : moveTiles) {
            GameObjectFactory.registerExistingObject(moveTile);
        }
    }

    @SuppressWarnings("Duplicates")
    public static GameMap loadMap(File file) {
        Vector2 viewSize = new Vector2(Renderer.get()
                                               .getSceneSize().x / 32f, Renderer.get()
                                                                                .getSceneSize().y / 32f).subtract(
                new Vector2(1f, 1f));
        Vector2 startPos = viewSize.copy();
        Vector2 mapSize = Vector2.ZERO();
        Vector2 offset = Vector2.ZERO();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            GameMap gameMap = SerializationUtils.deconvert(fileInputStream.readAllBytes());
            fileInputStream.close();
            Set<GameObject> objects = new LinkedHashSet<>();
            objects.addAll(Arrays.asList(gameMap.getMoveTiles()));
            objects.addAll(Arrays.asList(gameMap.getWallTiles()));
            for (GameObject moveTile : objects) {
                Vector2 pos = getMapPos(moveTile.getPosition());
                if (pos.x < startPos.x) {
                    startPos.x = pos.x;
                }
                if (pos.y < startPos.y) {
                    startPos.y = pos.y;
                }
            }
            for (GameObject moveTile : objects) {
                Vector2 pos = getMapPos(moveTile.getPosition());
                pos.x -= startPos.x + 1;
                pos.y -= startPos.y + 1;
                if (pos.x > mapSize.x) {
                    mapSize.x = pos.x;
                }
                if (pos.y > mapSize.y) {
                    mapSize.y = pos.y;
                }
            }
            offset.x = (float) Math.floor((viewSize.x - mapSize.x) / 2f) - startPos.x;
            offset.y = (float) Math.floor((viewSize.y - mapSize.y) / 2f) - startPos.y;
            for (GameObject object : objects) {
                object.setPosition(object.getPosition()
                                         .copy()
                                         .add(offset.copy()
                                                    .scale(new Vector2(32f, 32f))));
            }
            gameMap.registerObjects();
            return gameMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Vector2 getMapPos(Vector2 vec) {
        Vector2 n = vec.copy();
        n.x /= 32f;
        n.y /= 32f;
        return n;
    }
}
