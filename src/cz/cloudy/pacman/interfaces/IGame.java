/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 18:35
*/

package cz.cloudy.pacman.interfaces;

public interface IGame {
    void start();

    void update();

    default void fixedUpdate() {}

    default void belowRender() {}

    void render();
}
