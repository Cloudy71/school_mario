/*
  User: Cloudy
  Date: 26-Nov-18
  Time: 19:26
*/

package cz.cloudy.pacman.objects;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.io.Keyboard;
import cz.cloudy.fxengine.physics.PhysicsData;
import cz.cloudy.fxengine.physics.PhysicsDataBuilder;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Pivot;
import cz.cloudy.fxengine.types.Tileset;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.ImageUtils;
import cz.cloudy.pacman.Main;
import cz.cloudy.pacman.scenes.GameScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class PacMan
        extends GameObject {

    private int side;
    private int frame;
    private int frameSide;

    private Image[][] sprites = new Image[4][3];

    private final int SIDE_RIGHT  = 0;
    private final int SIDE_BOTTOM = 1;
    private final int SIDE_LEFT   = 2;
    private final int SIDE_TOP    = 3;

    private long lastFrameChange;
    private long frameChangeInterval = 100 * 1_000_000;

    @Override
    public void create() {
        this.frame = 0;
        this.side = 0;
        this.frameSide = 0;
        this.lastFrameChange = Renderer.get()
                                       .getTime();

        Tileset tileset = new Tileset(Main.class.getResourceAsStream("./tiles.png"), new Int2(16, 16));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                sprites[i][j] = tileset.getPart(new Int2(i * 3 + j, 21));
            }
        }

        if (Renderer.get()
                    .getGameScene() == GameScene.class) {
            setScale(new Vector2(2f, 2f));
            PhysicsData physicsData = PhysicsDataBuilder.buildRectangle(new Vector2(32f, 32f));
            physicsData.setScalable(false);
            physicsData.setSolid(true);
            physicsData.setParent(this);
            setPhysicsData(physicsData);
        } else {
            for (int i = 0; i < 3; i++) {
                sprites[SIDE_RIGHT][i] = ImageUtils.getImagePart(tileset.getSource(), new Int2(128 + 32 * i, 256),
                                                                 new Int2(32, 32));
            }
        }
        setPivot(Pivot.CENTER());
    }

    @Override
    public void update() {
        setSprite(sprites[side][frame]);
        if (Renderer.get()
                    .getTime() >= lastFrameChange + frameChangeInterval) {
            lastFrameChange = Renderer.get()
                                      .getTime();
            frame = (frameSide == 0) ? frame - 1 : frame + 1;
            if (frame > 2 || frame < 0) {
                frame = frame < 0 ? 0 : 2;
                frameSide = frameSide == 0 ? 1 : 0;
            }
        }
    }

    @Override
    protected void dispose() {

    }

    @Override
    public void fixedUpdate() {
        if (Renderer.get()
                    .getGameScene() != GameScene.class) return;

        if (Keyboard.isKeyPress(KeyCode.UP)) {
            this.move(new Vector2(0f, -2f));
        }
    }
}
