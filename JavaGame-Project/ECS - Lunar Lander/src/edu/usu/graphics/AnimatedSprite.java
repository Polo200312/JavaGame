package edu.usu.graphics;

import org.joml.Vector2f;

public class AnimatedSprite {

    private final Texture spriteSheet;
    private final float[] spriteTime;

    private double animationTime = 0;
    private int subImageIndex = 0;
    private final int subImageWidth;

    private final Vector2f size;
    protected Vector2f center;
    protected float rotation = 0;

    public AnimatedSprite(Texture spriteSheet, float[] spriteTime, Vector2f size, Vector2f center) {
        this.spriteSheet = spriteSheet;
        this.spriteTime = spriteTime;

        this.subImageWidth = spriteSheet.getWidth() / spriteTime.length;

        this.size = size;
        this.center = center;
    }

    public void update(double elapsedTime) {
        animationTime += elapsedTime;
        if (animationTime >= spriteTime[subImageIndex]) {
            animationTime -= spriteTime[subImageIndex];
            subImageIndex++;
            subImageIndex = subImageIndex % spriteTime.length;
        }
    }

    public void draw(Graphics2D graphics, Color color) {
        // Where to draw
        Rectangle destination = new Rectangle(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y);
        // Which sub-rectangle of the spritesheet to draw
        Rectangle subImage = new Rectangle(
                subImageWidth * subImageIndex,
                0,
                subImageWidth,
                spriteSheet.getHeight());
        graphics.draw(spriteSheet, destination, subImage, rotation, center, color);
    }
}
