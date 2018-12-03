package cz.cloudy.pacman;

import cz.cloudy.pacman.objects.MoveTile;
import cz.cloudy.pacman.objects.WallTile;

import java.io.Serializable;

public class GameMap
        implements Serializable {
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
}
