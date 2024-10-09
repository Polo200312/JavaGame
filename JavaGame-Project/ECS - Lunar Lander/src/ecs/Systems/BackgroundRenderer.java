package ecs.Systems;

import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import edu.usu.graphics.Texture;
import utils.Misc;

public class BackgroundRenderer extends System {

    private final Graphics2D graphics;
    private final Texture texBackground;
    private final Rectangle rectBackground = new Rectangle(-0.5f, -0.5f, 1, 1, Misc.LAYER_BACKGROUND);
    private final Rectangle rectBorder = new Rectangle(-0.505f, -0.505f, 1.01f, 1.01f, utils.Misc.LAYER_BORDER);

    public BackgroundRenderer(Graphics2D graphics) {
        this.graphics = graphics;

        texBackground = new Texture("resources/images/background.jpg");
    }

    @Override
    public void update(double elapsedTime) {
        // Draw a white rectangle as a border around everything first
        graphics.draw(rectBorder, Color.WHITE);

        // Now draw the background image second, so it covers most of the white rectangle
        graphics.draw(texBackground, rectBackground, Color.WHITE);
    }
}
