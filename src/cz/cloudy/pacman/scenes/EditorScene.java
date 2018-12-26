package cz.cloudy.pacman.scenes;

import cz.cloudy.fxengine.core.*;
import cz.cloudy.fxengine.interfaces.IGameScene;
import cz.cloudy.fxengine.io.Keyboard;
import cz.cloudy.fxengine.io.Mouse;
import cz.cloudy.fxengine.physics.RayCaster;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

public class EditorScene
        implements IGameScene {
    private Tileset  tileset;
    private Surface  subSurface;
    private PacMan   pacMan;
    private Renderer renderer;

    private int     ghosts;
    private boolean spawn;
    private int     walls;
    private int     moves;

    private String filename;

    @Override
    public void start() {
        renderer = Renderer.instance;

        tileset = new Tileset(Main.class.getResourceAsStream("./tiles.png"), new Int2(16, 16));

        subSurface = new Surface(new Vector2(128, 128));

        filename = null;

//        pacMan = GameObjectFactory.createObject(PacMan.class);
//        pacMan.setSprite(tileset.getPart(new Int2(1, 21)));
//        pacMan.setPosition(new Vector2(320f, 320f));

        //test.setSprite(tileset.getPart(new Int2(0, 22)));
    }

    private Vector2 getSnappedMousePosition() {
        Vector2 position = GridUtils.snapToGrid(Mouse.getPosition(), new Vector2(32f, 32f));
        position.x += 16f;
        position.y += 16f;
        return position;
    }

    @Override
    public void update() {
        ghosts = 0;
        spawn = false;
        moves = 0;
        for (MoveTile moveTile : renderer.getGameObjectCollector()
                                         .getGameObjectsOfType(MoveTile.class)) {
            if (moveTile.getType() == 2) ghosts++;
            else if (moveTile.getType() == 1) spawn = true;
            else moves++;
        }
        walls = renderer.getGameObjectCollector()
                        .getGameObjectsOfType(WallTile.class)
                        .size();

        if (Mouse.isMousePressed(MouseButton.PRIMARY)) {
            Vector2 position = getSnappedMousePosition();
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
            // TODO: Bug while changing its type. (if there are 3 ghost spawns, another spawn cannot be removed.)
            Vector2 position = getSnappedMousePosition();
            GameObject collider = RayCaster.castPoint(GameObject.class, position);
            if (collider != null && collider.getClass() == WallTile.class) {
                collider.destroy();
                collider = null;
            } else if (collider != null && collider.getClass() == MoveTile.class) {
                MoveTile moveTile = (MoveTile) collider;
                int target = moveTile.getType() + 1;
                if (spawn && ghosts == 4) target = 0;
                else if (spawn) target = 2;
                else if (ghosts == 4) target = 1;
                moveTile.setType(target);
                if (moveTile.getType() > 2) moveTile.setType(target - 3);
            }

            if (collider == null) {
                MoveTile moveTile = GameObjectFactory.createObject(MoveTile.class);
                moveTile.setPosition(GridUtils.snapToGrid(position, new Vector2(32f, 32f)));
            }
        }

        if (Keyboard.isKeyPressed(KeyCode.DELETE)) {
            Vector2 position = getSnappedMousePosition();
            GameObject collider = RayCaster.castPoint(GameObject.class, position);
            if (collider != null) collider.destroy();
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

            Calendar calendar = Calendar.getInstance();
            if (filename == null) {
                filename = "map_" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" +
                           calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.HOUR_OF_DAY) + "-" +
                           calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND);
            }

            GameMap gameMap = new GameMap(filename, wallTiles, moveTiles);
            byte[] bytes = SerializationUtils.convert(gameMap);
            try {
                FileOutputStream fos = new FileOutputStream(gameMap.getName() + ".jsb");
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else if (Keyboard.isKeyPressed(KeyCode.L)) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                       .addAll(new FileChooser.ExtensionFilter("Java Serialization Binary", "*.jsb"));
            fileChooser.setTitle("Load map");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showOpenDialog(Main.scene.getWindow());

            if (file != null) {
                filename = file.getAbsolutePath()
                               .substring(0, file.getAbsolutePath()
                                                 .lastIndexOf("."));
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    GameMap gameMap = SerializationUtils.deconvert(fileInputStream.readAllBytes());
                    fileInputStream.close();
                    gameMap.registerObjects();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

            }
        }
    }

    @Override
    public void render() {
        subSurface.setTarget();
        Render.begin()
              .color()
              .paint(Color.GRAY)
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
              .setPosition(new Vector2(0f, 16f))
              .end()
              .text()
              .setText("Walls, Moves: " + walls + ", " + moves)
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0, 48f))
              .end()
              .text()
              .setText("Ghosts: " + ghosts + " / 4")
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0, 64f))
              .end()
              .text()
              .setText("Spawn: " + (spawn ? "yes" : "no"))
              .setPaint(Color.WHITE)
              .setPosition(new Vector2(0, 80f))
              .end()
              .finish();
        Renderer.instance.resetRenderTarget();

        Render r = Render.begin();
        r.color()
         .paint(Color.BLACK)
         .end()
         .clear(true);
        // Map Grid
        for (int i = 0; i < Main.scene.getWidth() / 32; i++) {
            r.line()
             .startPoint(new Vector2(32f * i, 0f))
             .endPoint(new Vector2(32f * i, Main.scene.getHeight()))
             .setPaint(Color.GRAY)
             .end();
        }
        for (int i = 0; i < Main.scene.getHeight() / 32; i++) {
            r.line()
             .startPoint(new Vector2(0f, 32f * i))
             .endPoint(new Vector2(Main.scene.getWidth(), 32f * i))
             .setPaint(Color.GRAY)
             .end();
        }
        r.finish();
    }

    @Override
    public void aboveRender() {
        Render.begin()
              .rect()
              .setPosition(GridUtils.snapToGrid(Mouse.getPosition(), new Vector2(32f, 32f)))
              .setSize(new Vector2(32f, 32f))
              .setPaint(new Color(1f, 0f, 0f, 0.5f))
              .end()
              .transform()
              .position(new Vector2(16f, 16f))
              .end()
              .surface()
              .setSurface(subSurface)
              .setPosition(Vector2.ZERO())
              .end()
              .transform()
              .reset()
              .end()
              .finish();
    }
}
