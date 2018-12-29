/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.Main;

public class RedGhost
        extends Ghost {
    @Override
    public void create() {
        super.create();
        this.ghostId = 0;
        this.scatterTile = new Vector2(Main.currentMap.getMapSize().x - 3f, 1f);
        load();
    }
}
