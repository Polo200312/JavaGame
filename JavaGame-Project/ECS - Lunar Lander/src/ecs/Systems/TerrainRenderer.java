package ecs.Systems;

import ecs.Components.Terrain;
import ecs.Entities.Entity;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Triangle;
import org.joml.Vector3f;
import utils.Misc;

public class TerrainRenderer extends System {

    private final Graphics2D graphics;
    private long terrainEntityId;

    public TerrainRenderer(Graphics2D graphics) {
        super(ecs.Components.Terrain.class);

        this.graphics = graphics;
    }

    @Override
    public boolean add(Entity entity) {
        // This is only one terrain entity (ha!), so grab its id when added
        boolean added = super.add(entity);
        if (added) {
            terrainEntityId = entity.getId();
        }

        return added;
    }

    @Override
    public void update(double elapsedTime) {
        // Grab the terrain entity, "in theory" there should only be one entity in this system!
        if (!this.entities.isEmpty()) {
            Terrain terrain = this.entities.get(terrainEntityId).get(Terrain.class);
            renderTerrainFill(terrain);
            renderTerrainSurface(terrain);
        }
    }

    /**
     * Renders the terrain by drawing triangles to fill the area
     */
    private void renderTerrainFill(Terrain terrain) {
        final Color TERRAIN_COLOR = new Color(0.19f, 0.19f, 0.19f);

        // Go through the terrain elevations and create triangles out of them for rendering
        for (int i = 0; i < terrain.terrain.size() - 1; i++) {
            Terrain.TerrainPoint pt1 = terrain.terrain.get(i);
            Terrain.TerrainPoint pt2 = terrain.terrain.get(i + 1);

            // Create two triangles from these two points, one for the left side
            // and one for the right size of the area covered by these two points
            Triangle t1 = new Triangle(
                    new Vector3f(pt1.x, pt1.y, Misc.LAYER_TERRAIN),
                    new Vector3f(pt2.x, pt2.y, Misc.LAYER_TERRAIN),
                    new Vector3f(pt1.x, 0.5f, Misc.LAYER_TERRAIN));
            Triangle t2 = new Triangle(
                    new Vector3f(pt2.x, pt2.y, Misc.LAYER_TERRAIN),
                    new Vector3f(pt2.x, 0.5f, Misc.LAYER_TERRAIN),
                    new Vector3f(pt1.x, 0.5f, Misc.LAYER_TERRAIN));

            graphics.draw(t1, TERRAIN_COLOR);
            graphics.draw(t2, TERRAIN_COLOR);
        }
    }

    private void renderTerrainSurface(Terrain terrain) {
        // Go through the terrain elevations and create lines out of them for rendering
        for (int i = 0; i < terrain.terrain.size() - 1; i++) {
            Terrain.TerrainPoint pt1 = terrain.terrain.get(i);
            Terrain.TerrainPoint pt2 = terrain.terrain.get(i + 1);

            graphics.draw(
                    new Vector3f(pt1.x, pt1.y, Misc.LAYER_TERRAIN_SURFACE),
                    new Vector3f(pt2.x, pt2.y, Misc.LAYER_TERRAIN_SURFACE),
                    Color.WHITE);
        }
    }
}
