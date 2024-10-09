package edu.usu.graphics;

public class Color {
    public final float r;
    public final float g;
    public final float b;
    public final float a;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1.0f, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color PURPLE = new Color(0.5f, 0, 0.5f);
    public static final Color YELLOW = new Color(1, 1, 0);

    public static final Color CORNFLOWER_BLUE = new Color(100 / 255f, 149 / 255f, 237 / 255f);
}