package core;

import ecs.Entities.*;
import ecs.Systems.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    private TerrainRenderer sysRendererTerrain;
    private BackgroundRenderer sysRendererBackground;
    private HUDRenderer sysRendererHUD;
    private LanderRenderer sysRendererLander;
    private ecs.Systems.Collision sysCollision;
    private ecs.Systems.Movement sysMovement;
    private ecs.Systems.KeyboardInput sysKeyboardInput;

    public void initialize(Graphics2D graphics) {

        ParticleSystem.instance().initialize(graphics);

        sysRendererTerrain = new TerrainRenderer(graphics);
        sysRendererBackground = new BackgroundRenderer(graphics);
        sysRendererHUD = new HUDRenderer(graphics);
        sysRendererLander = new LanderRenderer(graphics);

        sysCollision = new Collision();
        sysMovement = new Movement();
        sysKeyboardInput = new KeyboardInput(graphics.getWindow());

        // Create the terrain and the lander
        addEntity(Terrain.create());
        addEntity(Lander.create(
                new Vector2f( -0.25f, -0.40f),
               new Vector2f(0.05f, 0.08f),
                (float)(3 * Math.PI / 2),
                1.5f,
                0.05f,
                20));   // seconds of fuel
    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);
        // Now do the normal update
        sysMovement.update(elapsedTime);
        sysCollision.update(elapsedTime);

        ParticleSystem.instance().update(elapsedTime);

        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

        for (var entity : addThese) {
            addEntity(entity);
        }
        addThese.clear();

        // Because ECS framework, rendering is now part of the update
        sysRendererBackground.update(elapsedTime);
        sysRendererTerrain.update(elapsedTime);
        sysRendererLander.update(elapsedTime);
        sysRendererHUD.update(elapsedTime);

        ParticleSystem.instance().render();
    }

    private void addEntity(Entity entity) {
        sysKeyboardInput.add(entity);
        sysMovement.add(entity);
        sysCollision.add(entity);
        sysRendererTerrain.add(entity);
        sysRendererHUD.add(entity);
        sysRendererLander.add(entity);
        // Background renderer doesn't need to add any entities, as it
        // just draws the background image...which is not an entity
    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
        sysMovement.remove(entity.getId());
        sysCollision.remove(entity.getId());
        sysRendererTerrain.remove(entity.getId());
        sysRendererHUD.remove(entity.getId());
        sysRendererLander.remove(entity.getId());
        // Background renderer doesn't need to remove any entities, as it
        // just draws the background image...which is not an entity
    }
}
