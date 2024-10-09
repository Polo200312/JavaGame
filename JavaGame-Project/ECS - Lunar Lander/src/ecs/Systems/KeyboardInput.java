package ecs.Systems;

import core.ParticleSystem;
import ecs.Components.Controllable;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends System {

    private final long window;

    public KeyboardInput(long window) {
        super(
                ecs.Components.KeyboardControlled.class,
                ecs.Components.Position.class,
                ecs.Components.Size.class,
                ecs.Components.LanderParams.class,
                ecs.Components.LanderStatus.class);

        this.window = window;
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            var input = entity.get(ecs.Components.KeyboardControlled.class);
            var params = entity.get(ecs.Components.LanderParams.class);
            var status = entity.get(ecs.Components.LanderStatus.class);

            if (glfwGetKey(window, input.lookup.get(Controllable.Action.RotateLeft)) == GLFW_PRESS) {
                if (status.alive && !status.landed) {
                    // TODO: Rotate the lander left
                    // Create a normalized direction vector
                    var landerPosition = entity.get(ecs.Components.Position.class); // 获取着陆器位置组件
                    var landerSize = entity.get(ecs.Components.Size.class); // 获取着陆器大小组件
                    /*double vectorX = Math.cos(status.rotation);
                    double vectorY = Math.sin(status.rotation);
                    landerPosition.x += (float) (vectorX * params.rotationRate * elapsedTime);
                    landerPosition.y += (float) (vectorY * params.rotationRate * elapsedTime);*/
                    status.rotation -= (float) (params.rotationRate * elapsedTime);
                }
            }
            if (glfwGetKey(window, input.lookup.get(Controllable.Action.RotateRight)) == GLFW_PRESS) {
                if (status.alive && !status.landed) {
                    // TODO: Rotate the lander right
                    status.rotation += (float) (params.rotationRate * elapsedTime);
                }
            }
            if (glfwGetKey(window, input.lookup.get(Controllable.Action.Thrust)) == GLFW_PRESS) {
                if (status.alive && !status.landed && status.fuel > 0) {
                    // TODO: Add thrust to the lander
                    // Generate some particles to show the thrust
                    var landerPosition = entity.get(ecs.Components.Position.class); // 获取着陆器位置组件
                    var landerSize = entity.get(ecs.Components.Size.class); // 获取着陆器大小组件
                    double vectorY = Math.cos(status.rotation);
                    double vectorX = Math.sin(status.rotation);
                    /*landerPosition.x += (float) (vectorX * params.thrustRate * elapsedTime);
                    landerPosition.y += (float) (vectorY * params.thrustRate * elapsedTime);*/
                    status.momentum.x += (float) (vectorX * params.thrustRate * elapsedTime);
                    status.momentum.y -= (float) (vectorY * params.thrustRate * elapsedTime);
                    // Update the fuel status
                    status.fuel -= (float) elapsedTime;
                    status.fuel = Math.max(0, status.fuel);

                    // Compute the point where the thrust comes from
                    Vector2f thrustPoint = new Vector2f(0, landerSize.height / 2);
                    Vector2f rotatedPoint = new Vector2f(
                            (float) (landerPosition.x + thrustPoint.x * Math.sin(status.rotation - Math.PI / 2) - thrustPoint.y * Math.cos(status.rotation - Math.PI / 2)),
                            (float) (landerPosition.y - thrustPoint.y * Math.sin(status.rotation - Math.PI / 2) + thrustPoint.x * Math.cos(status.rotation - Math.PI / 2))
                    );
                    // Show the thrust
                    ParticleSystem.instance().createThrust(
                            new Vector2f((float) -Math.sin(status.rotation), (float) Math.cos(status.rotation)),
                            new Vector2f(rotatedPoint.x, rotatedPoint.y),
                            0.008f, 0.002f,
                            0.2f, 0.025f,
                            1.0f, 0.025f);
                }
            }
        }
    }
}
