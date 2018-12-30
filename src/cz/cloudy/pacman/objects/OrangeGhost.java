/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.pacman.Main;

public class OrangeGhost
        extends Ghost {
    @Override
    public void create() {
        super.create();
        this.ghostId = 3;
        this.scatterTile = new Vector2(2f, Main.currentMap.getMapSize().y - 2f);
        load();
    }

    @Override
    protected void modeChase() {
        Vector2 playerPos = GridUtils.getTileByGrid(target.getPosition(), new Vector2(32f, 32f));
        if (getTileByPosition().distance(playerPos) > 4) {
            moveTile = playerPos;
        } else {
            moveAroundWall();
        }
    }
}
