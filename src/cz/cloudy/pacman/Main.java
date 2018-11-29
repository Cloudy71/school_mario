package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.fxengine.core.Render;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.interfaces.IGame;
import cz.cloudy.fxengine.surface.Surface;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Tileset;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.fxengine.utils.ImageUtils;
import cz.cloudy.pacman.objects.PacMan;
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

        PacMan pacMan = GameObjectFactory.createObject(PacMan.class);
        pacMan.setSprite(tileset.getPart(new Int2(1, 21)));
        pacMan.setPosition(new Vector2(320f, 320f));

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
                      .text()
                      .setText("FFPS: " + renderer.getFixedFramerate())
                      .setPaint(Color.WHITE)
                      .setPosition(new Vector2(0f, 32f))
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
                      .transform()
                      .position(new Vector2(16f, 16f))
                      .end()
                      .surface()
                      .setSurface(subSurface)
                      .setPosition(Vector2.IDENTITY())
                      .end()
                      .finish();
            }
        });
    }
}
