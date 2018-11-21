package cz.cloudy.pacman;

import cz.cloudy.pacman.core.Render;
import cz.cloudy.pacman.core.Renderer;
import cz.cloudy.pacman.core.GameObject;
import cz.cloudy.pacman.interfaces.IGame;
import cz.cloudy.pacman.surface.Surface;
import cz.cloudy.pacman.types.Int2;
import cz.cloudy.pacman.types.Tileset;
import cz.cloudy.pacman.types.Vector2;
import cz.cloudy.pacman.utils.ImageUtils;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main
        extends Application {
    public static Scene    scene;
    public static Renderer renderer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.show();

        renderer = Renderer.get();
        Tileset tileset = new Tileset(Main.class.getResourceAsStream("./tiles.png"), new Int2(16, 16));

        Surface subSurface = new Surface(new Vector2(128, 128));


        //test.setSprite(tileset.getPart(new Int2(0, 22)));

        Renderer.instance.addGameHandler(new IGame() {
            @Override
            public void start() {

            }

            @Override
            public void update() {
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
                      .finish();
                Renderer.instance.resetRenderTarget();

                Render.begin()
                      .color()
                      .paint(Color.BLACK)
                      .end()
                      .clear(true)
                      .tex()
                      .setTexture(ImageUtils.getImagePart(tileset.getSource(), new Int2(0, 0), new Int2(224, 256)))
                      .setPosition(new Vector2(400f - 224f, 300f - 256f))
                      .setSize(new Vector2(2f, 2f))
                      .end()
                      .tex()
                      .setTexture(tileset.getPart(new Int2(1, 21)))
                      .setPosition(new Vector2(256f, 256f))
                      .setSize(new Vector2(2f, 2f))
                      .end()
                      .transform()
                      .position(new Vector2(16f, 16f))
                      .end()
                      .surface()
                      .setSurface(subSurface)
                      .end()
                      .finish();
            }
        });
    }
}
