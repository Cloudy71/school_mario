/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.pacman.Main;

public class BlueGhost
        extends Ghost {
    @Override
    public void create() {
        super.create();
        this.ghostId = 1;
        this.scatterTile = new Vector2(Main.currentMap.getMapSize().x - 3f, Main.currentMap.getMapSize().y - 2f);
        load();
    }

    @Override
    protected void modeChase() {
        Vector2 playerFront = GridUtils.getTileByGrid(target.getPosition()
                                                            .copy(), new Vector2(32f, 32f))
                                       .add(target.getSideVector()
                                                  .copy());
        RedGhost redGhost = Renderer.get()
                                    .getGameObjectCollector()
                                    .getGameObjectsOfType(RedGhost.class)
                                    .iterator()
                                    .next();
        float dist = redGhost.getTileByPosition()
                             .distance(playerFront) * 2;
        float angle = redGhost.getTileByPosition()
                              .angle(playerFront);
        Vector2 desiredPos = GridUtils.getTileByGridRound(redGhost.getTileByPosition()
                                                                  .moveTowardsByLen(angle, dist), new Vector2(1f, 1f));
        moveTile = desiredPos;
    }
}
