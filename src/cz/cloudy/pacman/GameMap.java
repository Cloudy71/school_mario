package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.fxengine.utils.SerializationUtils;
import cz.cloudy.pacman.objects.MoveTile;
import cz.cloudy.pacman.objects.WallTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class GameMap
        implements Serializable {
    private static final long serialVersionUID = 23L;

    private           String     name;
    private           WallTile[] wallTiles;
    private           MoveTile[] moveTiles;
    transient private Vector2    mapSize;
    transient private Vector2    offset;
    transient private Vector2    startPos;

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

    public Vector2 getTilePosition(Vector2 tile) {
        return getTile(tile).scale(new Vector2(32f, 32f))
                            .add(new Vector2(16f, 16f));
    }

    public Vector2 getTile(Vector2 tile) {
        return tile.copy()
                   .add(offset.copy()
                              .add(startPos));
    }

    public Vector2 getMapSize() {
        return mapSize.copy();
    }

    public Vector2 getOffset() {
        return offset.copy();
    }

    public Vector2 getStartPos() {
        return startPos;
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
            Main.currentMap = gameMap;
            Set<GameObject> objects = new LinkedHashSet<>();
            objects.addAll(Arrays.asList(gameMap.getMoveTiles()));
            objects.addAll(Arrays.asList(gameMap.getWallTiles()));
            for (GameObject moveTile : objects) {
                Vector2 pos = GridUtils.getTileByGrid(moveTile.getPosition(), new Vector2(32f, 32f));
                if (pos.x < startPos.x) {
                    startPos.x = pos.x;
                }
                if (pos.y < startPos.y) {
                    startPos.y = pos.y;
                }
            }
            for (GameObject moveTile : objects) {
                Vector2 pos = GridUtils.getTileByGrid(moveTile.getPosition(), new Vector2(32f, 32f));
                pos.x -= startPos.x - 1;
                pos.y -= startPos.y - 1;
                if (pos.x > mapSize.x) {
                    mapSize.x = pos.x;
                }
                if (pos.y > mapSize.y) {
                    mapSize.y = pos.y;
                }
            }
            offset.x = (float) Math.round((viewSize.x - mapSize.x) / 2f) - startPos.x;
            offset.y = (float) Math.round((viewSize.y - mapSize.y) / 2f) - startPos.y;
            gameMap.mapSize = mapSize;
            gameMap.offset = offset;
            gameMap.startPos = startPos;
            for (GameObject object : objects) {
                object.setPosition(object.getPosition()
                                         .copy()
                                         .add(offset.copy()
                                                    .scale(new Vector2(32f, 32f))));
            }
            if (file.getName()
                    .contains("main.jsb")) {
                gameMap.name = "main";
                Map<Vector2, Vector2[]> restrictions = new LinkedHashMap<>();
                restrictions.put(new Vector2(9f + (startPos.x + offset.x), 7f + (startPos.y + offset.y)),
                                 new Vector2[] {Vector2.UP()});
                restrictions.put(new Vector2(11f + (startPos.x + offset.x), 7f + (startPos.y + offset.y)),
                                 new Vector2[] {Vector2.UP()});
                restrictions.put(new Vector2(9f + (startPos.x + offset.x), 15f + (startPos.y + offset.y)),
                                 new Vector2[] {Vector2.UP()});
                restrictions.put(new Vector2(11f + (startPos.x + offset.x), 15f + (startPos.y + offset.y)),
                                 new Vector2[] {Vector2.UP()});
                Main.pathFinder.addRestrictions(restrictions);
                Main.pathFinder1.addRestrictions(restrictions);
            } else {
                Main.pathFinder.removeRestrictions();
                Main.pathFinder1.removeRestrictions();
            }
            gameMap.registerObjects();
            return gameMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
