package core;

import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;

public class LunarLander {

    public static void main(String[] args) {

        try (Graphics2D graphics = new Graphics2D((int)(1920 * 1.0), (int)(1080 * 1.0), "Final Project - Lunar Lander")) {
            graphics.initialize(Color.BLACK);
            Game game = new Game(graphics);
            game.initialize();
            game.run();
            game.shutdown();
        }
    }
}
