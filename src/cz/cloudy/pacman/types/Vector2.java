package cz.cloudy.pacman.types;

public class Vector2 {
    public static final Vector2 IDENTITY       = new Vector2(0f, 0f);
    public static final Vector2 SCALE_IDENTITY = new Vector2(1f, 1f);

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector2(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector2 scale(Vector2 scaler) {
        this.x *= scaler.x;
        this.y *= scaler.y;
        return this;
    }

    public Vector2 move(Vector2 offset) {
        this.x += offset.x;
        this.y += offset.y;
        return this;
    }

    public float distance(Vector2 point) {
        return (float) Math.sqrt(1); // TODO: Finish
    }
}
