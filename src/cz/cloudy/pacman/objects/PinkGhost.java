/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;

public class PinkGhost
        extends Ghost {
    @Override
    public void create() {
        super.create();
        this.ghostId = 2;
        this.scatterTile = new Vector2(2f, 1f);
        load();
    }

    @Override
    protected void modeChase() {
        Vector2 playerFront = GridUtils.getTileByGrid(target.getPosition()
                                                            .copy(), new Vector2(32f, 32f))
                                       .add(target.getSideVector()
                                                  .copy()
                                                  .scale(new Vector2(2f, 2f)));
        moveTile = playerFront;
        if (errorFindingPath || getTileByPosition().equals(playerFront)) {
            moveTile = GridUtils.getTileByGrid(target.getPosition(), new Vector2(32f, 32f));
        }
    }
}
