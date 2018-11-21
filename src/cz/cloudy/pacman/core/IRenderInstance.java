/*
  User: Cloudy
  Date: 19-Nov-18
  Time: 23:56
*/

package cz.cloudy.pacman.core;


public interface IRenderInstance {
    default void proceed() {
    }

    default Render end() {
        return Render.begin(true)
                     .addToQueue(this);
    }
}
