package cz.cloudy.pacman;

import cz.cloudy.pacman.core.Renderer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Scene    scene;
    public static Renderer renderer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.show();

        renderer = new Renderer();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
