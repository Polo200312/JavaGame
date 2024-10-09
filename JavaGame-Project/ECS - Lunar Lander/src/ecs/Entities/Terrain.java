package ecs.Entities;

import java.util.ArrayList;
import ecs.Components.Terrain.TerrainPoint;
import utils.MyRandom;

public class Terrain {

    public static Entity create() {
        Entity entity = new Entity();

        ecs.Components.Terrain terrain = new ecs.Components.Terrain();
        terrain.terrain = generateTerrain();
        entity.add(terrain);

        return entity;
    }

    /**
     * Kick off the terrain generation by creating the landing spot and then passing
     * that off to the recursive midpoint displacement terrain generation.
     */
    private static ArrayList<TerrainPoint> generateTerrain() {
        MyRandom random = new MyRandom();

        float landY = random.nextFloat() * 0.4f;    // 0.4 is to keep it in the lower part of the area
        float landXStart = -0.4f + random.nextFloat() * 0.8f; // -0.4 to keep it away from the border
        float landXEnd = landXStart + 0.1f; // 0.1 is the size of the landing area

        ArrayList<TerrainPoint> path = new ArrayList<>();
        path.add(new ecs.Components.Terrain.TerrainPoint(-0.5f, random.nextFloat() * 0.4f, false));
        path.add(new TerrainPoint(landXStart, landY, true));
        path.add(new TerrainPoint(landXEnd, landY, true));
        path.add(new TerrainPoint(0.5f, random.nextFloat() * 0.4f, false));

        return midpointDisplacement(random, path, 8, 2);
    }

    /**
     * Recursively build the terrain
     */
    private static ArrayList<TerrainPoint> midpointDisplacement(MyRandom random, ArrayList<TerrainPoint> path, int depth, float s) {
        if (depth == 0) return path;
        ArrayList<TerrainPoint> newPath = new ArrayList<>();

        for (int pt = 0; pt < path.size() - 1; pt++) {
            newPath.add(path.get(pt));
            // Only perform displacement if the area is not safe...checking both segment endpoints
            if (!(path.get(pt).safe && path.get(pt + 1).safe)) {
                float r = (float) (s * random.nextGaussian(0, 0.375f) * Math.abs(path.get(pt).x - path.get(pt + 1).x));
                float y = 0.5f * (path.get(pt).y + path.get(pt + 1).y) + r;
                newPath.add(new TerrainPoint(
                        (path.get(pt).x + path.get(pt + 1).x) / 2,
                        Math.min(Math.max(y, 0), 0.4f),
                        false));
            }
        }
        newPath.add(path.get(path.size() - 1));

        return midpointDisplacement(random, newPath, depth - 1, s / 1.1f);
    }
}
