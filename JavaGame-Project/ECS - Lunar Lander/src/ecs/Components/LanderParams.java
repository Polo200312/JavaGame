package ecs.Components;

public class LanderParams extends Component {
    public final float rotationRate;
    public final float thrustRate;
    public final float safeAngle;
    public final float safeSpeed;

    public LanderParams(float rotationRate, float thrustRate, float safeAngle, float safeSpeed) {
        this.rotationRate = rotationRate;
        this.thrustRate = thrustRate;
        this.safeAngle = safeAngle;
        this.safeSpeed = safeSpeed;
    }
}
