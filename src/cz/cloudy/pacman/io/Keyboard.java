/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 20:59
*/

package cz.cloudy.pacman.io;

import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

public class Keyboard {
    private static List<KeyCode> keyPressed;
    private static List<KeyCode> keyReleased;
    private static List<KeyCode> keyPress;

    static {
        keyPressed = new LinkedList<>();
        keyReleased = new LinkedList<>();
        keyPress = new LinkedList<>();
    }

    public static boolean isKeyPressed(KeyCode keyCode) {
        return keyPressed.contains(keyCode);
    }

    public static boolean isKeyReleased(KeyCode keyCode) {
        return keyReleased.contains(keyCode);
    }

    public static boolean isKeyPress(KeyCode keyCode) {
        return keyPress.contains(keyCode);
    }

    protected static void addKey(KeyboardController.KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        switch (keyboardKeyType) {
            case PRESSED:
                keyPressed.add(keyCode);
            case RELEASED:
                keyReleased.add(keyCode);
            case PRESS:
                keyPress.add(keyCode);
        }
    }
}
