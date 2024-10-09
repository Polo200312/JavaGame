package views;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;

public class Powerup extends AnimatedSprite {
    public Powerup(Texture spriteSheet, float[] spriteTime, Vector2f size, Vector2f center) {
        super(spriteSheet, spriteTime, size, center);
    }
}
