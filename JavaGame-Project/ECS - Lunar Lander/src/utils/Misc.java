package utils;

public class Misc {
    public static final float LAYER_MENU = 0.0f;
    public static final float LAYER_BACKGROUND = -0.01f;
    public static final float LAYER_BORDER = -0.02f;
    public static final float LAYER_LANDER = 0.0f;
    public static final float LAYER_TERRAIN = 0.01f;
    public static final float LAYER_TERRAIN_SURFACE = 0.015f;
    public static final float LAYER_PARTICLES = 0.03f;
    public static final float LAYER_HUD = 0.1f;

    public static boolean isSafeAngle(float rotation, float safeAngle) {
        return (rotation >= (2 * Math.PI - safeAngle / 2) && rotation <= (2 * Math.PI + safeAngle / 2));
    }

}
