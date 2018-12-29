/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;
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
}
