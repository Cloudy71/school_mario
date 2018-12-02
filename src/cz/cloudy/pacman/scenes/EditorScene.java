package cz.cloudy.pacman.scenes;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.interfaces.IGameScene;
import cz.cloudy.fxengine.io.Keyboard;
import cz.cloudy.fxengine.io.Mouse;
import cz.cloudy.fxengine.physics.RayCaster;
import cz.cloudy.fxengine.surface.Surface;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Tileset;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.GridUtils;
import cz.cloudy.fxengine.utils.SerializationUtils;
import cz.cloudy.pacman.GameMap;
import cz.cloudy.pacman.Main;
import cz.cloudy.pacman.objects.MoveTile;
import cz.cloudy.pacman.objects.PacMan;
import cz.cloudy.pacman.objects.WallTile;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class EditorScene implements IGameScene {
    private Tileset  tileset;
    private Surface  subSurface;
    private PacMan   pacMan;
    private Renderer renderer;

    @Override
    public void start() {
        renderer = Renderer.instance;

        tileset = new Tileset(Main.class.getResourceAsStream("./tiles.png"), new Int2(16, 16));

        subSurface = new Surface(new Vector2(128, 128));

//        pacMan = GameObjectFactory.createObject(PacMan.class);
//        pacMan.setSprite(tileset.getPart(new Int2(1, 21)));
//        pacMan.setPosition(new Vector2(320f, 320f));

        //test.setSprite(tileset.getPart(new Int2(0, 22)));
    }

    @Override
    public void update() {
        if (Mouse.isMousePressed(MouseButton.PRIMARY)) {
            Vector2 position = GridUtils.snapToGrid(Mouse.getPosition(), new Vector2(32f, 32f));
            position.x += 16f;
            position.y += 16f;
            GameObject collider = RayCaster.castPoint(GameObject.class, position);
            if (collider != null && collider.getClass() == MoveTile.class) {
                collider.destroy();
                collider = null;
            }
            if (collider == null) {
                WallTile wallTile = GameObjectFactory.createObject(WallTile.class);
                wallTile.setPosition(GridUtils.snapToGrid(position, new Vector2(32f, 32f)));
            }
        }
        if (Mouse.isMousePressed(MouseButton.SECONDARY)) {
            Vector2 position = GridUtils.snapToGrid(Mouse.getPosition(), new Vector2(32f, 32f));
            position.x += 16f;
            position.y += 16f;
            GameObject collider = RayCaster.castPoint(GameObject.class, position);
            if (collider != null && collider.getClass() == WallTile.class) {
                collider.destroy();
                collider = null;
            }
            if (collider == null) {
                MoveTile moveTile = GameObjectFactory.createObject(MoveTile.class);
                moveTile.setPosition(GridUtils.snapToGrid(position, new Vector2(32f, 32f)));
            }
        }

        if (Keyboard.isKeyPressed(KeyCode.S)) {
            Set<WallTile> wallTilesSet = Renderer.instance.getGameObjectCollector()
                                                          .getGameObjectsOfType(WallTile.class);
            Set<MoveTile> moveTilesSet = Renderer.instance.getGameObjectCollector()
                                                          .getGameObjectsOfType(MoveTile.class);

            WallTile[] wallTiles = new WallTile[wallTilesSet.size()];
            MoveTile[] moveTiles = new MoveTile[moveTilesSet.size()];
            int i = 0;
            for (WallTile wallTile : wallTilesSet) {
                wallTiles[i++] = wallTile;
            }
            i = 0;
            for (MoveTile moveTile : moveTilesSet) {
                moveTiles[i++] = moveTile;
            }

            GameMap gameMap = new GameMap("no_name", wallTiles, moveTiles);
            byte[] bytes = SerializationUtils.convert(gameMap);
            try {
                FileOutputStream fos = new FileOutputStream(gameMap.getName() + ".fxb");
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void render() {
        subSurface.setTarget();
        Render.begin()
              .color()
              .paint(Color.RED)
              .end()
              .clear(true)
              .text()
              .setText("FPS: " + renderer.getFramerate())
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0f, 0f))
              .end()
              .text()
              .setText("FFPS: " + renderer.getFixedFramerate())
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0f, 32f))
              .end()
              .text()
              .setText("Walls: " + renderer.getGameObjectCollector()
                                           .getGameObjectsOfType(WallTile.class)
                                           .size())
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0, 48f))
              .end()
              .finish();
        Renderer.instance.resetRenderTarget();

        Render.begin()
              .color()
              .paint(Color.BLACK)
              .end()
              .clear(true)
              .transform()
              .position(new Vector2(16f, 16f))
              .end()
              .surface()
              .setSurface(subSurface)
              .setPosition(Vector2.IDENTITY())
              .end()
              .transform()
              .reset()
              .end()
              .rect()
              .setPosition(GridUtils.snapToGrid(Mouse.getPosition(), new Vector2(32f, 32f)))
              .setSize(new Vector2(32f, 32f))
              .setPaint(Color.RED)
              .end()
              .finish();
    }
}
