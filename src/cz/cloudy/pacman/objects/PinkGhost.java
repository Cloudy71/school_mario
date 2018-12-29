/*
  User: Cloudy
  Date: 28-Dec-18
  Time: 18:28
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.types.Vector2;

public class PinkGhost
        extends Ghost {
    @Override
    public void create() {
        super.create();
        this.ghostId = 2;
        this.scatterTile = new Vector2(2f, 1f);
        load();
    }
}
