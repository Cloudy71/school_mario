/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 15:05
*/

package cz.cloudy.fxengine.io;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.input.MouseButton;

public class MouseController {
    public enum MouseActionType {
        PRESSED, RELEASED, PRESS
    }

    public static void addAction(MouseActionType mouseActionType, MouseButton mouseButton) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.addAction(mouseActionType, mouseButton);
    }

    public static void removeAction(MouseActionType mouseActionType, MouseButton mouseButton) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.removeAction(mouseActionType, mouseButton);
    }

    public static void synchronizeActions() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.synchronizeActions();
    }

    public static void removeReleased() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.removeReleased();
    }

    public static void removePressed() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.removePressed();
    }

    public static void setPosition(Vector2 position) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Mouse.setPosition(position);
    }
}
