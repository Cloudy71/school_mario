/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 20:55
*/

package cz.cloudy.fxengine.io;

import cz.cloudy.fxengine.core.Renderer;
import javafx.scene.input.KeyCode;

public class KeyboardController {
    public enum KeyboardKeyType {
        PRESSED, RELEASED, PRESS
    }

    public static void addKey(KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.addKey(keyboardKeyType, keyCode);
    }

    public static void removeKey(KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.removeKey(keyboardKeyType, keyCode);
    }

    public static void synchronizeKeys() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.synchronizeKeys();
    }

    public static void removeReleased() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.removeReleased();
    }

    public static void removePressed() {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.removePressed();
    }
}
