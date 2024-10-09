package ecs.Components;

import edu.usu.graphics.Texture;

public class Appearance extends Component {
    public Texture image;

    public Appearance(Texture image) {
        this.image = image;
    }
}
