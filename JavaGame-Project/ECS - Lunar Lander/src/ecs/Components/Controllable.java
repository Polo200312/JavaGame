package ecs.Components;

public class Controllable extends Component {

    public enum Action {
        RotateLeft,
        RotateRight,
        Thrust
    }

    public Controllable() {
    }
}
