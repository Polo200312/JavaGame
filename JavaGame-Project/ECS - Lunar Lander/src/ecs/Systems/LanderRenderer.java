package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import org.joml.Vector2f;
import utils.Misc;

public class LanderRenderer extends System {

    private final Graphics2D graphics;

    public LanderRenderer(Graphics2D graphics) {
        super(
                ecs.Components.Lander.class,
                ecs.Components.Appearance.class,
                ecs.Components.Position.class,
                ecs.Components.Size.class,
                ecs.Components.LanderStatus.class);

        this.graphics = graphics;
    }

    @Override
    public void update(double elapsedTime) {
        // Even though there is only one lander, will still loop through all the
        // entities in case more get added in the future
        for (Entity entity : entities.values()) {
            var appearance = entity.get(ecs.Components.Appearance.class);
            var position = entity.get(ecs.Components.Position.class);
            var size = entity.get(ecs.Components.Size.class);
            var status = entity.get(ecs.Components.LanderStatus.class);

            if (status.alive) {
                graphics.draw(
                        appearance.image,
                        new Rectangle(position.x - size.width / 2, position.y - size.height / 2, size.width, size.height, Misc.LAYER_LANDER),
                        status.rotation,
                        new Vector2f(position.x, position.y),
                        Color.WHITE);
            }
        }
    }
}
