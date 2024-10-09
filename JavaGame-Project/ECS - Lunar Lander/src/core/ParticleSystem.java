package core;

import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.MyRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implemented as a singleton to allow access throughout the code
 */
public class ParticleSystem {
    private static final ParticleSystem instance = new ParticleSystem();

    private final HashMap<Long, Particle> particles = new HashMap<>();
    private long nextName = 0;
    private final MyRandom random = new MyRandom();
    private Graphics2D graphics;
    private Texture texThrust;
    private Texture texFire;
    private Texture texSmoke;

    /**
     * Making this private prevents anything else from creating an instance of the class
     */
    private ParticleSystem() {
    }

    public static ParticleSystem instance() {
        return instance;
    }

    /**
     * This is needed because we need to wait until other things are ready before the particle
     * system can be fully initialized.
     */
    public void initialize(Graphics2D graphics) {
        this.graphics = graphics;
        texThrust = new Texture("resources/images/smoke.png");
        texFire = new Texture("resources/images/fire.png");
        texSmoke = new Texture("resources/images/smoke.png");
    }

    public void update(double elapsedTime) {
        // Update existing particles
        List<Long> removeMe = new ArrayList<>();
        for (Particle p : particles.values()) {
            // Particles can be removed if their time is up or if they are out of the boundary of the game-play area
            if (!p.update(elapsedTime)) {
                removeMe.add(p.name);
            } else if (p.center.x > 0.5f || p.center.x < -0.5f || p.center.y > 0.5f || p.center.y < -0.5f) {
                removeMe.add(p.name);
            }
        }

        // Remove dead particles
        for (Long key : removeMe) {
            particles.remove(key);
        }
    }

    public void render() {
        for (var particle : particles.values()) {
            graphics.draw(particle.texture, particle.area, particle.rotation, particle.center, Color.WHITE);
        }
    }

    public void createThrust(Vector2f direction, Vector2f center, float sizeMean, float sizeStdDev, float speedMean, float speedStdDev, float lifetimeMean, float lifetimeStdDev) {
        for (int particle = 0; particle < 10; particle++) {
            float size = (float) random.nextGaussian(sizeMean, sizeStdDev);
            var p = new Particle(
                    texThrust,
                    new Vector2f(center.x, center.y),   // Have to make a copy otherwise they all share the same center
                    new Vector2f(direction.x + (float) random.nextGaussian(0, 0.25f), direction.y + (float) random.nextGaussian(0, 0.25f)),
                    (float) random.nextGaussian(speedMean, speedStdDev),
                    new Vector2f(size, size),
                    random.nextGaussian(lifetimeMean, lifetimeStdDev));

            particles.put(nextName++, p);
        }
    }

    public void createExplosion(Vector2f center) {
        // TODO: Create the explosion effect
        // Number of particles for the explosion
        int numParticles = 20;

        // Parameters for particle creation
        float sizeMean = 0.010f;       // Mean size of particles
        float sizeStdDev = 0.004f;      // Standard deviation of size
        float speedMean = 0.5f;     // Mean speed of particles
        float speedStdDev = 0.025f;    // Standard deviation of speed
        float lifetimeMean = 1.0f;   // Mean lifetime of particles
        float lifetimeStdDev = 0.5f; // Standard deviation of lifetime

        // Create particles for the explosion
        for (int particle = 0; particle < numParticles; particle++) {
            float size = (float) random.nextGaussian(sizeMean, sizeStdDev);
            Vector2f direction = new Vector2f((float) Math.random() * 2 - 1, (float) Math.random() * 2 - 1).normalize();
            Vector2f direction1 = new Vector2f((float) Math.random() * 2 - 1, (float) Math.random() * 2 - 1).normalize();
            float speed = (float) random.nextGaussian(speedMean, speedStdDev);
            float lifetime = (float) random.nextGaussian(lifetimeMean, lifetimeStdDev);

            // Create the particle
            var p1 = new Particle(
                    texFire,   // Assuming you have a texture for explosion particles
                    new Vector2f(center.x, center.y),  // Center of the explosion
                    new Vector2f(direction.x, direction.y),  // Random direction
                    speed,
                    new Vector2f(size, size),
                    lifetime);
            var p2 = new Particle(
                    texSmoke,   // Assuming you have a texture for explosion particles
                    new Vector2f(center.x, center.y),  // Center of the explosion
                    new Vector2f(direction1.x, direction1.y),  // Random direction
                    speed,
                    new Vector2f(size, size),
                    lifetime);

            particles.put(nextName++, p1);
            particles.put(nextName++, p2);// Add particle to the collection
        }
    }
}
