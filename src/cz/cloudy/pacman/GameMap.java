package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.pacman.objects.MoveTile;
import cz.cloudy.pacman.objects.WallTile;

import java.io.Serializable;

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
}
