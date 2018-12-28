/*
  User: Cloudy
  Date: 27-Dec-18
  Time: 22:15
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.Main;
import javafx.scene.image.Image;

public class Ghost
        extends GameObject {
    private int ghostId;

    private int     side;
    private Image[] sprites = new Image[4];
    private float   scale;

    @Override
    public void create() {
        this.ghostId = Main.ghost++;
        this.side = 0;

        for (int i = 0; i < 4; i++) {
            sprites[i] = Main.tileset.getPart(new Int2(i * 2, 16 + ghostId));
        }

        this.scale = 0f;

        setPivot(Pivot.CENTER());
    }

    @Override
    public void update() {
        setSprite(sprites[side]);
        setScale(new Vector2(scale, scale));
    }

    @Override
    protected void dispose() {

    }
}
