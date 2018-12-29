/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;
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
}
