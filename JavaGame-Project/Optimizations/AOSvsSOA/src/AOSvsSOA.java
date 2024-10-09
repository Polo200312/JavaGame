import java.util.Random;

public class AOSvsSOA {

    public static final int SIM_ITERATIONS = 10;   // Slowly move up by one to show the effect
    public static final int SIM_ELAPSED_TIME = 10; // ms
    public static final int SIM_PARTICLES_COUNT = 5000000;

    public static void main(String[] args) {

        Particle[] particles1 = makeArrayOfStructures(SIM_PARTICLES_COUNT);
        long start = System.currentTimeMillis();
        for (int iter = 0; iter < SIM_ITERATIONS; iter++) {
            for (int p = 0; p < particles1.length; p++) {
                particles1[p].center.x += (SIM_ELAPSED_TIME * particles1[p].speed * particles1[p].direction.x);
                particles1[p].center.y += (SIM_ELAPSED_TIME * particles1[p].speed * particles1[p].direction.y);
                particles1[p].alive += SIM_ELAPSED_TIME;
            }
        }
        long end = System.currentTimeMillis();
        System.out.printf("Array of Structures : %d\n", end - start);

        ParticleArrays particles2 = makeStuctureOfArrays(SIM_PARTICLES_COUNT);
        start = System.currentTimeMillis();
        for (int iter = 0; iter < SIM_ITERATIONS; iter++) {
            for (int p = 0; p < particles1.length; p++) {
                particles2.center[p].x += (SIM_ELAPSED_TIME * particles2.speed[p] * particles2.direction[p].x);
                particles2.center[p].y += (SIM_ELAPSED_TIME * particles2.speed[p] * particles2.direction[p].y);
                particles2.alive[p] += SIM_ELAPSED_TIME;
            }
        }
        end = System.currentTimeMillis();
        System.out.printf("Structure of Arrays : %d\n", end - start);
    }

    private static Particle[] makeArrayOfStructures(int howMany) {
        Random rnd = new Random();
        Particle[] particles = new Particle[howMany];

        for (int i = 0; i < howMany; i++) {
            particles[i] = new Particle(rnd);
        }

        return particles;
    }

    private static ParticleArrays makeStuctureOfArrays(int howMany) {
        Random rnd = new Random();
        ParticleArrays particles = new ParticleArrays(howMany);

        for (int i = 0; i < howMany; i++) {
            particles.size[i] = rnd.nextDouble() * 10;
            particles.center[i] = new Vector2(300, 300);
            particles.direction[i] = new Vector2(rnd.nextDouble(), rnd.nextDouble());
            particles.speed[i] = rnd.nextDouble() * 10;
            particles.rotation[i] = 0;
            particles.lifetime[i] = rnd.nextDouble() * 4;
            particles.alive[i] = 0;
        }

        return particles;
    }
}

class Vector2 {
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double x;
    public double y;
}

class Particle
{
    public double size;
    public Vector2 center;
    public Vector2 direction;
    public double speed;
    public double rotation;
    public double lifetime;
    public double alive;

    public Particle(Random rnd)
    {
        this.size = rnd.nextDouble() * 10;
        this.center = new Vector2(300, 300);
        this.direction = new Vector2(rnd.nextDouble(), rnd.nextDouble());
        this.speed = rnd.nextDouble() * 10;
        this.rotation = 0;
        this.lifetime = rnd.nextDouble() * 4;
        this.alive = 0;
    }
}

class ParticleArrays {
    public double[] size;
    public Vector2[] center;
    public Vector2[] direction;
    public double[] speed;
    public double[] rotation;
    public double[] lifetime;
    public double[] alive;

    public ParticleArrays(int howMany) {
        this.size = new double[howMany];
        this.center = new Vector2[howMany];
        this.direction = new Vector2[howMany];
        this.speed = new double[howMany];
        this.rotation = new double[howMany];
        this.lifetime = new double[howMany];
        this.alive = new double[howMany];
    }
}
