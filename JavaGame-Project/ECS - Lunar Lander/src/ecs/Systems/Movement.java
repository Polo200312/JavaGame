package ecs.Systems;

import ecs.Entities.Entity;

public class Movement extends System {
    public Movement() {
        super(ecs.Components.Lander.class, ecs.Components.Position.class, ecs.Components.LanderStatus.class);
    }

    @Override
    public void update(double elapsedTime) {
        final float GRAVITY = 0.025f;    // world units per second

        // Even though there should only be one, still going to iterate through
        // the whole set of values in case other landers get added in the future.
        for (Entity entity : entities.values()) {
            var status = entity.get(ecs.Components.LanderStatus.class);
            var position = entity.get(ecs.Components.Position.class);

            if (status.alive && !status.landed) {
                status.momentum.y += (float) (GRAVITY * elapsedTime);
                position.y += (float) (status.momentum.y * elapsedTime);
                position.x += (float) (status.momentum.x * elapsedTime);
            }
        }
    }
}
