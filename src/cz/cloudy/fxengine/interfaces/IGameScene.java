/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 18:35
*/

package cz.cloudy.fxengine.interfaces;

public interface IGameScene {
    void start();

    void update();

    default void fixedUpdate() {
    }

    default void aboveRender() {
    }

    void render();

    default void end() {
    }
}
