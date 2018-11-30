/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 15:05
*/

package cz.cloudy.fxengine.io;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;
import javafx.scene.input.MouseButton;

import java.util.LinkedList;
import java.util.List;

public class Mouse {
    private static List<MouseButton> mousePressed;
    private static List<MouseButton> mousePressedFixed;
    private static List<MouseButton> mouseReleased;
    private static List<MouseButton> mouseReleasedFixed;
    private static List<MouseButton> mousePress;
    private static Vector2           position;

    static {
        mousePressed = new LinkedList<>();
        mousePressedFixed = new LinkedList<>();
        mouseReleased = new LinkedList<>();
        mouseReleasedFixed = new LinkedList<>();
        mousePress = new LinkedList<>();
        position = Vector2.IDENTITY();
    }

    public static boolean isMousePressed(MouseButton mouseButton) {
        return mousePressed.contains(mouseButton) ||
               (mousePressedFixed.contains(mouseButton) && Renderer.instance.isFixedSection());
    }

    public static boolean isMouseReleased(MouseButton mouseButton) {
        return mouseReleased.contains(mouseButton) ||
               (mouseReleasedFixed.contains(mouseButton) && Renderer.instance.isFixedSection());
    }

    public static boolean isMousePress(MouseButton mouseButton) {
        return mousePress.contains(mouseButton);
    }

    @SuppressWarnings("Duplicates")
    protected static void addAction(MouseController.MouseActionType mouseActionType, MouseButton mouseButton) {
        switch (mouseActionType) {
            case PRESSED:
                if (mousePressed.contains(mouseButton)) break;
                mousePressed.add(mouseButton);
                mousePressedFixed.add(mouseButton);
            case RELEASED:
                if (mouseReleased.contains(mouseButton)) break;
                mouseReleased.add(mouseButton);
                mouseReleasedFixed.add(mouseButton);
            case PRESS:
                if (mousePress.contains(mouseButton)) break;
                mousePress.add(mouseButton);
        }
    }

    @SuppressWarnings("Duplicates")
    protected static void removeAction(MouseController.MouseActionType mouseActionType, MouseButton mouseButton) {
        switch (mouseActionType) {
            case PRESSED:
                if (Renderer.instance.isFixedSection()) {
                    mousePressedFixed.remove(mouseButton);
                } else {
                    mousePressed.remove(mouseButton);
                }
            case RELEASED:
                if (Renderer.instance.isFixedSection()) {
                    mouseReleasedFixed.remove(mouseButton);
                } else {
                    mouseReleased.remove(mouseButton);
                }
            case PRESS:
                mousePress.remove(mouseButton);
        }
    }

    protected static void synchronizeActions() {
        for (MouseButton mouseButton : mousePressedFixed) {
            if (!mousePressed.contains(mouseButton)) mousePressedFixed.remove(mouseButton);
        }
        for (MouseButton mouseButton : mouseReleasedFixed) {
            if (!mouseReleased.contains(mouseButton)) mouseReleasedFixed.remove(mouseButton);
        }
    }

    protected static void removeReleased() {
        mouseReleased.clear();
    }

    protected static void removePressed() {
        mousePressed.clear();
    }

    protected static void setPosition(Vector2 position) {
        Mouse.position = position;
    }

    public static Vector2 getPosition() {
        return Mouse.position;
    }
}
