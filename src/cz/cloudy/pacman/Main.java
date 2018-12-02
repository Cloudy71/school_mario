package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.pacman.scenes.EditorScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main
        extends Application {
    public static Scene    scene;
    public static Renderer renderer;

    public static void main(String[] args) {
        launch(args);
    }

    public <T extends Double> void addToList(T a) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.show();

        renderer = Renderer.get();

        Renderer.instance.addGameScene(new EditorScene());
        Renderer.instance.setGameScene(0);
    }
}
