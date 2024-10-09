package views;

import core.GameModel;
import core.KeyboardInput;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Texture;
import org.joml.Random;
import org.joml.Vector2f;
import utils.MyRandom;

import static org.lwjgl.glfw.GLFW.*;

public class GamePlayView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.GamePlay;
    private GameModel gameModel;
    private Powerup powerup;

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });

        Texture texPowerup = new Texture("resources/images/powerup.png");

        MyRandom rX = new MyRandom();
        float px = rX.nextFloat(-0.5f, 0.5f);
        MyRandom rY = new MyRandom();
        float py = rY.nextFloat(-0.5f, 0.05f);
        powerup = new Powerup(texPowerup,
                new float[]{50 / 1000f, 50 / 1000f, 50 / 1000f, 50 / 1000f, 50 / 1000f, 50 / 1000f},
                new Vector2f(0.05f, 0.05f),
                new Vector2f(px, py));
    }

    @Override
    public void initializeSession() {
        gameModel = new GameModel();
        gameModel.initialize(graphics);
        nextGameState = GameStateEnum.GamePlay;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {

        gameModel.update(elapsedTime);
        powerup.update(elapsedTime);
    }

    @Override
    public void render(double elapsedTime) {
        // Nothing to do because the render now occurs in the update of the game model
        powerup.draw(graphics, Color.WHITE);
    }
}
