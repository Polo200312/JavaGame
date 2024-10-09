package ecs.Components;

import org.joml.Vector2f;

public class LanderStatus extends Component {
    public float rotation;
    public float fuel;
    public Vector2f momentum = new Vector2f(0, 0);
    public boolean alive = true;
    public boolean landed = false;

    public LanderStatus(float rotation, float fuel) {
        this.rotation = rotation;
        this.fuel = fuel;
    }

    public float getSpeed() {
        return (float)Math.sqrt(momentum.x * momentum.x + momentum.y * momentum.y);
    }
}
