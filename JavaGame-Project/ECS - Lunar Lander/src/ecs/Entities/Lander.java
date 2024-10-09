package ecs.Entities;

import ecs.Components.Appearance;
import ecs.Components.Controllable;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Lander {

    public static Entity create(Vector2f center, Vector2f size, float rotation, float rotationRate, float thrustRate, float fuel) {
        Entity lander = new Entity();

        lander.add(new ecs.Components.Lander());
        lander.add(new ecs.Components.Position(center.x, center.y));
        lander.add(new ecs.Components.Size(size.x, size.y));
        lander.add(new ecs.Components.LanderStatus(rotation, fuel));
        lander.add(new ecs.Components.LanderParams(rotationRate, thrustRate, (float) Math.toRadians(10), 0.04f));

        Texture image = new Texture("resources/images/Lani.png");
        ecs.Components.Appearance appearance = new Appearance(image);
        lander.add(appearance);

        lander.add(new Controllable());
        lander.add(new ecs.Components.KeyboardControlled(
                Map.of(
                        GLFW_KEY_UP, Controllable.Action.Thrust,
                        GLFW_KEY_LEFT, Controllable.Action.RotateLeft,
                        GLFW_KEY_RIGHT, Controllable.Action.RotateRight
                )));

        return lander;
    }
}
