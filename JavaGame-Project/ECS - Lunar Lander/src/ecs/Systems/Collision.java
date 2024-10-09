package ecs.Systems;

import core.ParticleSystem;
import ecs.Entities.Entity;
import org.joml.Vector2f;

public class Collision extends System {
    private Entity entityTerrain;
    private Entity entityLander;

    public Collision() {
        // We don't identify any components here, because a different technique is
        // needed.  The overridden isInterested and add methods handles this.
        super();
    }

    @Override
    public void update(double elapsedTime) {
        if (landerTouchSurface()) {

        }
    }

    /**
     * Look only for the terrain and lander entities
     */
    @Override
    public boolean add(Entity entity) {
        boolean added = false;
        if (entity.contains(ecs.Components.Terrain.class)) {
            entityTerrain = entity;
            added = true;
        }
        if (entity.contains(ecs.Components.Lander.class)) {
            entityLander = entity;
            added = true;
        }
        return added;
    }

    /**
     * Customize to look for only the terrain and lander entities
     */
    @Override
    protected boolean isInterested(Entity entity) {
        // Yes, I realize these aren't needed, because the overidden add method
        // doesn't use it, but I still did it for correctness in the case
        // it eventually is needed.
        if (entity.contains(ecs.Components.Terrain.class)) {
            return true;
        }
        if (entity.contains(ecs.Components.Lander.class)) {
            return true;
        }

        return false;
    }

    /**
     * Works through each line segment in the surface to see if the lander has collided with it or not.
     *
     * @return
     */
    private boolean landerTouchSurface() {
        if (entityLander == null || entityTerrain == null) {
            return true;
        }

        var terrain = entityTerrain.get(ecs.Components.Terrain.class);
        var landerStatus = entityLander.get(ecs.Components.LanderStatus.class);
        var landerParams = entityLander.get(ecs.Components.LanderParams.class);
        var landerCenter = new Vector2f(entityLander.get(ecs.Components.Position.class).x, entityLander.get(ecs.Components.Position.class).y);
        float landerSize = entityLander.get(ecs.Components.Size.class).height / 2;  // divide by 2 is because the circle size isn't close enough

        // Create these here, so we don't have to dynamically allocate for every surface point,
        // instead just reuse these.
        Vector2f pt1 = new Vector2f(0, 0);
        Vector2f pt2 = new Vector2f(0, 0);
        for (int point = 0; point < terrain.terrain.size() - 1; point++) {
            pt1.x = terrain.terrain.get(point).x;
            pt1.y = terrain.terrain.get(point).y;

            pt2.x = terrain.terrain.get(point + 1).x;
            pt2.y = terrain.terrain.get(point + 1).y;

            if (lineCircleIntersection(pt1, pt2, landerCenter, landerSize)) {
                if (terrain.terrain.get(point).safe && terrain.terrain.get(point + 1).safe && utils.Misc.isSafeAngle(landerStatus.rotation, landerParams.safeAngle) && landerStatus.getSpeed() <= landerParams.safeSpeed) {
                    landerStatus.landed = true;
                } else if (landerStatus.alive) {
                    ParticleSystem.instance().createExplosion(landerCenter);
                    landerStatus.alive = false;
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Tests if a line intersects with a circle.  True returned if an intersection occurs, false otherwise.
     * Code provided by ChatGPT from the following prompt:
     * ---
     * Given this Java method signature, write a method that returns a boolean true if the line formed by the two points intersects the circle:
     * public boolean lineCircleIntersection(Vector2f pt1, Vector2f pt2, Vector2f circleCenter, float circleRadius)
     * ---
     */
    private boolean lineCircleIntersection(Vector2f pt1, Vector2f pt2, Vector2f circleCenter, float circleRadius) {
        // Translate points to circle's coordinate system
        Vector2f d = pt2.sub(pt1); // Direction vector of the line
        Vector2f f = pt1.sub(circleCenter); // Vector from circle center to the start of the line

        float a = d.dot(d);
        float b = 2 * f.dot(d);
        float c = f.dot(f) - circleRadius * circleRadius;

        float discriminant = b * b - 4 * a * c;

        // If the discriminant is negative, no real roots and thus no intersection
        if (discriminant < 0) {
            return false;
        }

        // Check if the intersection points are within the segment
        discriminant = (float) Math.sqrt(discriminant);
        float t1 = (-b - discriminant) / (2 * a);
        float t2 = (-b + discriminant) / (2 * a);

        if (t1 >= 0 && t1 <= 1) {
            return true;
        }
        if (t2 >= 0 && t2 <= 1) {
            return true;
        }

        return false;
    }
}
