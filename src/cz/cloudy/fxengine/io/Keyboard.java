/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 20:59
*/

package cz.cloudy.fxengine.io;

import cz.cloudy.fxengine.core.Renderer;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

public class Keyboard {
    private static List<KeyCode> keyPressed;
    private static List<KeyCode> keyPressedFixed;
    private static List<KeyCode> keyReleased;
    private static List<KeyCode> keyReleasedFixed;
    private static List<KeyCode> keyPress;

    static {
        keyPressed = new LinkedList<>();
        keyPressedFixed = new LinkedList<>();
        keyReleased = new LinkedList<>();
        keyReleasedFixed = new LinkedList<>();
        keyPress = new LinkedList<>();
    }

    public static boolean isKeyPressed(KeyCode keyCode) {
        return keyPressed.contains(keyCode) ||
               (keyPressedFixed.contains(keyCode) && Renderer.instance.isFixedSection());
    }

    public static boolean isKeyReleased(KeyCode keyCode) {
        return keyReleased.contains(keyCode) ||
               (keyReleasedFixed.contains(keyCode) && Renderer.instance.isFixedSection());
    }

    public static boolean isKeyPress(KeyCode keyCode) {
        return keyPress.contains(keyCode);
    }

    @SuppressWarnings("Duplicates")
    protected static void addKey(KeyboardController.KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        switch (keyboardKeyType) {
            case PRESSED:
                if (keyPressed.contains(keyCode)) break;
                keyPressed.add(keyCode);
                keyPressedFixed.add(keyCode);
            case RELEASED:
                if (keyReleased.contains(keyCode)) break;
                keyReleased.add(keyCode);
                keyReleasedFixed.add(keyCode);
            case PRESS:
                if (keyPress.contains(keyCode)) break;
                keyPress.add(keyCode);
        }
    }

    @SuppressWarnings("Duplicates")
    protected static void removeKey(KeyboardController.KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        switch (keyboardKeyType) {
            case PRESSED:
                if (Renderer.instance.isFixedSection()) {
                    keyPressedFixed.remove(keyCode);
                } else {
                    keyPressed.remove(keyCode);
                }
            case RELEASED:
                if (Renderer.instance.isFixedSection()) {
                    keyReleasedFixed.remove(keyCode);
                } else {
                    keyReleased.remove(keyCode);
                }
            case PRESS:
                keyPress.remove(keyCode);
        }
    }

    protected static void synchronizeKeys() {
        for (KeyCode keyCode : keyPressedFixed) {
            if (!keyPressed.contains(keyCode)) keyPressedFixed.remove(keyCode);
        }
        for (KeyCode keyCode : keyReleasedFixed) {
            if (!keyReleased.contains(keyCode)) keyReleasedFixed.remove(keyCode);
        }
    }

    protected static void removeReleased() {
        keyReleased.clear();
    }

    protected static void removePressed() {
        keyPressed.clear();
    }
}
